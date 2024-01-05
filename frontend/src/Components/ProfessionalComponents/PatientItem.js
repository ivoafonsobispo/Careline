import './ProfessionalBase.css'
import './PatientList.css'

import { Heart, ThermometerHalf, HeartFill, HeartHalf, Thermometer, ThermometerHigh, PlusSquare } from 'react-bootstrap-icons';
import { NavLink } from 'react-router-dom';

import axios from 'axios';
import { useState, useEffect } from 'react';

import { useSelector } from 'react-redux';

export default function PatientItem({ patient, title, setAssociatedPatient }) {
    const token = useSelector((state) => state.auth.token);	
    const user = useSelector((state) => state.auth.user);	

    const [heartbeat, setLastHeartbeat] = useState(null);
    const [temperature, setLastTemperature] = useState(null);
    const [heartbeatSeverity, setHeartbeatSeverity] = useState(null);
    const [temperatureSeverity, setTemperatureSeverity] = useState(null);

    let urlHeartbeat = `http://10.20.229.55/api/patients/${patient.id}/heartbeats/latest`;
    let urlTemperature = `http://10.20.229.55/api/patients/${patient.id}/temperatures/latest`;

    useEffect(() => {
        axios.get(urlHeartbeat, {
            headers: {
                'Access-Control-Allow-Origin': '*',
                Authorization: `Bearer ${token}`,
            },
            proxy: {
                port: 8080
            }
        })
            .then(response => {
                let heartbeatObject = response.data.content[0];
                if (heartbeatObject) {
                    setLastHeartbeat(heartbeatObject.heartbeat);
                    setHeartbeatSeverity(heartbeatObject.severity);
                }
            })
            .catch(error => {
                // handle the error
                console.log(error);
            });

        axios.get(urlTemperature, {
            headers: {
                'Access-Control-Allow-Origin': '*',
                Authorization: `Bearer ${token}`,
            },
            proxy: {
                port: 8080
            }
        })
            .then(response => {
                let temperatureObject = response.data.content[0];
                if (temperatureObject) {
                    setLastTemperature(temperatureObject.temperature);
                    setTemperatureSeverity(temperatureObject.severity);
                }
            })
            .catch(error => {
                // handle the error
                console.log(error);
            });
    }, [urlHeartbeat, urlTemperature, token]);

    const associatePatient = async () => {
        try {
            const response = await fetch(`http://10.20.229.55/api/professionals/${user.id}/patients/${patient.id}`, {
                method: 'PATCH',
                headers: {
                    'Content-Type': 'application/json',
                    Authorization: `Bearer ${token}`,
                },
            });

            if (!response.ok) {
                // Handle non-successful response here
                console.error('Error:', response.statusText);
                return;
            }

            setAssociatedPatient()
        } catch (error) {
            // Handle errors during the fetch
            console.error('Error:', error);
        }
    };

    return (
        <div className='align-line-row' style={{ gap: "2%" }}>
            <NavLink to={`/patient/${patient.id}`} className="App-professional-info-list-item" style={{ display: "flex", flexDirection: "column", color: "black" }}>
                <div className='vertical-container' style={{ gap: "10%", paddingBottom: "2%" }}>
                    <span className='patient-info'>{patient.name} ({patient.nus})</span>
                    <div className='horizontal-container'>
                        {/* <div className='align-line-column patient-info'> */}
                        <span className='horizontal-container' style={{ gap: "10%" }}>
                            {!heartbeatSeverity ? (
                                <Heart size={20} color="black" />
                            ) : heartbeatSeverity === 'GOOD' ? (
                                <HeartFill size={20} color="var(--lightGreen)" />
                            ) : heartbeatSeverity === 'MEDIUM' ? (
                                <HeartHalf size={20} color="orange" />
                            ) : (
                                <Heart size={20} color="red" />
                            )}

                            {heartbeat ? heartbeat : "---"} BPM
                        </span>
                        <span className='horizontal-container' style={{ gap: "10%" }}>
                            {!temperatureSeverity ? (
                                <Thermometer size={20} color="black" />
                            ) : temperatureSeverity === 'GOOD' ? (
                                <ThermometerHigh size={20} color="var(--lightGreen)" />
                            ) : heartbeatSeverity === 'MEDIUM' ? (
                                <ThermometerHalf size={20} color="orange" />
                            ) : (
                                <Thermometer size={20} color="red" />
                            )}
                            {temperature ? temperature : "---"} °C
                        </span>
                    </div>
                </div>
            </NavLink>
            {title === "Available Patients" ? (
                <button className='patient-item-button' onClick={associatePatient}><PlusSquare size={20} /></button>
            ) : (<></>)}

        </div>
    );
}
