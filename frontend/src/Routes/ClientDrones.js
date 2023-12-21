import PageTitle from "../Components/PageTitle/PageTitle";
import "../Components/ClientComponents/ClientBase.css";

import React, { useState, useEffect } from 'react';

import { Map, TileLayer, Marker, Popup } from 'leaflet';
import 'leaflet/dist/leaflet.css'


export default function ClientDrones() {

    // useEffect(() => {
    //     // Create a map
    //     const map = L.map('map').setView([51.505, -0.09], 13);
    
    //     // Add a tile layer
    //     L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
    //       attribution: '&copy; OpenStreetMap contributors',
    //     }).addTo(map);
    
    //     // Add a marker
    //     L.marker([51.505, -0.09])
    //       .addTo(map)
    //       .bindPopup('A pretty CSS3 popup.<br> Easily customizable.')
    //       .openPopup();
    //   }, []);

    return(
        <div className="vertical-container">
            <PageTitle title="Drones"/>
            <div className='App-content'>
                <Map center={[51.505, -0.09]} zoom={13} style={{ height: '400px' }}>
                    <TileLayer
                        url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
                        attribution='&copy; OpenStreetMap contributors'
                    />
                    <Marker position={[51.505, -0.09]}>
                        <Popup>A pretty CSS3 popup.<br/> Easily customizable.</Popup>
                    </Marker>
                </Map>
            </div>
        </div>
    );
}