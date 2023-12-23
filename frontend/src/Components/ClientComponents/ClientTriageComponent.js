import './ClientTriageComponent.css'
import './ClientBase.css'

import {Pencil, Paperclip, Check, Download, ClockHistory} from 'react-bootstrap-icons'

export default function ClientTriageComponent({status}) {
    
    return (
        <div className="client-triage-box">
            <div className='align-line-row'>
                <span className='align-line-row'><Pencil size={15}/> &nbsp;Triage 1</span>
                {/* <span className='diagnosis-download-button' onClick={pdfDownloadClick}><Download size={20}/></span> */}
            </div>
            <hr className='triage-hr'></hr>
            
            <div className='horizontal-container client-triage-information-box'>
                <div className='vertical-container' style={{gap: "10%", width: "24%", minWidth: "24%", marginRight: "3%"}}>
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
            <hr className='triage-hr'></hr>
            
            <div className='horizontal-container'>
                <div>
                    {status === "Reviewed" ? <span className='align-line-row' style={{color: "#3cbd65", fontWeight: "bold"}}><Check size={20}/> &nbsp;Status - Reviewed</span> : <span className='align-line-row' style={{color: "#eb7c49", fontWeight: "bold"}}><ClockHistory size={15}/> &nbsp; Status - Sent</span>}
                </div>

                <div className='align-line-row' style={{marginLeft: "auto", marginRight: "2%"}}>
                    <span className='align-line-row'><Paperclip size={20}/> &nbsp;Diagnosis 1 &nbsp;</span>
                    <span className='align-line-row triage-diagnosis-download-button'><Download size={20}/></span>
                </div>
            </div>

            <span className='triage-date'>WEDNESDAY, 8 OCT AT 15:35 PM</span>
        </div>        
    );
}