import PageTitle from "../../Components/PageTitle/PageTitle";
import "../../Components/ClientComponents/ClientBase.css";

import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useParams } from 'react-router-dom';

import L from 'leaflet';
import { MapContainer, TileLayer, Marker, Popup } from 'react-leaflet'
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
    }, []);

    return (
        <div className="vertical-container">
            <PageTitle title="Drone" />
            <div className='App-content'>
                <MapContainer center={[51.505, -0.09]} zoom={15} scrollWheelZoom={false} style={{ height: "80%", width: "80%" }}>
                    <TileLayer
                        attribution='<a href="https://www.maptiler.com/copyright/" target="_blank">&copy; MapTiler</a> <a href="https://www.openstreetmap.org/copyright" target="_blank">&copy; OpenStreetMap contributors</a>'
                        url="https://api.maptiler.com/maps/streets-v2/{z}/{x}/{y}.png?key=5jMVN99UbOfedO1jfCJL"
                    />
                    <Marker position={[51.505, -0.09]}>
                        <Popup>
                            A pretty CSS3 popup. <br /> Easily customizable.
                        </Popup>
                    </Marker>
                </MapContainer>
            </div>
        </div>
    );
}