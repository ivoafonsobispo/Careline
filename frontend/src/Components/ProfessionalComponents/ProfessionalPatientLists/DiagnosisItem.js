import './PatientInfoList.css'
import '../ProfessionalBase.css'
import './DiagnosisItem.css'

import { Download } from 'react-bootstrap-icons';

import axios from 'axios';

export default function DiagnosisItem({ diagnosis }) {
    const urlPdf = `http://localhost:8080/api/patients/${diagnosis.patient.id}/diagnosis/${diagnosis.id}/pdf`;

    // const [pdf, setPdf] = useState(null);
    const pdfDownloadClick = () => {
        axios.get(urlPdf, {
            responseType: 'blob',
            headers: {
                'Access-Control-Allow-Origin': '*',
            },
            proxy: {
                port: 8080
            }
        })
            .then(response => {
                const blob = new Blob([response.data], { type: 'application/pdf' });
                const url = window.URL.createObjectURL(blob);

                // Temporary anchor element
                const a = document.createElement('a');
                a.href = url;
                a.download = `Diagnosis_${diagnosis.id}.pdf`;
                document.body.appendChild(a);
                a.click();
                document.body.removeChild(a);

                // Release the object URL
                window.URL.revokeObjectURL(url);
            })
            .catch(error => {
                // handle the error
                console.log(error);
            });
    };

    return (
        <div className="patient-diagnosis-item-box">
            <div className='align-line-row'>
                Diagnosis {diagnosis.id}
                <span className='professional-diagnosis-download-button' onClick={pdfDownloadClick}><Download size={20} /></span>
            </div>
            <hr></hr>
            <div className='align-line-row' style={{ marginBottom: "1%" }}>
                <b>Description: &nbsp;</b>{diagnosis.diagnosis}
            </div>
            {diagnosis.medications.length !== 0 ? (
                <div style={{ display: "flex", flexDirection: "row" }}>
                    <span>
                        <b>Medications: &nbsp;</b>
                    </span>
                    <div className='pacient-diagnosis-item-information'>
                        {diagnosis.medications.map((medication, index) => {
                            return (
                                <div key={index}>
                                    {medication.name} - {medication.dosage} x {medication.frequency} | {medication.duration}
                                </div>
                            )
                        })}
                    </div>
                </div>
            ) : (<span>No medications prescripted</span>)}
        </div>
    );
}