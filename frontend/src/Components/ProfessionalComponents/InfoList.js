import './InfoList.css'
import './ProfessionalBase.css'

import { NavLink } from 'react-router-dom';

import {Person, Paperclip} from 'react-bootstrap-icons';
import InfoItem from './InfoItem';

export default function MeasureList({title, dataArray}) {
    return (
        <div className="professional-info-list-box">
            <span className='list-title align-line-row'> 
                {title === "Patients" ? (
                    <>
                        <Person size={20} color="black"/> &nbsp;
                    </>
                ) : title === "Diagnoses" ? (
                    <>
                        <Paperclip size={20} color="black"/> &nbsp;
                    </>
                ) : (<></>)} 
                {title}:
            </span>
            {title === "Patients" ? (
                <>
                    <div className="professional-measure-list">
                        {dataArray.map((patient, index) => {
                            return (
                                <InfoItem key={index} patientName={patient.name} patientId={patient.id}/>
                            )
                        })}
                        <div className="App-professional-info-list-item" style={{display: "flex", flexDirection: "column"}}>
                            <NavLink to='/patients' style={{backgroundColor: "white"}}> 
                                <span className='info-list-navlink'>Show More</span>
                            </NavLink>
                        </div> 
                    </div>
                </>
            ) : ( // Diagnoses
                <>
                    <div className="professional-measure-list">
                        {dataArray.map((diagnosis, index) => {
                            return (
                                <div key={index} className="App-professional-info-list-item" style={{display: "flex", flexDirection: "column"}}>
                                    <span>Diagnosis {diagnosis.id}</span>
                                    <span className='list-item-date'>{diagnosis.created_at} </span>
                                </div>  
                            )
                        })}
                        <div className="App-professional-info-list-item" style={{display: "flex", flexDirection: "column"}}>
                            <NavLink to='/diagnoses' style={{backgroundColor: "white"}}> 
                                <span className='info-list-navlink'>Show More</span>
                            </NavLink>
                        </div> 
                    </div>
                </>
            )}                    
        </div>
    );
}