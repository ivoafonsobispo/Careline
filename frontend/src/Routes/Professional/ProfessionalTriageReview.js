import PageTitle from "../../Components/PageTitle/PageTitle";
import "../../Components/ClientComponents/ClientBase.css";
import "./ProfessionalTriage.css"
import "./ProfessionalTriageReview.css"
import ProfessionalMedicationList from "../../Components/ProfessionalComponents/ProfessionalMedicationList";

import { useState } from 'react';

import { useParams, useNavigate } from "react-router-dom";
import classNames from "classnames";


import { toast, ToastContainer } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

export default function ProfessionalTriageReview() {
    const { id } = useParams();
    const navigate = useNavigate();

    const [medications, setMedications] = useState([]);
    const [isMedicationsChecked, setMedicationsCheck] = useState(false);

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

    const handleCheckboxChange = () => {
        setMedicationsCheck(!isMedicationsChecked);
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

            onReviewSuccess();
        } catch (error) {
            // Handle errors during the fetch
            console.error('Error:', error);
        }
    };

    const onReviewSuccess = () => {
        
        toast.success('Triage reviewed successfully!', {
            style: {
                fontSize: '16px',
            },
        });

        navigate('/triage');
    };

    return (
        <div className="horizontal-container">
            <div className="vertical-container">
                <PageTitle title="Triage Review" />
                <div className='App-content' >
                    <div className="vertical-container" style={{ gap: "4%" }}>
                        <div className="professional-client-triage-box" style={{ fontSize: "16px" }}>
                            <span style={{ margin: "0% 1%" }}>Patient: Alice Johnson &nbsp; | &nbsp; Email: alice@mail.com &nbsp; | &nbsp; NUS: 987654321</span>
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

                        <div className={`horizontal-container medications-container ${isMedicationsChecked ? 'show' : ''}`} style={{ fontSize: "16px" }}>
                            <div className={`horizontal-container`} style={{ padding: "2%" }}>
                                <div className="vertical-container" style={{ flexGrow: "1", gap: "8%" }}>
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
                                            className={classNames("professional-triage-button", !(isValidName && isValidDosage && isValidFrequency && isValidDuration) ? "professional-triage-button-inactive" : "")}
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

                        <div className="align-line-row">
                            <button
                                className={classNames("professional-triage-button", !isValidDiagnosis ? "professional-triage-button-inactive" : "")}
                                style={{ margin: "auto" }}
                                onClick={postDiagnosis}
                            >Review</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
}