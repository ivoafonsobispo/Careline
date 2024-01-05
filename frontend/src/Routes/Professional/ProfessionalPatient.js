import PageTitle from "../../Components/PageTitle/PageTitle";
import "../../Components/ProfessionalComponents/ProfessionalBase.css";
import "./ProfessionalPatient.css"
import DigitalTwin from "../../Components/ProfessionalComponents/ProfessionalDigitalTwin";

import { ThermometerHalf, Heart } from 'react-bootstrap-icons';

import Stomp from 'stompjs';
import SockJS from 'sockjs-client';

import axios from 'axios';
import { useSelector } from "react-redux";

import { useState, useEffect } from 'react';
import { useParams } from 'react-router-dom';
import PatientInfoList from "../../Components/ProfessionalComponents/ProfessionalPatientLists/PatientInfoList";

export default function ProfessionalPatient() {
    const token = useSelector((state) => state.auth.token);

    const { id } = useParams();
    const [patient, setPatient] = useState(null);
    const [currentList, setCurrentList] = useState("urtriage");
    const [currentDroneStatusFilter, setcurrentDroneStatusFilter] = useState('PENDING');

    const urlPatient = `http://10.20.229.55/api/professionals/1/patients/${id}`;
    const urlLastHeartbeat = `http://10.20.229.55/api/patients/${id}/heartbeats/latest?size=1`;
    const urlLastTemperature = `http://10.20.229.55/api/patients/${id}/temperatures/latest?size=1`;

    // Heartbeat (value and severity)
    const [lastHeartbeat, setLastHeartbeat] = useState(null);
    const [heartbeatSeverity, setHeartbeatSeverity] = useState(null);

    // Temperature (value and severity)
    const [lastTemperature, setLastTemperature] = useState(null);
    const [temperatureSeverity, setTemperatureSeverity] = useState(null);

    const urlDiagnoses = `http://10.20.229.55/api/professionals/1/patients/${id}/diagnosis/latest`;
    const [diagnoses, setDiagnoses] = useState(null);

    const [heartStyle, setHeartStyle] = useState({
        animation: `growAndFade 1s ease-in-out infinite alternate`,
    });

    const urlDrones = `http://10.20.229.55/api/patients/${id}/deliveries`;
    const [drones, setDrones] = useState(null);

    useEffect(() => {
        axios.get(urlPatient, {
            headers: {
                'Access-Control-Allow-Origin': '*',
                Authorization: `Bearer ${token}`,
            },
            proxy: {
                port: 8080
            }
        })
            .then(response => {
                setPatient(response.data);
            })
            .catch(error => {
                console.log(error);
            });

        axios.get(urlLastHeartbeat, {
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
                console.log(error);
            });

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
                // console.log(response.data.content);
                setDiagnoses(response.data.content);
            })
            .catch(error => {
                console.log(error);
            });

        axios.get(urlDrones, {
            headers: {
                'Access-Control-Allow-Origin': '*',
                Authorization: `Bearer ${token}`,
            },
            proxy: {
                port: 8080
            }
        })
            .then(response => {
                // console.log(response.data.content);
                setDrones(response.data.content);
            })
            .catch(error => {
                console.log(error);
            });
    }, [id, urlLastHeartbeat, urlLastTemperature, urlPatient, urlDrones, urlDiagnoses, token]);

    let stompClient;

    useEffect(() => {
        const socket = new SockJS('http://10.20.229.55/websocket-endpoint');
        stompClient = Stomp.over(socket);

        try {
            stompClient.connect({}, () => {
                stompClient.subscribe('/topic/deliveries', (message) => {
                    let newDrone = JSON.parse(message.body);
                    if (newDrone.patient.id === parseInt(id)) {
                        setDrones((prevDrones) => [newDrone, ...prevDrones]);
                    }
                });

                stompClient.subscribe('/topic/diagnosis', (message) => {
                    let newDiagnosis = JSON.parse(message.body);
                    if (newDiagnosis.patient.id === parseInt(id)) {
                        setDiagnoses((prevDiagnoses) => [newDiagnosis, ...prevDiagnoses]);
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
    }, [id]);

    const handleListChange = (list) => {
        console.log(list);
        setCurrentList(list);
    }

    useEffect(() => {
        if (currentList === 'pdrones') {
            setcurrentDroneStatusFilter('PENDING');
        } else if (currentList === 'itdrones') {
            setcurrentDroneStatusFilter('IN_TRANSIT');
        } else if (currentList === 'ddrones') {
            setcurrentDroneStatusFilter('DELIVERED');
        } else if (currentList === 'fdrones') {
            setcurrentDroneStatusFilter('FAILED');
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
                                    <div>Total diagnoses: {!diagnoses || diagnoses.length === 0 ? 0 : diagnoses.length}</div>
                                </div>
                                <PatientInfoList title={"Diagnoses"} dataArray={diagnoses} />
                            </>
                        ) : (
                            <>
                                <div className="align-line-column">
                                    <div className="align-line-row" style={{ gap: "11%" }}>
                                        <div>Total drones: {!drones || drones.length === 0 ? 0 : drones.length}</div>
                                        <div>Pending Drones: {!drones || drones.length === 0 ? 0 : drones.filter(drone => drone.status === 'PENDING').length}</div>
                                        <div>Drones In Transit: {!drones || drones.length === 0 ? 0 : drones.filter(drone => drone.status === 'IN_TRANSIT').length}</div>
                                    </div>
                                    <div className="align-line-row" style={{ gap: "4%", }}>
                                        <div>Delivered Drones: {!drones || drones.length === 0 ? 0 : drones.filter(drone => drone.status === 'DELIVERED').length}</div>
                                        <div>Failed Drones: {!drones || drones.length === 0 ? 0 : drones.filter(drone => drone.status === 'FAILED').length}</div>
                                    </div>
                                </div>
                                <PatientInfoList title={
                                    currentList === "pdrones" ? "Pending Drones" :
                                        currentList === "itdrones" ? "Drones In Transit" :
                                            currentList === "ddrones" ? "Delivered Drones" :
                                                "Failed Drones"}
                                    setCurrentList={handleListChange}
                                    dataArray={drones !== null ? drones.filter(drone => drone.status === currentDroneStatusFilter) : drones} />
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