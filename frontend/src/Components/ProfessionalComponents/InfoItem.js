import './ProfessionalBase.css'
import './InfoList.css'

import { Heart, ThermometerHalf, HeartFill } from 'react-bootstrap-icons';

import axios from 'axios';
import { useState, useEffect } from 'react';
const urlHeartbeat = 'http://localhost:8080/api/patients/1/heartbeats/latest';

export default function InfoItem({ patientName, patientId }) {

    const [heartbeatObject, setHeartbeat] = useState(null);

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
                // handle the response
                setHeartbeat(response.data.content);
                //   console.log("Last Temperature:");
                //   console.log(response.data.content);
            })
            .catch(error => {
                // handle the error
                console.log(error);
            });
    }, []);


    if (!heartbeatObject) return null;
    let lastHeartbeatValue = Object.values(heartbeatObject)[0].heartbeat;
    // console.log(lastHeartbeatValue);


    return (
        <div className="App-professional-info-list-item" style={{ display: "flex", flexDirection: "column" }}>
            <div className='horizontal-container' style={{ alignItems: "center" }}>
                <span className='patient-info' style={{ width: "40%", maxWidth: "40%" }}>{patientName} </span>
                <div className='align-line-column patient-info'>
                    <span><Heart size={20} color="black" /> {lastHeartbeatValue} BPM</span>
                    <span><ThermometerHalf size={20} color="black" /> 36 °C</span>
                </div>
                <span className='patient-info align-line-row'><HeartFill size={20} color="var(--lightGreen)" /> &nbsp; Good</span>
            </div>
        </div>
    );
}
