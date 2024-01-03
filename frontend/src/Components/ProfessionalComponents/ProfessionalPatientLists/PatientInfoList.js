import './PatientInfoList.css'
import '../ProfessionalBase.css'

import { FileEarmarkMedical, Paperclip, BoxSeam } from 'react-bootstrap-icons';
import TriageItem from './TriageItem';
import DiagnosisItem from './DiagnosisItem';

export default function PatientInfoList({ title, dataArray, setCurrentList }) {
    return (
        <div className="professional-info-list-box" style={{maxHeight: "370px"}}>
            <span className='list-title align-line-row'>
                {title === 'Unreviewed Triage' || title === 'Reviewed Triage' ? (
                    <FileEarmarkMedical size={20} color="black" />
                ) : title === 'Diagnoses' ? (
                    <Paperclip size={20} color="black" />
                ) : (
                    <BoxSeam size={20} color="black" />
                )}

                &nbsp; {title}:
                {title === 'Unreviewed Triage' ? (
                    <button className='patient-change-list-button' onClick={() => setCurrentList('rtriage')}>Reviewed Triage</button>
                ) : title === 'Reviewed Triage' ? (
                    <button className='patient-change-list-button' onClick={() => setCurrentList('urtriage')}>Unreviewed Triage</button>
                ) : title === 'Diagnoses' ? (
                    <></>
                ) : (
                    <select onChange={(event) => setCurrentList(event.target.value)} className='patient-info-list-select'>
                        <option className='patient-info-list-select-option' value={'pdrones'}>Pending Drones</option>
                        <option className='patient-info-list-select-option' value={'itdrones'}>Drones In Transit</option>
                        <option className='patient-info-list-select-option' value={'ddrones'}>Delivered Drones</option>
                        <option className='patient-info-list-select-option' value={'fdrones'}>Failed Drones</option>
                    </select>
                    // <button className='patient-change-list-button' onClick={() => setCurrentList('itdrones')}>Drones In Transit</button>
                )}
            </span>

            <div className={`professional-measure-list`}>
                {!dataArray || dataArray.length === 0 ? (
                    <div className='no-records'>
                        {
                            title === "Unreviewed Triage" ? "No unreviewed triage yet." :
                                title === "Reviewed Triage" ? "No reviewed triage yet." :
                                    title === "Diagnoses" ? "No diagnoses prescripted yet." :
                                        title === "Drones In Transit" ? "No drones in transit yet." :
                                            title === "Pending Drones" ? "No pending drones yet." :
                                                title === "Delivered Drones" ? "No delivered drones yet." :
                                                        "No failed drones yet."
                        }
                    </div>
                ) : title === "Unreviewed Triage" ? (
                    <>
                        {dataArray.map((triage, index) => {
                            return (
                                <TriageItem key={index} triage={triage} />
                            )
                        })}
                    </>
                ) : title === "Reviewed Triage" ? (
                    <>
                        {dataArray.map((triage, index) => {
                            return (
                                <TriageItem key={index} triage={triage} />
                            )
                        })}
                    </>
                ) : title === "Diagnoses" ? (
                    <>
                        {dataArray.map((diagnosis, index) => {
                            return (
                                <DiagnosisItem key={index} diagnosis={diagnosis} />
                            )
                        })}
                    </>
                ) : title === "Drones In Transit" ? (
                    <>
                        {dataArray.map((triage, index) => {
                            return (
                                <TriageItem key={index} triage={triage} />
                            )
                        })}
                    </>
                ) : title === "Drones Delivered" ? (
                    <>
                        {dataArray.map((triage, index) => {
                            return (
                                <TriageItem key={index} triage={triage} />
                            )
                        })}
                    </>
                ) : title === "Pending Drones" ? (
                    <>
                        {dataArray.map((triage, index) => {
                            return (
                                <TriageItem key={index} triage={triage} />
                            )
                        })}
                    </>
                ) : title === "Failed Drones" ? (
                    <>
                        {dataArray.map((triage, index) => {
                            return (
                                <TriageItem key={index} triage={triage} />
                            )
                        })}
                    </>
                ) : (<></>)}

            </div>

        </div>
    );
}