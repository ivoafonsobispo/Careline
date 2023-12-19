import './ClientDiagnosis.css'
import './ClientBase.css'

import {Paperclip, Pen} from 'react-bootstrap-icons'

export default function ClientDiagnosis({id, description, prescriptions, professional, date}) {
    return (
        <div className="client-diagnosis-box">
            <span className='align-line-row'><Paperclip size={20}/> Diagnosis {id}</span>
            <hr className='diagnosis-hr'></hr>
            
            <div className='horizontal-container client-diagnosis-information-box'>
                <div >
                <span>Description: </span>
                </div>
                <div className='client-diagnosis-information'>
                    <span>{description}</span>
                </div>

                <div >
                    <span>Prescriptions: </span>
                </div>
                <div className='client-diagnosis-information'>
                    <span>{prescriptions.join(', ')}</span>
                </div>
            </div>
            <hr className='diagnosis-hr'></hr>
            
            <div>
                <span className='align-line-row'><Pen size={15}/> &nbsp; By: {professional}</span>
            </div>
            
            <span className='diagnosis-date'>{date}</span>
        </div>        
    );
}