import PageTitle from "../../Components/PageTitle/PageTitle";
import "../../Components/ProfessionalComponents/ProfessionalBase.css";
import DigitalTwin from "../../Components/ProfessionalComponents/ProfessionalDigitalTwin";

import axios from 'axios';
import { useState, useEffect } from 'react';
import { useParams } from 'react-router-dom';

export default function ProfessionalPatient() {
    const { id } = useParams();
    const [patient, setPatient] = useState(null);

    const urlPatient = `http://localhost:8080/api/patients/${id}`;

    const urlLastHeartbeat = `http://localhost:8080/api/patients/${id}/heartbeats/latest?size=1`;

    // Heartbeat (value and severity)
    const [lastHeartbeat, setLastHeartbeat] = useState(null);
    const [heartbeatSeverity, setHeartbeatSeverity] = useState(null);

    const [heartStyle, setHeartStyle] = useState({
        animation: `growAndFade 1s ease-in-out infinite alternate`,
    });

    useEffect(() => {
        axios.get(urlPatient, {
            headers: {
                'Access-Control-Allow-Origin': '*',
            },
            proxy: {
                port: 8080
            }
        })
            .then(response => {

                console.log(response.data);
                setPatient(response.data);
            })
            .catch(error => {
                console.log(error);
            });

        axios.get(urlLastHeartbeat, {
            headers: {
                'Access-Control-Allow-Origin': '*',
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

                    // 60 BPM <=> 1 segundo = 1 beat
                    // 80 BPM <=> 1 segundo = 1.33 beats
                    // Para tornar mais suave: retirar apenas metade da diferença de 60 para 80
                    // ex: 1s - ((80 / 70) - 1s) <=> 1s - (1,14 - 1s) = 1s - 0.14 = 0.86s
                    const newAnimationSpeed = 1 - ((heartbeatObject.heartbeat / (60 + ((heartbeatObject.heartbeat - 60) / 2))) - 1);
                    setHeartStyle({
                        animation: `growAndFade ${newAnimationSpeed}s ease-in-out infinite alternate`,
                    });
                }
            })
            .catch(error => {
                console.log(error);
            });
    }, [id]);

    if (!patient) return null;
    if (!lastHeartbeat) return null;

    return (
        <div className="vertical-container">
            <PageTitle title="Patient" />
            <div className='App-content'>
                <div className="vertical-container gap-vertical">
                    <div className="horizontal-container gap-horizontal">
                        <DigitalTwin value={lastHeartbeat} heartStyle={heartStyle} />
                        <div className="vertical-container">
                            {patient.name}
                            <ul>
                                <li>amamama</li>
                                <li>amamama</li>
                                <li>amamama</li>
                            </ul>
                        </div>
                    </div>
                    <div className="horizontal-container gap-horizontal">
                    </div>
                </div>
            </div>
        </div>
    );
}