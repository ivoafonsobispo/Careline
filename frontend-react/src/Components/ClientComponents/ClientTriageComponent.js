import './ClientTriageComponent.css'
import './ClientBase.css'

import { FileMedical, Check, ClockHistory } from 'react-bootstrap-icons'

export default function ClientTriageComponent({ triage }) {

    return (
        <div className="client-triage-box">
            <div className='align-line-row'>
                <span className='align-line-row'><FileMedical size={15} /> &nbsp;Triage {triage.id}</span>
                {/* <span className='diagnosis-download-button' onClick={pdfDownloadClick}><Download size={20}/></span> */}
            </div>
            <hr className='triage-hr'></hr>

            <div className='horizontal-container client-triage-information-box'>
                <div className='vertical-container' style={{ gap: "10%", width: "30%", minWidth: "30%", marginRight: "3%" }}>
                    <div className='align-line-row'>
                        <span><b>Heartbeat:&nbsp;</b></span>
                        <span className='triage-field'>{triage.heartbeat} BPM</span>
                    </div>
                    <div className='align-line-row'>
                        <span><b>Temperature:&nbsp;</b></span>
                        <span className='triage-field'>{triage.temperature} °C</span>
                    </div>
                </div>
                <div >
                    <span><b>Symptoms: </b></span>
                </div>
                <div className='client-triage-information'>
                    <span > {triage.symptoms} </span>
                </div>

            </div>
            <hr className='triage-hr'></hr>

            <div className='horizontal-container'>
                <div>
                    {triage.status === "REVIEWED" ? <span className='align-line-row' style={{ color: "var(--lightGreen)", fontWeight: "bold" }}><Check size={20} /> &nbsp;Status - Reviewed</span> : <span className='align-line-row' style={{ color: "#eb7c49", fontWeight: "bold" }}><ClockHistory size={15} /> &nbsp; Status - Sent</span>}
                </div>
            </div>

            <span className='triage-date'>{triage.created_at}</span>
        </div>
    );
}