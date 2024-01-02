import PageTitle from "../../Components/PageTitle/PageTitle";
import "../../Components/ClientComponents/ClientBase.css";
import "./ProfessionalTriage.css"
import "./ProfessionalTriageReview.css"
import ProfessionalMedicationList from "../../Components/ProfessionalComponents/ProfessionalMedicationList";

import { useState } from 'react';

import { useParams, useNavigate } from "react-router-dom";
import classNames from "classnames";

import { Pencil, FileMedical} from 'react-bootstrap-icons'

import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

import axios from 'axios';

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

    const { id } = useParams();
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
                setPosition([lat, lon]);
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

            const response = await fetch('http://localhost:8080/api/professionals/1/patients/1/diagnosis', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
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

                droneFields.medications = medications;
                droneFields.coordinate = {
                    "departure_latitude": startPosition[0],
                    "departure_longitude": startPosition[1],
                    "arrival_latitude": position[0],
                    "arrival_longitude": position[1],
                };

                console.log(JSON.stringify(droneFields));

                const responseDrone = await fetch(`http://localhost:8080/api/patients/1/diagnosis/${data.id}/deliveries`, {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json',
                    },
                    body: JSON.stringify(droneFields),
                });

                if (!responseDrone.ok) {
                    console.error('Error:', responseDrone.statusText);
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

    return (
        <div className="horizontal-container">
            <div className="vertical-container">
                <ToastContainer />
                <PageTitle title="Triage Review" />
                <div className='App-content' >
                    <div className="vertical-container" style={{ gap: "4%" }}>
                        <div className="professional-client-triage-box" style={{ fontSize: "16px" }}>
                            <span style={{ margin: "0% 0.7%" }} className="align-line-row"><FileMedical size={20}/> &nbsp;Patient: Alice Johnson &nbsp; | &nbsp; Email: alice@mail.com &nbsp; | &nbsp; NUS: 987654321</span>
                            <hr className='professional-triage-hr'></hr>
                            <div className='horizontal-container client-triage-information-box'>
                                <div className='vertical-container' style={{ gap: "10%", width: "24%", minWidth: "24%", marginRight: "3%" }}>
                                    <div className='align-line-row'>
                                        <span>Heartbeat:&nbsp;</span>
                                        <span className='triage-field'>120 BPM</span>
                                    </div>
                                    <div className='align-line-row'>
                                        <span>Temperature:&nbsp;</span>
                                        <span className='triage-field'>50 °C</span>
                                    </div>
                                </div>
                                <div >
                                    <span>Symptoms: </span>
                                </div>
                                <div className='client-triage-information'>
                                    <span > ahahah ah a u ahahah ah a u hdwuhduwh ahahah ah a u hdwuhduwh ahahah ah a u hdwuhduwhahahah ah a u hdwuhduwh ahahah ah a u </span>
                                </div>

                            </div>
                            <span className='triage-date'>WEDNESDAY, 8 OCT AT 15:35 PM</span>

                        </div>
                        <div className="align-line-column" style={{ fontSize: "16px" }}>
                            <div>Diagnosis:</div>
                            <textarea
                                style={{ minWidth: "98%", maxWidth: "98%", fontSize: "16px", minHeight: "100px" }}
                                onChange={handleDiagnosisChange}
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