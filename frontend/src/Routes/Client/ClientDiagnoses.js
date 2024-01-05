import PageTitle from "../../Components/PageTitle/PageTitle";
import "../../Components/ClientComponents/ClientBase.css";
import "./ClientDiagnoses.css";
import ClientDiagnosis from "../../Components/ClientComponents/ClientDiagnosis";

import { useState } from 'react';

//Day Picker
import { format } from 'date-fns';
import { DayPicker } from 'react-day-picker';
import 'react-day-picker/dist/style.css';
import '../../DayPicker.css';

import Stomp from 'stompjs';
import SockJS from 'sockjs-client';

import axios from 'axios';
import { useEffect } from 'react';

import { useSelector } from "react-redux";

export default function ClientDiagnoses() {
    const token = useSelector((state) => state.auth.token);

    const [selected, setSelected] = useState(new Date());
    const [date, setDate] = useState("2023-12-25");

    const urlDiagnoses = `http://10.20.229.55/api/patients/1/diagnosis/date/${date}`;

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
                Authorization: `Bearer ${token}`,
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
    }, [urlDiagnoses, token]);

    useEffect(() => {
        try {
            const socket = new SockJS('http://10.20.229.55/websocket-endpoint');
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
        } catch (error) {
            console.error('WebSocket connection error:', error);
            // Handle the error here, e.g., show a user-friendly message or retry the connection
        }
    }, []);

    return (
        <div className="horizontal-container">
            <div className="vertical-container">
                <PageTitle title="Diagnoses" />
                <div className='App-content'>
                    <div className="vertical-container diagnoses-list" style={{ maxHeight: "540px" }}>
                        {!diagnoses || diagnoses.length === 0 ? (
                            <div className='no-records'>No records yet.</div>
                        ) : (
                            <>
                                {diagnoses.map((diagnosis, index) => {
                                    return (
                                        <ClientDiagnosis key={index} diagnosis={diagnosis} />
                                    )
                                })}
                            </>
                        )}
                    </div>
                </div>
            </div>
            <div className='day-picker'>
                <DayPicker
                    mode="single"
                    selected={selected}
                    onSelect={setSelected}
                    footer={footer}
                    className='day-picker-style'
                />
            </div>
        </div>
    );
}