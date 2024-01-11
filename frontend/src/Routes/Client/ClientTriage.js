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

import Stomp from 'stompjs';
import SockJS from 'sockjs-client';

import { useSelector } from "react-redux";
import { useEffect } from "react";
import axios from "axios";

export default function ClientTriage() {
    const token = useSelector((state) => state.auth.token);
    const user = useSelector((state) => state.auth.user);

    const [selected, setSelected] = useState(new Date());

    let footer = <p>Please pick a day.</p>;
    if (selected) {
        footer = <p>You picked {format(selected, 'PP')}.</p>;
    }

    const [selectedButton, setButton] = useState("all"); // all; urtriage; rtriage

    const urlTriage = `http://10.20.229.55/api/patients/${user.id}/triages`;
    const [triages, setTriages] = useState([]);
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

    let stompClient;

    useEffect(() => {
        const socket = new SockJS('http://10.20.229.55/websocket-endpoint');
        stompClient = Stomp.over(socket);

        try {
            stompClient.connect({}, () => {
                stompClient.subscribe('/topic/triages', (message) => {
                    
                    let newTriage = JSON.parse(message.body);
                    if (newTriage.status === 'UNREVIEWED') {
                        setTriages((prevTriages) => [newTriage, ...prevTriages]);
                    } else {
                        const updatedTriages = triages.map((triage) => (triage.id === newTriage.id ? newTriage : triage));
                        setTriages(updatedTriages);
                    }
                });
            });


        } catch (error) {
            console.error('WebSocket connection error:', error);
            // Handle the error here, e.g., show a user-friendly message or retry the connection
        }

        return () => {
            if (stompClient && stompClient.connected) {
                stompClient.disconnect();
            }
        };
    }, [urlTriage, triages]);

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
                                    {!triages || triages === null || triages.length === 0 ? (
                                        <div className='no-records'>No triage yet.</div>
                                    ) : (
                                        <>
                                            {triages.map((triage, index) => {
                                                return (
                                                    <ClientTriageComponent key={index} triage={triage} />
                                                )
                                            })}
                                        </>
                                    )}
                                </>
                            ) : selectedButton === 'urtriage' ? (
                                <>
                                    {!triages || triages.filter(triage => triage.status === 'UNREVIEWED').length === 0 ? (
                                        <div className='no-records'>No unreviewed triage.</div>
                                    ) : (
                                        <>
                                            {triages.filter(triage => triage.status === 'UNREVIEWED').map((triage, index) => {
                                                return (
                                                    <ClientTriageComponent key={index} triage={triage} />
                                                )
                                            })}
                                        </>
                                    )}
                                </>
                            ) : (
                                <>
                                    {!triages || triages.filter(triage => triage.status === 'REVIEWED').length === 0 ? (
                                        <div className='no-records'>No reviewed triage.</div>
                                    ) : (
                                        <>
                                            {triages.filter(triage => triage.status === 'REVIEWED').map((triage, index) => {
                                                return (
                                                    <ClientTriageComponent key={index} triage={triage} />
                                                )
                                            })}
                                        </>
                                    )}
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