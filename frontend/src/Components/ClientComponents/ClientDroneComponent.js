import './ClientTriageComponent.css'
import './ClientDroneComponent.css'
import './ClientBase.css'

import { Check, Eye, ClockHistory, X, BoxSeam } from 'react-bootstrap-icons'

import { NavLink } from 'react-router-dom';

export default function ClientDroneComponent({ drone }) {

    return (
        <div className="client-triage-box">
            <div className='align-line-row'>
                <span className='align-line-row' style={{ marginLeft: "0.8%" }}><BoxSeam size={20} /> &nbsp;Drone {drone.id}</span>
            </div>
            <hr className='triage-hr'></hr>

            <div className='horizontal-container client-triage-information-box'>
                <div className='vertical-container' style={{ gap: "10%", width: "24%", minWidth: "40%", marginRight: "3%" }}>
                    <div className='align-line-row'>
                        <span><b>&#91;</b>{drone.coordinate.departure_latitude}, {drone.coordinate.departure_longitude}<b>&#93;</b> &nbsp; &#8594; &nbsp; <b>&#91;</b>{drone.coordinate.arrival_latitude}, {drone.coordinate.arrival_longitude}<b>&#93;</b></span>
                    </div>
                    <div className='align-line-column'>
                        <div className='align-line-row'>
                            <span><b>Departure date:</b> &nbsp; </span>
                            <span className='triage-field'>
                                {drone.status === 'PENDING' ? (
                                    <span>-----</span>
                                ) : (
                                    <span className='triage-date-field'>{drone.departure_time}</span>
                                )}

                            </span>
                        </div>
                        <div className='align-line-row'>
                            <span><b>Arrival date:</b> &nbsp; </span>
                            <span className='triage-field'>
                                {drone.status === 'PENDING' || drone.status === 'IN_TRANSIT' ? (
                                    <span>-----</span>
                                ) : (
                                    <span className='triage-date-field'>{drone.arrival_time}</span>
                                )}

                            </span>
                        </div>
                    </div>
                </div>
                <div >
                    <span><b>Medications: </b></span>
                </div>
                <div className='client-triage-information'>
                    <span > {drone.medications.join(', ')} </span>
                </div>


            </div>
            <hr className='triage-hr'></hr>

            <div className='align-line-row'>
                <div>
                    {drone.status === "PENDING" ?
                        <span className='align-line-row' style={{ color: "var(--pendingBlue)", fontWeight: "bold" }}>
                            <ClockHistory size={15} /> &nbsp;Status - Pending
                        </span>
                    : drone.status === "IN_TRANSIT" ?
                        <span className='align-line-row' style={{ color: "#eb7c49", fontWeight: "bold" }}>
                            <ClockHistory size={15} /> &nbsp; Status - Inshipping
                        </span>
                    : drone.status === "DELIVERED" ?
                        <span className='align-line-row' style={{ color: "var(--lightGreen)", fontWeight: "bold" }}>
                            <Check size={20} /> &nbsp; Status - Delivered
                        </span>
                    :
                        <span className='align-line-row' style={{ color: "red", fontWeight: "bold" }}>
                            <X size={20} /> &nbsp; Status - Failed
                        </span>
                    }
                </div>

                <NavLink to={'/drones/1'} className='triage-button align-line-row' style={{ marginLeft: "auto", marginRight: "2%", padding: "1% 0%", fontSize: "16px" }}>
                    <span className='align-line-row' style={{ margin: "auto" }}>
                        <Eye size={15} /> &nbsp; View
                    </span>
                </NavLink>

            </div>

            <span className='triage-date'>{drone.created_at}</span>
        </div>
    );
}