import './ClientTriageComponent.css'
import './ClientBase.css'

import { Pencil, Paperclip, Check, Download, ClockHistory } from 'react-bootstrap-icons'

import axios from 'axios';

export default function ClientTriageComponent({ status }) {

    const urlPdf = `http://localhost:8080/api/patients/1/diagnosis/1/pdf`;

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
                a.download = `Diagnosis_1.pdf`;
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
        <div className="client-triage-box">
            <div className='align-line-row'>
                <span className='align-line-row'><Pencil size={15} /> &nbsp;Triage 1</span>
                {/* <span className='diagnosis-download-button' onClick={pdfDownloadClick}><Download size={20}/></span> */}
            </div>
            <hr className='triage-hr'></hr>

            <div className='horizontal-container client-triage-information-box'>
                <div className='vertical-container' style={{ gap: "10%", width: "24%", minWidth: "24%", marginRight: "3%" }}>
                    <div className='align-line-row'>
                        <span><b>Heartbeat:&nbsp;</b></span>
                        <span className='triage-field'>120 BPM</span>
                    </div>
                    <div className='align-line-row'>
                        <span><b>Temperature:&nbsp;</b></span>
                        <span className='triage-field'>50 °C</span>
                    </div>
                </div>
                <div >
                    <span><b>Symptoms: </b></span>
                </div>
                <div className='client-triage-information'>
                    <span > ahahah ah a u ahahah ah a u hdwuhduwh ahahah ah a u hdwuhduwh ahahah ah a u hdwuhduwhahahah ah a u hdwuhduwh ahahah ah a u </span>
                </div>

            </div>
            <hr className='triage-hr'></hr>

            <div className='horizontal-container'>
                <div>
                    {status === "Reviewed" ? <span className='align-line-row' style={{ color: "var(--lightGreen)", fontWeight: "bold" }}><Check size={20} /> &nbsp;Status - Reviewed</span> : <span className='align-line-row' style={{ color: "#eb7c49", fontWeight: "bold" }}><ClockHistory size={15} /> &nbsp; Status - Sent</span>}
                </div>

                {status === "Reviewed" ? (
                    <div className='align-line-row' style={{ marginLeft: "auto", marginRight: "2%" }}>
                        <span className='align-line-row'><Paperclip size={20} /> &nbsp;Diagnosis 1 &nbsp;</span>
                        <span className='align-line-row triage-diagnosis-download-button' onClick={pdfDownloadClick}><Download size={20} /></span>
                    </div>
                ) : (<></>)}
            </div>

            <span className='triage-date'>WEDNESDAY, 8 OCT AT 15:35 PM</span>
        </div>
    );
}