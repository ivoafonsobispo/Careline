import PageTitle from "../../Components/PageTitle/PageTitle";
import "../../Components/ClientComponents/ClientBase.css";
import "./ClientDrone.css"

import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useParams } from 'react-router-dom';

import { Check, ClockHistory, X, BoxSeam } from 'react-bootstrap-icons'

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
    const { id } = useParams();
    const [drone, setDrone] = useState(null);
    const [startPosition, setStartPosition] = useState([0, 0]);
    const [endPosition, setEndPosition] = useState([0, 0]);
    const [mapKey, setMapKey] = useState(0);

    const [animate, setAnimate] = useState(false);
    const [currentPosition, setCurrentPosition] = useState([0, 0]);

    const urlDrone = `http://localhost:8080/api/patients/1/deliveries/${id}`;

    useEffect(() => {
        axios.get(urlDrone, {
            headers: {
                'Access-Control-Allow-Origin': '*',
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

        // setDrone({
        //     "id": 1,
        //     "patient": {
        //         "id": 1,
        //         "name": "Alice Johnson",
        //         "email": "alice@mail.com",
        //         "nus": "987654321"
        //     },
        //     "medications": [
        //         "a"
        //     ],
        //     "status": "DELIVERED",
        //     "coordinate": {
        //         "departure_latitude": 39.734,
        //         "departure_longitude": -8.821,
        //         "arrival_latitude": 39.742,
        //         "arrival_longitude": -8.775
        //     },
        //     "created_at": "Wed, Jan 03 AT 11:06",
        //     "departure_time": "Wed, Jan 03 AT 11:06",
        //     "arrival_time": "Thu, Jan 01 AT 01:00"
        // });
    }, []);


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
                }
            }, 1000); // Update every second (adjust as needed)
        };

        if (animate) {
            simulateDroneMovement();
        }
    }, [startPosition, endPosition, animate]);

    if (drone === null) return;

    return (
        <div className="vertical-container">
            <PageTitle title="Drone" />
            <div className='App-content'>
                <div className="vertical-container">
                    <div className='align-line-row client-triage-information-box'>
                        <div className='vertical-container' style={{ gap: "10%", width: "24%", minWidth: "40%", marginRight: "3%" }}>
                            <div className='align-line-row'>
                                <span><b>&#91;</b>{drone.coordinate.departure_latitude}, {drone.coordinate.departure_longitude}<b>&#93;</b> &nbsp; &#8594; &nbsp; <b>&#91;</b>{drone.coordinate.arrival_latitude}, {drone.coordinate.arrival_longitude}<b>&#93;</b></span>
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
        </div>
    );
}