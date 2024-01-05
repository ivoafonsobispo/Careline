import './ProfessionalTriageComponent.css'
import './ProfessionalBase.css'

import { Pencil, Check, ClockHistory, FileMedical } from 'react-bootstrap-icons'

import { NavLink } from 'react-router-dom';

export default function ProfessionalTriageComponent({ triage }) {

    return (
        <div className="professional-client-triage-box">
            <div className='align-line-row'>
                <span className='align-line-row' style={{ marginLeft: "0.8%" }}><FileMedical size={20} /> &nbsp;Triage {triage.id}</span>
                <span style={{ marginLeft: "auto", marginRight: "2%" }}>Patient {triage.patient.id}</span>
            </div>
            <hr className='professional-triage-hr'></hr>

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
            <hr className='professional-triage-hr'></hr>

            <div className='align-line-row'>
                <div>
                    {triage.status === "REVIEWED" ? <span className='align-line-row' style={{ color: "var(--lightGreen)", fontWeight: "bold" }}><Check size={20} /> &nbsp;Status - Reviewed</span> : <span className='align-line-row' style={{ color: "#eb7c49", fontWeight: "bold" }}><ClockHistory size={15} /> &nbsp; Status - Unreviewed</span>}
                </div>

                {triage.status === "REVIEWED" ? (
                   <></>
                ) : (
                    <NavLink to={'/triage/1/review'} className='professional-triage-button align-line-row' style={{ marginLeft: "auto", marginRight: "2%", padding: "1% 0%", fontSize: "16px" }}>
                        <span className='align-line-row' style={{ margin: "auto" }}>
                            <Pencil size={13} /> &nbsp; Review
                        </span>
                    </NavLink>
                )}

            </div>

            <span className='triage-date'>{triage.created_at}</span>
        </div>
    );
}