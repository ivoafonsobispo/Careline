import './ClientDiagnosis.css'
import './ClientBase.css'

import { Paperclip, Pen, Download } from 'react-bootstrap-icons'

import axios from 'axios';

import { useSelector } from 'react-redux';

export default function ClientDiagnosis({ diagnosis }) {

    const user = useSelector((state) => state.auth.user);	
    const token = useSelector((state) => state.auth.token);	

    const urlPdf = `/patients/${user.id}/diagnosis/${diagnosis.id}/pdf`;

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
        <div className="client-diagnosis-box">
            <div className='align-line-row'>
                <span className='align-line-row'><Paperclip size={20} /> Diagnosis {diagnosis.id}</span>
                <span className='diagnosis-download-button' onClick={pdfDownloadClick}><Download size={20} /></span>
            </div>
            <hr className='diagnosis-hr'></hr>

            <div className='vertical-container client-diagnosis-information-box'>
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
            <hr className='diagnosis-hr'></hr>

            <div>
                <span className='align-line-row'><Pen size={15} /> &nbsp; By: {diagnosis.professional.name}</span>
            </div>

            <span className='diagnosis-date'>{diagnosis.created_at}</span>
        </div>
    );
}