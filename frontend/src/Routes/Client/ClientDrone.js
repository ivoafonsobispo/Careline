import PageTitle from "../../Components/PageTitle/PageTitle";
import "../../Components/ClientComponents/ClientBase.css";
import "./ClientDrone.css"

import Stomp from 'stompjs';
import SockJS from 'sockjs-client';

import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useParams } from 'react-router-dom';

import { useSelector } from "react-redux";

import { Check, ClockHistory, X } from 'react-bootstrap-icons'

import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

import L from 'leaflet';
import { MapContainer, TileLayer, Marker, Popup, Polyline } from 'react-leaflet'
import 'leaflet/dist/leaflet.css';

import icon from 'leaflet/dist/images/marker-icon.png';
import iconShadow from 'leaflet/dist/images/marker-shadow.png';

let DefaultIcon = L.icon({
    iconUrl: icon,
    shadowUrl: iconShadow,
    iconSize: [25, 41],
    iconAnchor: [12, 41],
    popupAnchor: [0, -30]
});

L.Marker.prototype.options.icon = DefaultIcon;


export default function ClientDrone() {
    const token = useSelector((state) => state.auth.token);
    const user = useSelector((state) => state.auth.user);

    const { id } = useParams();
    const [drone, setDrone] = useState(null);
    const [startPosition, setStartPosition] = useState([0, 0]);
    const [endPosition, setEndPosition] = useState([0, 0]);
    const [mapKey, setMapKey] = useState(0);

    const [animate, setAnimate] = useState(false);
    const [currentPosition, setCurrentPosition] = useState([0, 0]);

    const urlDrone = `http://10.20.229.55/api/patients/${user.id}/deliveries/${id}`;

    const patchDrone = async () => {
        const responsePatch = await fetch(`http://10.20.229.55/api/patients/${drone.patient.id}/deliveries/${id}/delivered`, {
            method: 'PATCH',
            headers: {
                'Content-Type': 'application/json',
                Authorization: `Bearer ${token}`,
            },
        });

        if (!responsePatch.ok) {
            console.error('Error:', responsePatch.statusText);
            return;
        }

        
        setAnimate(false);
        drone.status = 'DELIVERED';
        toast.success('Drone delivered successfully!', {
            style: {
                fontSize: '16px',
            },
        });
    };

    useEffect(() => {
        axios.get(urlDrone, {
            headers: {
                'Access-Control-Allow-Origin': '*',
                Authorization: `Bearer ${token}`,
            },
            proxy: {
                port: 8080
            }
        })
            .then(response => {
                console.log(response.data);
                setDrone(response.data);
            })
            .catch(error => {
                console.log(error);
            });
    }, [urlDrone, token]);


    useEffect(() => {
        if (drone !== null) {
            setStartPosition([drone.coordinate.departure_latitude, drone.coordinate.departure_longitude]);
            setEndPosition([drone.coordinate.arrival_latitude, drone.coordinate.arrival_longitude]);

            if (drone.status === 'PENDING') {
                setCurrentPosition([drone.coordinate.departure_latitude, drone.coordinate.departure_longitude]);
            } else if (drone.status === 'IN_TRANSIT') {
                setCurrentPosition([drone.coordinate.departure_latitude, drone.coordinate.departure_longitude]);
                setAnimate(true);
            } else if (drone.status === 'DELIVERED') {
                setCurrentPosition([drone.coordinate.arrival_latitude, drone.coordinate.arrival_longitude]);
            }
            setMapKey((prevKey) => prevKey + 1);
        }
    }, [drone]);

    useEffect(() => {
        const interpolatePoints = (start, end, fraction) => [
            start[0] + (end[0] - start[0]) * fraction,
            start[1] + (end[1] - start[1]) * fraction,
        ];

        const simulateDroneMovement = () => {
            let fraction = 0.05;

            const interval = setInterval(() => {
                if (fraction < 1.05) {
                    setCurrentPosition(interpolatePoints(startPosition, endPosition, fraction));
                    setMapKey((prevKey) => prevKey + 1);
                    fraction += 0.05;
                } else {
                    clearInterval(interval);
                    patchDrone();
                }
            }, 1000); // Update every second (adjust as needed)
        };

        if (animate) {
            simulateDroneMovement();
        }
    }, [startPosition, endPosition, animate]);

    let stompClient;

    useEffect(() => {
        const socket = new SockJS('http://10.20.229.55/websocket-endpoint');
        stompClient = Stomp.over(socket);
        try {

            if (drone !== null) {
                stompClient.connect({}, () => {
                    stompClient.subscribe('/topic/deliveries', (message) => {
                        let newDrone = JSON.parse(message.body);
                        if (newDrone.id === drone.id) {
                            setDrone(newDrone);
                        }
                    });
                });
            }

        } catch (error) {
            console.error('WebSocket connection error:', error);
            // Handle the error here, e.g., show a user-friendly message or retry the connection
        }

        return () => {
            if (stompClient && stompClient.connected) {
                stompClient.disconnect();
            }
        };
    }, [drone]);


    if (drone === null) return;

    return (
        <div className="vertical-container">
            <PageTitle title="Drone" />
            <div className='App-content'>
                <div className="vertical-container">
                    <div className='align-line-row client-triage-information-box'>
                        <div className='vertical-container' style={{ gap: "10%", width: "24%", minWidth: "40%", marginRight: "3%" }}>
                            <div className='align-line-row'>
                                <span><b>&#91;</b>{(drone.coordinate.departure_latitude).toFixed(3)}, {(drone.coordinate.departure_longitude).toFixed(3)}<b>&#93;</b> &nbsp; &#8594; &nbsp; <b>&#91;</b>{(drone.coordinate.arrival_latitude).toFixed(3)}, {(drone.coordinate.arrival_longitude).toFixed(3)}<b>&#93;</b></span>
                            </div>
                            <div className='align-line-column'>
                                <div className='align-line-row'>
                                    <span><b>Departure date:</b> &nbsp; </span>
                                    <span className='triage-field'>
                                        {drone.status === 'PENDING' ? (
                                            <span>-----</span>
                                        ) : (
                                            <span className='triage-date-field'>{drone.departure_time}</span>
                                        )}

                                    </span>
                                </div>
                                <div className='align-line-row'>
                                    <span><b>Arrival date:</b> &nbsp; </span>
                                    <span className='triage-field'>
                                        {drone.status === 'PENDING' || drone.status === 'IN_TRANSIT' ? (
                                            <span>-----</span>
                                        ) : (
                                            <span className='triage-date-field'>{drone.arrival_time}</span>
                                        )}

                                    </span>
                                </div>
                            </div>
                        </div>
                        <div >
                            <span><b>Medications: </b></span>
                        </div>
                        <div className='client-triage-information'>
                            <span > {drone.medications.join(', ')} </span>
                        </div>
                    </div>

                    <div className='align-line-row' style={{ margin: "2% 1%" }}>
                        {drone.status === "PENDING" ?
                            <span className='align-line-row' style={{ color: "var(--pendingBlue)", fontWeight: "bold" }}>
                                <ClockHistory size={15} /> &nbsp;Status - Pending
                            </span>
                            : drone.status === "IN_TRANSIT" ?
                                <span className='align-line-row' style={{ color: "#eb7c49", fontWeight: "bold" }}>
                                    <ClockHistory size={15} /> &nbsp; Status - In Transit
                                </span>
                                : drone.status === "DELIVERED" ?
                                    <span className='align-line-row' style={{ color: "var(--lightGreen)", fontWeight: "bold" }}>
                                        <Check size={20} /> &nbsp; Status - Delivered
                                    </span>
                                    :
                                    <span className='align-line-row' style={{ color: "red", fontWeight: "bold" }}>
                                        <X size={20} /> &nbsp; Status - Failed
                                    </span>
                        }
                    </div>

                    <MapContainer key={mapKey} center={[currentPosition[0], currentPosition[1]]} zoom={13} scrollWheelZoom={false} style={{ height: "340px", width: "100%", margin: "auto" }}>
                        <TileLayer
                            attribution='<a href="https://www.maptiler.com/copyright/" target="_blank">&copy; MapTiler</a> <a href="https://www.openstreetmap.org/copyright" target="_blank">&copy; OpenStreetMap contributors</a>'
                            url="https://api.maptiler.com/maps/streets-v2/{z}/{x}/{y}.png?key=5jMVN99UbOfedO1jfCJL"
                        />
                        <Marker position={[startPosition[0], startPosition[1]]}>
                            <Popup>
                                Starting point
                            </Popup>
                        </Marker>
                        <Marker position={[endPosition[0], endPosition[1]]}>
                            <Popup>
                                Ending point
                            </Popup>
                        </Marker>

                        <Polyline positions={[startPosition, endPosition]} color="var(--basecolor)" />

                        {drone.status !== 'PENDING' ? (
                            <Marker position={[currentPosition[0], currentPosition[1]]} id="current">
                                <Popup>
                                    Current position
                                </Popup>
                            </Marker>
                        ) : (<></>)}
                    </MapContainer>
                </div>
            </div>
            <ToastContainer />
        </div>
    );
}