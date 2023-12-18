import './MeasureList.css'
import './ClientBase.css'

import { NavLink } from 'react-router-dom';

export default function MeasureList({title}) {
    return (
        <div className="client-measure-list-box">
            <span className='list-title'>{title}:</span>
            <div className="client-measure-list">
                {title == "Measures" ? (
                    <>
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
                            <NavLink to='/' style={{backgroundColor: "white"}}> 
                                <span className='measure-list-navlink'>Show More</span>
                            </NavLink>
                        </div> 
                    </>
                ) : (
                    <>
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
                            <NavLink to='/' style={{backgroundColor: "white"}}> 
                                <span className='measure-list-navlink'>Show More</span>
                            </NavLink>
                        </div> 
                    </>
                )}
                
            </div>        
        </div>
    );
}