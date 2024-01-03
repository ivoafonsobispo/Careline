import PageTitle from "../../Components/PageTitle/PageTitle";
import "../../Components/ProfessionalComponents/ProfessionalBase.css";
import "./ProfessionalPatient.css"
import DigitalTwin from "../../Components/ProfessionalComponents/ProfessionalDigitalTwin";

import { ThermometerHalf, Heart } from 'react-bootstrap-icons';

import axios from 'axios';
import { useState, useEffect } from 'react';
import { useParams } from 'react-router-dom';
import PatientInfoList from "../../Components/ProfessionalComponents/ProfessionalPatientLists/PatientInfoList";

export default function ProfessionalPatient() {
    const { id } = useParams();
    const [patient, setPatient] = useState(null);
    const [currentList, setCurrentList] = useState("urtriage");

    const urlPatient = `http://localhost:8080/api/patients/${id}`;
    const urlLastHeartbeat = `http://localhost:8080/api/patients/${id}/heartbeats/latest?size=1`;
    const urlLastTemperature = `http://localhost:8080/api/patients/${id}/temperatures/latest?size=1`;

    // Heartbeat (value and severity)
    const [lastHeartbeat, setLastHeartbeat] = useState(null);
    const [heartbeatSeverity, setHeartbeatSeverity] = useState(null);

    // Temperature (value and severity)
    const [lastTemperature, setLastTemperature] = useState(null);
    const [temperatureSeverity, setTemperatureSeverity] = useState(null);

    const urlDiagnoses = `http://localhost:8080/api/professionals/1/patients/${id}/diagnosis`;
    const [diagnoses, setDiagnoses] = useState(null);

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

        axios.get(urlLastTemperature, {
            headers: {
                'Access-Control-Allow-Origin': '*',
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
                console.log(error);
            });

        axios.get(urlDiagnoses, {
            headers: {
                'Access-Control-Allow-Origin': '*',
            },
            proxy: {
                port: 8080
            }
        })
            .then(response => {
                console.log(response.data.content);
                setDiagnoses(response.data.content);
            })
            .catch(error => {
                console.log(error);
            });
    }, [id, urlLastHeartbeat, urlLastTemperature, urlPatient]);

    const handleListChange = (list) => {
        console.log(list);
        setCurrentList(list);
    }

    useEffect(() => {
        if (currentList === 'Pending Drones') {
            // Apply sort/map
        }
    }, [currentList]);

    if (!patient) return null;
    if (!lastHeartbeat) return null;

    return (
        <div className="vertical-container">
            <PageTitle title="Patient" />
            <div className='App-content'>
                <div className="horizontal-container gap-horizontal">
                    <div className="align-line-column gap-vertical patient-page-container">
                        <DigitalTwin value={lastHeartbeat} heartStyle={heartStyle} />
                        <div className="vertical-container" style={{ gap: "8%", padding: "2%" }}>
                            <div>Name: {patient.name}</div>
                            <div>Email: {patient.email}</div>
                            <div>NUS: {patient.nus}</div>
                            <div className="align-line-row"><Heart />&nbsp; {lastHeartbeat} BPM</div>
                            <div className="align-line-row"><ThermometerHalf />&nbsp; {lastTemperature} °C</div>
                        </div>
                    </div>
                    <div className="vertical-container gap-vertical patient-page-container" style={{ flexGrow: "1" }}>
                        {currentList === "urtriage" || currentList === "rtriage" ? (
                            <>
                                <div className="align-line-row" style={{ gap: "8%" }}>
                                    <div>Total triage: 2</div>
                                    <div>Unreviewed triage: 1</div>
                                    <div>Reviewd triage: 1</div>
                                </div>
                                <PatientInfoList title={currentList === 'urtriage' ? "Unreviewed Triage" : "Reviewed Triage"} setCurrentList={handleListChange} />
                            </>
                        ) : currentList === "diagnoses" ? (
                            <>
                                <div className="align-line-row" style={{ gap: "8%" }}>
                                    <div>Total diagnoses: 2</div>
                                </div>
                                <PatientInfoList title={"Diagnoses"} dataArray={diagnoses}/>
                            </>
                        ) : (
                            <>
                                <div className="align-line-row" style={{ gap: "8%" }}>
                                    <div>Total drones: 2</div>
                                    <div>Drones inshipping: 1</div>
                                    <div>Drones shipped: 1</div>
                                </div>
                                <PatientInfoList title={currentList === "pdrones" ? "Pending Drones" : currentList === "itdrones" ? "Drones In Transit" : currentList === "ddrones" ? "Delivered Drones" : "Failed Drones"} setCurrentList={handleListChange} />
                            </>
                        )}
                        <div className="align-line-row" style={{ gap: "2%" }}>
                            <button className="professional-patient-page-button" onClick={() => setCurrentList("urtriage")}>Triage</button>
                            <button className="professional-patient-page-button" onClick={() => setCurrentList("diagnoses")}>Diagnoses</button>
                            <button className="professional-patient-page-button" onClick={() => setCurrentList("pdrones")}>Drones</button>
                        </div>
                    </div>

                </div>
            </div>
        </div>
    );
}