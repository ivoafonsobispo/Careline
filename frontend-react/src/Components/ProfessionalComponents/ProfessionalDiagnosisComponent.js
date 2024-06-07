import './ProfessionalDiagnosisComponent.css'
import './ProfessionalBase.css'

import { Paperclip, Pen, Download } from 'react-bootstrap-icons'

import axios from 'axios';
import { useSelector } from 'react-redux';

export default function ProfessionalDiagnosisComponent({ diagnosis }) {
    const token = useSelector((state) => state.auth.token);
    const user = useSelector((state) => state.auth.user);	

    const urlPdf = `/professionals/${user.id}/patients/${diagnosis.patient.id}/diagnosis/${diagnosis.id}/pdf`;

    // const [pdf, setPdf] = useState(null);
    const pdfDownloadClick = () => {
        axios.get(urlPdf, {
            responseType: 'blob',
            headers: {
                'Access-Control-Allow-Origin': '*',
                Authorization: `Bearer ${token}`,
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
        <div className="professional-diagnosis-box">
            <div className='align-line-row'>
                <span className='align-line-row'><Paperclip size={20} /> Diagnosis {diagnosis.id}</span>
                <span className='professional-diagnosis-download-button' onClick={pdfDownloadClick}><Download size={20} /></span>
            </div>
            <hr className='professional-diagnosis-hr'></hr>

            <div className='vertical-container client-diagnosis-information-box'>
                <div className='horizontal-container'>
                    <div >
                        <span><b>Patient:</b> </span>
                    </div>
                    <div className='client-diagnosis-information'>
                        <span>{diagnosis.patient.name} | {diagnosis.patient.email} | {diagnosis.patient.nus}</span>
                    </div>
                </div>
                <div className='horizontal-container'>
                    <div >
                        <span><b>Description:</b> </span>
                    </div>
                    <div className='client-diagnosis-information'>
                        <span>{diagnosis.diagnosis}</span>
                    </div>
                </div>

                <div className='horizontal-container'>
                    <div >
                        <span><b>Medications:</b> </span>
                    </div>
                    <div className='client-diagnosis-information'>
                        {diagnosis.medications.map((medication, index) => {
                            return (
                                <div key={index}>
                                    {medication.name} - {medication.dosage} x {medication.frequency} | {medication.duration}
                                </div>
                            )
                        })}
                    </div>
                </div>
            </div>
            <hr className='professional-diagnosis-hr'></hr>

            <div>
                <span className='align-line-row'><Pen size={15} /> &nbsp; By: {diagnosis.professional.name}</span>
            </div>

            <span className='diagnosis-date'>{diagnosis.created_at}</span>
        </div>
    );
}