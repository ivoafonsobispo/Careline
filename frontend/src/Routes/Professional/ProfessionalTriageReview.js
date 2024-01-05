import PageTitle from "../../Components/PageTitle/PageTitle";
import "../../Components/ClientComponents/ClientBase.css";
import "./ProfessionalTriage.css"
import "./ProfessionalTriageReview.css"
import ProfessionalMedicationList from "../../Components/ProfessionalComponents/ProfessionalMedicationList";

import { useState } from 'react';

import { useParams, useNavigate } from "react-router-dom";
import classNames from "classnames";

import { Pencil, FileMedical } from 'react-bootstrap-icons'

import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

import axios from 'axios';

import { useSelector } from "react-redux";
import { useEffect } from "react";

import L from 'leaflet';
import { MapContainer, TileLayer, Marker, Popup, useMapEvents } from 'react-leaflet'
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


export default function ProfessionalTriageReview() {
    const token = useSelector((state) => state.auth.token);	
    const user = useSelector((state) => state.auth.user);	

    const { id } = useParams();
    const [triage, setTriage] = useState(null);

    const urlTriage = `http://10.20.229.55/api/triages/${id}`;
    useEffect(() => {
        axios.get(urlTriage, {
            headers: {
                'Access-Control-Allow-Origin': '*',
                Authorization: `Bearer ${token}`,
            },
            proxy: {
                port: 8080
            }
        })
            .then(response => {
                setTriage(response.data);
                // console.log(response.data.content);
            })
            .catch(error => {
                console.log(error);
            });
    }, [urlTriage, token]);

    const navigate = useNavigate();

    const [medications, setMedications] = useState([]);
    const [isMedicationsChecked, setMedicationsCheck] = useState(false);

    const [isSendDroneChecked, setIsSendDroneChecked] = useState(false);

    const [diagnosis, setDiagnosis] = useState("");
    const [isValidDiagnosis, setIsValidDiagnosis] = useState(false);

    const [medicationName, setMedicationName] = useState("");
    const [isValidName, setIsValidName] = useState(false);
    const [medicationDosage, setMedicationDosage] = useState("");
    const [isValidDosage, setIsValidDosage] = useState(false);
    const [medicationFrequency, setMedicationFrequency] = useState("");
    const [isValidFrequency, setIsValidFrequency] = useState(false);
    const [medicationDuration, setMedicationDuration] = useState("");
    const [isValidDuration, setIsValidDuration] = useState(false);

    const [destination, setDestination] = useState('');
    const [position, setPosition] = useState([39.734, -8.821]);
    const startPosition = [39.734, -8.821];
    const [mapKey, setMapKey] = useState(0);

    const handleGeocoding = (e) => {
        setDestination(e.target.value);
    };

    const handleSearch = async (e) => {
        e.preventDefault();

        try {
            const response = await axios.get(`https://nominatim.openstreetmap.org/search?format=json&q=${destination}`);
            console.log(response.data);

            if (response.data.length > 0) {
                const { lat, lon } = response.data[0];
                setPosition([parseFloat(lat), parseFloat(lon)]);
                setMapKey((prevKey) => prevKey + 1);
            } else {
                console.log("WATNING");
                toast.error('Error finding location. Please try again.');
            }
        } catch (error) {
            console.error('Error during geocoding:', error);
        }
    };

    function MapClickHandler({ onLocationClick }) {
        const handleMapClick = async (e) => {
            const { lat, lng } = e.latlng;

            // Reverse geocoding to get the address from coordinates
            try {
                const response = await axios.get(`https://nominatim.openstreetmap.org/reverse?format=json&lat=${lat}&lon=${lng}`);
                const address = response.data.display_name;
                onLocationClick([lat, lng, address]);
            } catch (error) {
                console.error('Error during reverse geocoding:', error);
            }
        };

        useMapEvents({
            click: (e) => {
                handleMapClick(e);
            },
        });

        return null;
    }

    const updateMapStateAfterClick = (newPosition) => {
        setPosition([newPosition[0], newPosition[1]]);
        setDestination(newPosition[2]);
    };


    const handleCheckboxChange = () => {
        setMedicationsCheck(!isMedicationsChecked);
    };

    const handleCheckboxDroneChange = () => {
        setIsSendDroneChecked(!isSendDroneChecked);
    };

    const handleDiagnosisChange = (e) => {
        setDiagnosis(e.target.value);
        const diagnosisRegex = /^[^\s@]/;
        const isValidDiagnosis = diagnosisRegex.test(e.target.value);
        setIsValidDiagnosis(isValidDiagnosis);
    };

    const handleNameChange = (e) => {
        setMedicationName(e.target.value);
        const nameRegex = /^[^\s@]/;
        const isValidName = nameRegex.test(e.target.value);
        setIsValidName(isValidName);
    };

    const handleDosageChange = (e) => {
        setMedicationDosage(e.target.value);
        const dosageRegex = /^[^\s@]/;
        const isValidDosage = dosageRegex.test(e.target.value);
        setIsValidDosage(isValidDosage);
    };

    const handleFrequencyChange = (e) => {
        setMedicationFrequency(e.target.value);
        const frenquencyRegex = /^[^\s@]/;
        const isValidFrequency = frenquencyRegex.test(e.target.value);
        setIsValidFrequency(isValidFrequency);
    };

    const handleDurationChange = (e) => {
        setMedicationDuration(e.target.value);
        const durationRegex = /^[^\s@]/;
        const isValidDuration = durationRegex.test(e.target.value);
        setIsValidDuration(isValidDuration);
    };

    const addMedicationClicked = () => {
        let currentMedication = {
            "name": medicationName,
            "dosage": medicationDosage,
            "frequency": medicationFrequency,
            "duration": medicationDuration
        };

        setMedications((prevMedications) => [...prevMedications, currentMedication]);
    };

    const handleDeleteMedication = (index) => {
        const updatedMedications = [...medications.slice(0, index), ...medications.slice(index + 1)];
        setMedications(updatedMedications);
    };

    const postDiagnosis = async () => {
        try {
            const diganosisFields = {};

            diganosisFields.diagnosis = diagnosis;

            if (medications.length !== 0) {
                diganosisFields.medications = medications;
            } else {
                setIsSendDroneChecked(false);
            }

            console.log(diganosisFields);
            console.log(JSON.stringify(diganosisFields));

            //TODO - Fazer patch para alterar estado da triagem para reviewed

            const response = await fetch(`http://10.20.229.55/api/professionals/${user.id}/patients/1/diagnosis`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    Authorization: `Bearer ${token}`,
                },
                body: JSON.stringify(diganosisFields),
            });

            if (!response.ok) {
                console.error('Error:', response.statusText);
                return;
            }

            const data = await response.json();

            if (isSendDroneChecked && medications.length !== 0) {

                const droneFields = {};
                console.log(position);

                // const medicationNames = 
                droneFields.medications = medications.map(medication => medication.name);
                droneFields.coordinate = {
                    "departure_latitude": parseFloat(startPosition[0].toFixed(3)),
                    "departure_longitude": parseFloat(startPosition[1].toFixed(3)),
                    "arrival_latitude": parseFloat(position[0].toFixed(3)),
                    "arrival_longitude": parseFloat(position[1].toFixed(3)),
                };

                console.log(JSON.stringify(droneFields));

                const responseDrone = await fetch(`http://10.20.229.55/api/patients/1/diagnosis/${data.id}/deliveries`, {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json',
                        Authorization: `Bearer ${token}`,
                    },
                    body: JSON.stringify(droneFields),
                });

                if (!responseDrone.ok) {
                    console.error('Error:', responseDrone.statusText);
                    return;
                }

                const responsePatch = await fetch(`http://10.20.229.55/api/patients/${triage.patient.id}/triages/${id}/reviewed`, {
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


                onReviewSuccess('Drone set up successfully!');
            }

            onReviewSuccess('Triage reviewed successfully!');
        } catch (error) {
            // Handle errors during the fetch
            console.error('Error:', error);
        }
    };

    const onReviewSuccess = (message) => {
        toast.success(message, {
            style: {
                fontSize: '16px',
            },
            onClose: () => {
                // Delay the navigation or navigate here after the toast is closed
                navigate('/triage');
            },
        });
    };

    if(!triage || triage === null) return

    return (
        <div className="horizontal-container">
            <div className="vertical-container">
                <ToastContainer />
                <PageTitle title="Triage Review" />
                <div className='App-content' >
                    <div className="vertical-container" style={{ gap: "4%" }}>
                        <div className="professional-client-triage-box" style={{ fontSize: "16px" }}>
                            <span style={{ margin: "0% 0.7%" }} className="align-line-row"><FileMedical size={20} /> &nbsp;Patient: {triage.patient.name} &nbsp; | &nbsp; Email: {triage.patient.email} &nbsp; | &nbsp; NUS: {triage.patient.nus}</span>
                            <hr className='professional-triage-hr'></hr>
                            <div className='horizontal-container client-triage-information-box'>
                                <div className='vertical-container' style={{ gap: "10%", width: "24%", minWidth: "24%", marginRight: "3%" }}>
                                    <div className='align-line-row'>
                                        <span>Heartbeat:&nbsp;</span>
                                        <span className='triage-field'>{triage.heartbeat} BPM</span>
                                    </div>
                                    <div className='align-line-row'>
                                        <span>Temperature:&nbsp;</span>
                                        <span className='triage-field'>{triage.temperature} °C</span>
                                    </div>
                                </div>
                                <div >
                                    <span>Symptoms: </span>
                                </div>
                                <div className='client-triage-information'>
                                    <span > {triage.symptoms} </span>
                                </div>

                            </div>
                            <span className='triage-date'>{triage.created_at}</span>

                        </div>
                        <div className="align-line-column" style={{ fontSize: "16px" }}>
                            <div style={{marginBottom: "1%"}}>Diagnosis:</div>
                            <textarea
                                style={{ minWidth: "98%", maxWidth: "98%", fontSize: "16px", minHeight: "100px" }}
                                onChange={handleDiagnosisChange}
                                className="professional-diagnosis-field"
                            ></textarea>
                        </div>

                        <div className="align-line-row" style={{ fontSize: "16px" }}>
                            <input
                                type="checkbox"
                                id="medications" name="medications"
                                checked={isMedicationsChecked}
                                onChange={handleCheckboxChange} />
                            <label > &nbsp; Medications</label>
                        </div>

                        <div className={`vertical-container  medications-container ${isMedicationsChecked ? 'show' : ''}`} style={{ height: medications.length === 0 && isMedicationsChecked ? "400px" : medications.length !== 0 && isMedicationsChecked ? isSendDroneChecked ? "1050px" : "480px" : "" }}>
                            <div className={`horizontal-container`} style={{ fontSize: "16px", height: "400px" }}>
                                <div className={`horizontal-container`} style={{ padding: "2%" }}>
                                    <div className="vertical-container" style={{ flexGrow: "1", gap: "8%", height: "240px" }}>
                                        <div className="align-line-row">
                                            <span style={{ width: "25%" }}>Name: &nbsp;</span>
                                            <input
                                                id="medication-name"
                                                name="medication-name"
                                                className="professional-triage-medication-field"
                                                onChange={handleNameChange}
                                            />
                                        </div>
                                        <div className="align-line-row">
                                            <span style={{ width: "25%" }}>Dosage: &nbsp;</span>
                                            <input
                                                id="medication-dosage"
                                                name="medication-dosage"
                                                className="professional-triage-medication-field"
                                                onChange={handleDosageChange} />
                                        </div>
                                        <div className="align-line-row">
                                            <span style={{ width: "25%" }}>Frequency: &nbsp;</span>
                                            <input
                                                id="medication-frequency"
                                                name="medication-frequency"
                                                className="professional-triage-medication-field"
                                                onChange={handleFrequencyChange} />
                                        </div>
                                        <div className="align-line-row">
                                            <span style={{ width: "25%" }}>Duration: &nbsp;</span>
                                            <input
                                                id="medication-duration"
                                                name="medication-duration"
                                                className="professional-triage-medication-field"
                                                onChange={handleDurationChange} />
                                        </div>
                                        <div className="align-line-row">
                                            <button
                                                className={classNames("professional-medication-button", !(isValidName && isValidDosage && isValidFrequency && isValidDuration) ? "professional-medication-button-inactive" : "")}
                                                style={{ marginLeft: "auto", marginRight: "23%" }}
                                                onClick={addMedicationClicked}>
                                                Add medication</button>
                                        </div>

                                    </div>
                                    <div className="vertical-container" style={{ flexGrow: "1", height: "85%" }}>
                                        <ProfessionalMedicationList dataArray={medications} onDeleteMedication={handleDeleteMedication} />
                                    </div>
                                </div>

                            </div>
                            {medications.length !== 0 ? (
                                <div className="align-line-row" style={{ fontSize: "16px", padding: "0% 2%", marginBottom: "2%" }}>
                                    <input
                                        type="checkbox"
                                        id="sendDrone" name="sendDrone"
                                        checked={isSendDroneChecked}
                                        onChange={handleCheckboxDroneChange} />
                                    <label > &nbsp; Send Drone</label>
                                </div>
                            ) : (<></>)}
                            {isSendDroneChecked && medications.length !== 0 ? (
                                <div className={`vertical-container`} style={{ height: "65%" }}>
                                    <div className="floating-form">
                                        <form onSubmit={handleSearch}>
                                            <input type="text" onChange={handleGeocoding} className="professional-triage-map-search" />
                                            <button type="submit" className="professional-medication-button" style={{ marginLeft: "auto" }}>Search</button>
                                        </form>
                                    </div>
                                    <MapContainer key={mapKey} center={position} zoom={13} scrollWheelZoom={false} style={{ height: "400px", width: "80%", margin: "auto", bottom: "45px" }}>
                                        <MapClickHandler onLocationClick={updateMapStateAfterClick} />
                                        <TileLayer
                                            url="https://api.maptiler.com/maps/streets-v2/{z}/{x}/{y}.png?key=5jMVN99UbOfedO1jfCJL"
                                            attribution='<a href="https://www.maptiler.com/copyright/" target="_blank">&copy; MapTiler</a> <a href="https://www.openstreetmap.org/copyright" target="_blank">&copy; OpenStreetMap contributors</a>'
                                        />
                                        <Marker key={position.join(',')} position={position}>
                                            <Popup>You selected {destination}</Popup>
                                        </Marker>
                                    </MapContainer>
                                </div>
                            ) : (<></>)}
                        </div>

                        <div className="align-line-row">
                            <button
                                className={classNames("professional-medication-button align-line-row", !isValidDiagnosis ? "professional-medication-button-inactive" : "")}
                                style={{ margin: "auto" }}
                                onClick={postDiagnosis}
                            ><Pencil size={13} /> &nbsp; Review</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
}