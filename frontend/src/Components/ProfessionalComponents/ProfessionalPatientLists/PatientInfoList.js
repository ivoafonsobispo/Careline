import './PatientInfoList.css'
import '../ProfessionalBase.css'

import { FileEarmarkMedical } from 'react-bootstrap-icons';
import TriageItem from './TriageItem';

export default function PatientInfoList({ title, dataArray, setCurrentList }) {
    return (
        <div className="professional-info-list-box">
            <span className='list-title align-line-row'>
                <FileEarmarkMedical size={20} color="black" />
                &nbsp; {title}:
                {title === 'Unreviewed Triage' ? (
                    <button className='patient-change-list-button' onClick={() => setCurrentList('rtriage')}>Reviewed Triage</button>
                ) : title === 'Reviewed Triage' ? (
                    <button className='patient-change-list-button' onClick={() => setCurrentList('urtriage')}>Unreviewed Triage</button>
                ) : title === 'Drones Inshipping' ? (
                    <button className='patient-change-list-button' onClick={() => setCurrentList('sdrones')}>Drones Shipped</button>
                ) : title === 'Drones Shipped' ? (
                    <button className='patient-change-list-button' onClick={() => setCurrentList('isdrones')}>Drones Inshipping</button>
                ) : (<></>)}
            </span>

            <div className={`professional-measure-list`}>
                {!dataArray || dataArray.length === 0 ? (
                    <div className='no-records'>
                        {
                            title === "Unreviewed Triage" ? "No unreviewed triage yet." :
                            title === "Reviewed Triage" ? "No reviewed triage yet." : 
                            title === "Diagnoses" ? "No diagnoses prescripted yet." : 
                            title === "Drones Inshipping" ? "No drones being shipped yet." : 
                            "No drones shipped yet."
                        }
                    </div>
                ) : (
                    <>
                        {dataArray.map((triage, index) => {
                            return (
                                <TriageItem key={index} triage={triage} />
                            )
                        })}
                    </>
                )}

            </div>

        </div>
    );
}