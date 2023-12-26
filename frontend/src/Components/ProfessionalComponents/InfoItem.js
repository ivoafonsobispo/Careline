import './ProfessionalBase.css'
import './InfoList.css'

import { Heart, ThermometerHalf, HeartFill, HeartHalf, Thermometer, ThermometerHigh } from 'react-bootstrap-icons';

import axios from 'axios';
import { useState, useEffect } from 'react';

export default function InfoItem({ patient }) {

    const [heartbeat, setLastHeartbeat] = useState(null);
    const [temperature, setLastTemperature] = useState(null);
    const [heartbeatSeverity, setHeartbeatSeverity] = useState(null);
    const [temperatureSeverity, setTemperatureSeverity] = useState(null);

    let urlHeartbeat = `http://localhost:8080/api/patients/${patient.id}/heartbeats/latest`;
    let urlTemperature = `http://localhost:8080/api/patients/${patient.id}/temperatures/latest`;

    useEffect(() => {
        axios.get(urlHeartbeat, {
            headers: {
                'Access-Control-Allow-Origin': '*',
            },
            proxy: {
                port: 8080
            }
        })
            .then(response => {
                console.log(response.data.content[0]);
                let heartbeatObject = response.data.content[0];
                setLastHeartbeat(heartbeatObject.heartbeat);
                setHeartbeatSeverity(heartbeatObject.severity);
            })
            .catch(error => {
                // handle the error
                console.log(error);
            });

        axios.get(urlTemperature, {
            headers: {
                'Access-Control-Allow-Origin': '*',
            },
            proxy: {
                port: 8080
            }
        })
            .then(response => {

                console.log(response.data.content[0]);
                let temperatureObject = response.data.content[0];
                setLastTemperature(temperatureObject.temperature);
                setTemperatureSeverity(temperatureObject.severity);
            })
            .catch(error => {
                // handle the error
                console.log(error);
            });
    }, [urlHeartbeat, urlTemperature]);


    if (!heartbeat) return null;
    if (!temperature) return null;
    if (!heartbeatSeverity) return null;
    if (!temperatureSeverity) return null;


    return (
        <div className="App-professional-info-list-item" style={{ display: "flex", flexDirection: "column" }}>
            <div className='vertical-container' style={{gap: "10%", paddingBottom: "2%"}}>
                <span className='patient-info'>{patient.name} </span>
                <div className='horizontal-container'>
                    {/* <div className='align-line-column patient-info'> */}
                    <span className='horizontal-container' style={{ gap: "10%" }}>
                        {heartbeatSeverity === 'GOOD' ? (
                            <HeartFill size={20} color="var(--lightGreen)" />
                        ) : heartbeatSeverity === 'MEDIUM' ? (
                            <HeartHalf size={20} color="orange" />
                        ) : (
                            <Heart size={20} color="red" />
                        )}

                        {heartbeat} BPM
                    </span>
                    <span className='horizontal-container' style={{ gap: "10%" }}>
                        {temperatureSeverity === 'GOOD' ? (
                            <ThermometerHigh size={20} color="var(--lightGreen)" />
                        ) : heartbeatSeverity === 'MEDIUM' ? (
                            <ThermometerHalf size={20} color="orange" />
                        ) : (
                            <Thermometer size={20} color="red" />
                        )}
                        {temperature} °C
                    </span>
                </div>
                {/* </div> */}
                {/* <span className='patient-info align-line-row'><HeartFill size={20} color="var(--lightGreen)" /> &nbsp; Good</span> */}
            </div>
        </div>
    );
}
