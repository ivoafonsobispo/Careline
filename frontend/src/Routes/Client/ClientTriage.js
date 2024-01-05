import PageTitle from "../../Components/PageTitle/PageTitle";
import "../../Components/ClientComponents/ClientBase.css";
import ClientTriageComponent from "../../Components/ClientComponents/ClientTriageComponent";
import classNames from "classnames";
import "./ClientTriage.css";

import { useState } from 'react';

//Day Picker
import { format } from 'date-fns';
import { DayPicker } from 'react-day-picker';
import 'react-day-picker/dist/style.css';
import '../../DayPicker.css';

import { useSelector } from "react-redux";
import { useEffect } from "react";
import axios from "axios";

export default function ClientTriage() {
    const token = useSelector((state) => state.auth.token);

    const [selected, setSelected] = useState(new Date());

    let footer = <p>Please pick a day.</p>;
    if (selected) {
        footer = <p>You picked {format(selected, 'PP')}.</p>;
    }

    const [selectedButton, setButton] = useState("all"); // all; urtriage; rtriage

    const urlTriage = `http://10.20.229.55/api/triages`;
    const [triages, setTriages] = useState(null);
    useEffect(() => {
        axios.get(urlTriage, {
            headers: {
                'Access-Control-Allow-Origin': '*',
                Authorization: `Bearer ${token}`,
            },
            proxy: {
                port: 8080
            }
        })
            .then(response => {
                setTriages(response.data.content);
                // console.log(response.data.content);
            })
            .catch(error => {
                console.log(error);
            });
    }, [urlTriage, token]);

    return (
        <div className="horizontal-container">
            <div className="vertical-container">
                <PageTitle title="Triage" />
                <div className='App-content'>
                    <div className="vertical-container">
                        <div className="align-line-row" style={{ marginBottom: "2%", gap: "2%" }}>
                            <button className={classNames("triage-button", selectedButton !== 'all' ? "triage-button-inactive" : "")} onClick={() => setButton("all")}>All Triage</button>
                            <button className={classNames("triage-button", selectedButton !== 'urtriage' ? "triage-button-inactive" : "")} onClick={() => setButton("urtriage")}>Unreviewed Triage</button>
                            <button className={classNames("triage-button", selectedButton !== 'rtriage' ? "triage-button-inactive" : "")} onClick={() => setButton("rtriage")}>Reviewed Triage</button>
                        </div>
                        <div className="vertical-container diagnoses-list" style={{ maxHeight: "470px" }}>
                            {selectedButton === 'all' ? (
                                <>
                                    <ClientTriageComponent status={"Reviewed"} />
                                    <ClientTriageComponent status={"Unreviewed"} />
                                </>
                            ) : selectedButton === 'urtriage' ? (
                                <>
                                    <ClientTriageComponent status={"Unreviewed"} />
                                </>
                            ) : (
                                <>
                                    <ClientTriageComponent status={"Reviewed"} />
                                </>
                            )}
                        </div>
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