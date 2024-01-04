import PageTitle from "../../Components/PageTitle/PageTitle";
import "../../Components/ProfessionalComponents/ProfessionalBase.css";
import "./ProfessionalDiagnoses.css";
import ClientDiagnosis from "../../Components/ClientComponents/ClientDiagnosis";

import { useState } from 'react';

//Day Picker
import { format } from 'date-fns';
import { DayPicker } from 'react-day-picker';
import 'react-day-picker/dist/style.css';
import '../../DayPicker.css';

import Stomp from 'stompjs';
import SockJS from 'sockjs-client';

import { Pen } from 'react-bootstrap-icons'
import { NavLink } from 'react-router-dom';

import axios from 'axios';
import { useEffect } from 'react';
import ProfessionalDiagnosisComponent from "../../Components/ProfessionalComponents/ProfessionalDiagnosisComponent";

export default function ProfessionalDiagnoses() {
    const [selected, setSelected] = useState(new Date());
    const [date, setDate] = useState("2023-12-25");

    // const urlDiagnoses = `http://localhost:8080/api/professionals/1/diagnosis/latest`;
    const urlDiagnoses = `http://localhost:8080/api/professionals/1/diagnosis/date/${date}`;

    useEffect(() => {
        if (selected) {
            const year = selected.getFullYear();
            const month = String(selected.getMonth() + 1).padStart(2, '0');
            const day = String(selected.getDate()).padStart(2, '0');
            const formattedDate = `${year}-${month}-${day}`;
            setDate(formattedDate);
            console.log(formattedDate);
        }
    }, [selected]);

    let footer = <p>Please pick a day.</p>;
    if (selected) {
        footer = <p>You picked {format(selected, 'PP')}.</p>;
    }

    const [diagnoses, setDiagnoses] = useState(null);
    useEffect(() => {
        axios.get(urlDiagnoses, {
            headers: {
                'Access-Control-Allow-Origin': '*',
            },
            proxy: {
                port: 8080
            }
        })
            .then(response => {
                setDiagnoses(response.data.content);
                // console.log(response.data.content);
            })
            .catch(error => {
                console.log(error);
            });
    }, [urlDiagnoses]);

    useEffect(() => {
        const socket = new SockJS('http://localhost:8080/websocket-endpoint');
        const stompClient = Stomp.over(socket);

        stompClient.connect({}, () => {
            stompClient.subscribe('/topic/diagnosis', (message) => {
                let newDiagnosis = JSON.parse(message.body);
                setDiagnoses((prevDiagnoses) => [newDiagnosis, ...prevDiagnoses]);
            });
        });

        return () => {
            stompClient.disconnect();
        };
    }, []);

    return (
        <div className="horizontal-container">
            <div className="vertical-container">
                <PageTitle title="Diagnoses" />
                <div className='App-content'>
                    <div className="vertical-container">
                        <div className="vertical-container diagnoses-list" style={{ maxHeight: "470px" }}>
                            {!diagnoses || diagnoses.length === 0 ? (
                                <div className='no-records'>No records yet.</div>
                            ) : (
                                <>
                                    {diagnoses.map((diagnosis, index) => {
                                        return (
                                            <ProfessionalDiagnosisComponent key={index} diagnosis={diagnosis} />
                                        )
                                    })}
                                </>
                            )}
                        </div>

                        <NavLink to={'/diagnosis'} className='professional-triage-button align-line-row' style={{ marginLeft: "auto", marginTop: "3%", marginRight: "1%", padding: "1.5%", fontSize: "16px" }}>
                            <span className='align-line-row' style={{ margin: "auto" }}>
                                <Pen size={13} /> &nbsp; Prescribe Diagnosis
                            </span>
                        </NavLink>
                    </div>
                </div>
            </div>
            <div className='day-picker' style={{ background: "var(--professionalLightColor)" }}>
                <DayPicker
                    mode="single"
                    selected={selected}
                    onSelect={setSelected}
                    footer={footer}
                    className='day-picker-style-professional'
                />
            </div>
        </div>
    );
}