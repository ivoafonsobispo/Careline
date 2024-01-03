import PageTitle from "../../Components/PageTitle/PageTitle";
import "../../Components/ClientComponents/ClientBase.css";
import classNames from "classnames";
import ClientDroneComponent from "../../Components/ClientComponents/ClientDroneComponent";

//Day Picker
import { format } from 'date-fns';
import { DayPicker } from 'react-day-picker';
import 'react-day-picker/dist/style.css';
import '../../DayPicker.css';

import axios from 'axios';
import { useState, useEffect } from 'react';

export default function ClientDrones() {
    const [selected, setSelected] = useState(new Date());
    const [date, setDate] = useState("2023-12-25");

    // const urlDrones = `http://localhost:8080/api/patients/1/deliveries/date/${date}`;
    const urlDrones = `http://localhost:8080/api/patients/1/deliveries`;

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

    const [selectedButton, setButton] = useState("all"); // all; pdrones; itdrones; ddrones; fdrones

    const [drones, setDrones] = useState(null);
    
    useEffect(() => {

        axios.get(urlDrones, {
            headers: {
                'Access-Control-Allow-Origin': '*',
            },
            proxy: {
                port: 8080
            }
        })
            .then(response => {
                console.log(response.data.content);
                setDrones(response.data.content);
            })
            .catch(error => {
                console.log(error);
            });
    }, []);

    return (

        <div className="horizontal-container">
            <div className="vertical-container">
                <PageTitle title="Drones" />
                <div className='App-content'>
                    <div className="vertical-container">
                        <div className="align-line-row" style={{ marginBottom: "2%", gap: "2%" }}>
                            <button className={classNames("triage-button", selectedButton !== 'all' ? "triage-button-inactive" : "")} onClick={() => setButton("all")}>All Drones</button>
                            <button className={classNames("triage-button", selectedButton !== 'pdrones' ? "triage-button-inactive" : "")} onClick={() => setButton("pdrones")}>Pending Drones</button>
                            <button className={classNames("triage-button", selectedButton !== 'itdrones' ? "triage-button-inactive" : "")} onClick={() => setButton("itdrones")}>In Transit Drones</button>
                            <button className={classNames("triage-button", selectedButton !== 'ddrones' ? "triage-button-inactive" : "")} onClick={() => setButton("ddrones")}>Delivered Drones</button>
                            <button className={classNames("triage-button", selectedButton !== 'fdrones' ? "triage-button-inactive" : "")} onClick={() => setButton("fdrones")}>Failed Drones</button>
                        </div>
                        <div className="vertical-container diagnoses-list" style={{ maxHeight: "470px" }}>
                            {selectedButton === 'all' ? (
                                <>
                                    {!drones || drones.length === 0 ? (
                                        <div className='no-records'>No drones yet.</div>
                                    ) : (
                                        <>
                                            {drones.map((drone, index) => {
                                                return (
                                                    <ClientDroneComponent key={index} drone={drone} />
                                                )
                                            })}
                                        </>
                                    )}
                                </>
                            ) : selectedButton === 'pdrones' ? (
                                <>
                                    {!drones || drones.length === 0 || drones.filter(drone => drone.status === 'PENDING').length === 0 ? (
                                        <div className='no-records'>No pending drones.</div>
                                    ) : (
                                        <>
                                            {drones.filter(drone => drone.status === 'PENDING').map((drone, index) => {
                                                return (
                                                    <ClientDroneComponent key={index} drone={drone} />
                                                )
                                            })}
                                        </>
                                    )}
                                </>
                            ) : selectedButton === 'itdrones' ? (
                                <>
                                    {!drones || drones.length === 0 || drones.filter(drone => drone.status === 'IN_TRANSIT').length === 0 ? (
                                        <div className='no-records'>No in transit drones.</div>
                                    ) : (
                                        <>
                                            {drones.filter(drone => drone.status === 'IN_TRANSIT').map((drone, index) => {
                                                return (
                                                    <ClientDroneComponent key={index} drone={drone} />
                                                )
                                            })}
                                        </>
                                    )}
                                </>
                            ) : selectedButton === 'ddrones' ? (
                                <>
                                    {!drones || drones.length === 0 || drones.filter(drone => drone.status === 'DELIVERED').length === 0 ? (
                                        <div className='no-records'>No delivered drones.</div>
                                    ) : (
                                        <>
                                            {drones.filter(drone => drone.status === 'DELIVERED').map((drone, index) => {
                                                return (
                                                    <ClientDroneComponent key={index} drone={drone} />
                                                )
                                            })}
                                        </>
                                    )}
                                </>
                            ) : (
                                <>
                                    {!drones || drones.length === 0 || drones.filter(drone => drone.status === 'FAILED').length === 0 ? (
                                        <div className='no-records'>No failed drones.</div>
                                    ) : (
                                        <>
                                            {drones.filter(drone => drone.status === 'FAILED').map((drone, index) => {
                                                return (
                                                    <ClientDroneComponent key={index} drone={drone} />
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