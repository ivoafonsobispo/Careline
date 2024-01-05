import './ProfessionalTriageComponent.css'
import './ProfessionalBase.css'

import { Pencil, Paperclip, Check, Download, ClockHistory, FileMedical } from 'react-bootstrap-icons'

import axios from 'axios';
import { NavLink } from 'react-router-dom';

import { useSelector } from 'react-redux';

export default function ProfessionalTriageComponent({ status }) {
    const token = useSelector((state) => state.auth.token);

    const urlPdf = `http://10.20.229.55/api/professionals/1/patients/1/diagnosis/1/pdf`;

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
        <div className="professional-client-triage-box">
            <div className='align-line-row'>
                <span className='align-line-row' style={{marginLeft: "0.8%"}}><FileMedical size={20} /> &nbsp;Triage 1</span>
                <span style={{ marginLeft: "auto", marginRight: "2%" }}>Patient 1</span>
            </div>
            <hr className='professional-triage-hr'></hr>

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
            <hr className='professional-triage-hr'></hr>

            <div className='align-line-row'>
                <div>
                    {status === "Reviewed" ? <span className='align-line-row' style={{ color: "var(--lightGreen)", fontWeight: "bold" }}><Check size={20} /> &nbsp;Status - Reviewed</span> : <span className='align-line-row' style={{ color: "#eb7c49", fontWeight: "bold" }}><ClockHistory size={15} /> &nbsp; Status - Unreviewed</span>}
                </div>

                {status === "Reviewed" ? (
                    <div className='align-line-row' style={{ marginLeft: "auto", marginRight: "2%" }}>
                        <span className='align-line-row'><Paperclip size={20} /> &nbsp;Diagnosis 1 &nbsp;</span>
                        <span className='align-line-row professional-triage-diagnosis-download-button' onClick={pdfDownloadClick}><Download size={20} /></span>
                    </div>
                ) : (
                    <NavLink to={'/triage/1/review'} className='professional-triage-button align-line-row' style={{ marginLeft: "auto", marginRight: "2%", padding: "1% 0%", fontSize: "16px" }}>
                        <span className='align-line-row' style={{margin: "auto"}}>
                            <Pencil size={13} /> &nbsp; Review
                        </span>
                    </NavLink>
                )}

            </div>

            <span className='triage-date'>WEDNESDAY, 8 OCT AT 15:35 PM</span>
        </div>
    );
}