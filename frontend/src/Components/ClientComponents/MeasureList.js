import './MeasureList.css'
import './ClientBase.css'

import { NavLink } from 'react-router-dom';

export default function MeasureList({title, dataArray}) {
    return (
        <div className="client-measure-list-box">
            <span className='list-title'>{title}:</span>
            {title === "Measures" ? (
                <>
                    <div className="client-measure-list">
                        {dataArray.map(measure => {
                            return (
                                <div className="App-client-measure-list-item vertical-container">
                                    <span>{title}: {measure.heartbeat} </span>
                                    <span className='list-item-date'>{measure.created_at} </span>
                                </div>  
                            )
                        })}
                        <div className="App-client-measure-list-item vertical-container">
                            <span>Temperature: 30 °C </span>
                            <span className='list-item-date'>WEDNESDAY, OCT 18 AT 15:35 </span>
                        </div>  
                        <div className="App-client-measure-list-item vertical-container">
                            <span>Heartbeat: 70 BPM </span>
                            <span className='list-item-date'>WEDNESDAY, OCT 18 AT 15:35 </span>
                        </div> 
                        <div className="App-client-measure-list-item vertical-container">
                            <span>Temperature: 30 °C </span>
                            <span className='list-item-date'>WEDNESDAY, OCT 18 AT 15:35 </span>
                        </div> 
                        <div className="App-client-measure-list-item vertical-container">
                            <span>Temperature: 30 °C </span>
                            <span className='list-item-date'>WEDNESDAY, OCT 18 AT 15:35 </span>
                        </div> 
                        <div className="App-client-measure-list-item vertical-container">
                            <span>Temperature: 30 °C </span>
                            <span className='list-item-date'>WEDNESDAY, OCT 18 AT 15:35 </span>
                        </div> 
                        <div className="App-client-measure-list-item vertical-container">
                            <span>Temperature: 30 °C </span>
                            <span className='list-item-date'>WEDNESDAY, OCT 18 AT 15:35 </span>
                        </div> 
                        <div className="App-client-measure-list-item vertical-container">
                            <span>Temperature: 30 °C </span>
                            <span className='list-item-date'>WEDNESDAY, OCT 18 AT 15:35 </span>
                        </div> 
                        <div className="App-client-measure-list-item vertical-container">
                            <span>Temperature: 30 °C </span>
                            <span className='list-item-date'>WEDNESDAY, OCT 18 AT 15:35 </span>
                        </div> 
                        <div className="App-client-measure-list-item vertical-container">
                            <NavLink to='/measures' style={{backgroundColor: "white"}}> 
                                <span className='measure-list-navlink'>Show More</span>
                            </NavLink>
                        </div> 
                    </div>
                </>
            ) : title === "Diagnoses" ? (
                <>
                    <div className="client-measure-list">
                        <div className="App-client-measure-list-item vertical-container">
                            <span>Diagnosis: ajqjsojsojsqojqs </span>
                            <span className='list-item-date'>WEDNESDAY, OCT 18 AT 15:35 </span>
                        </div>  
                        <div className="App-client-measure-list-item vertical-container">
                            <span>Diagnosis: ajqjsojsojsqojqs </span>
                            <span className='list-item-date'>WEDNESDAY, OCT 18 AT 15:35 </span>
                        </div>  
                        <div className="App-client-measure-list-item vertical-container">
                            <span>Diagnosis: ajqjsojsojsqojqs </span>
                            <span className='list-item-date'>WEDNESDAY, OCT 18 AT 15:35 </span>
                        </div>  
                        <div className="App-client-measure-list-item vertical-container">
                            <span>Diagnosis: ajqjsojsojsqojqs </span>
                            <span className='list-item-date'>WEDNESDAY, OCT 18 AT 15:35 </span>
                        </div>  
                        <div className="App-client-measure-list-item vertical-container">
                            <span>Diagnosis: ajqjsojsojsqojqs </span>
                            <span className='list-item-date'>WEDNESDAY, OCT 18 AT 15:35 </span>
                        </div>  
                        <div className="App-client-measure-list-item vertical-container">
                            <NavLink to='/diagnoses' style={{backgroundColor: "white"}}> 
                                <span className='measure-list-navlink'>Show More</span>
                            </NavLink>
                        </div> 
                    </div>
                </>
            ) : title === "Temperature" ? (
                <>
                    <div className="client-measure-list">
                        {dataArray.map(measure => {
                            return (
                                <div className="App-client-measure-list-item vertical-container" style={{maxHeight: "10%"}}>
                                    <span>Temperature: {measure.temperature} °C</span>
                                    <span className='list-item-date'>{measure.created_at} </span>
                                </div>  
                            )
                        })}
                    </div>
                </>
            ) : ( // Heartbeat
                <>
                    <div className="client-measure-list">
                        {dataArray.map(measure => {
                            return (
                                <div className="App-client-measure-list-item vertical-container" style={{maxHeight: "10%"}}>
                                    <span>Heartbeat: {measure.heartbeat} BPM</span>
                                    <span className='list-item-date'>{measure.created_at} </span>
                                </div>  
                            )
                        })}
                    </div>
                </>
            )}                    
        </div>
    );
}