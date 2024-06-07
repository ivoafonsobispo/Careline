import './PatientInfoList.css'
import '../ProfessionalBase.css'

export default function DroneItem({ drone }) {

    return (
        <div className="patient-diagnosis-item-box">
            <div className='align-line-row'>
                Drone {drone.id}
            </div>
            <hr></hr>
            <div className='align-line-row' style={{ marginBottom: "1%" }}>
                <span><b>&#91;</b>{(drone.coordinate.departure_latitude).toFixed(3)}, {(drone.coordinate.departure_longitude).toFixed(3)}<b>&#93;</b> &nbsp; &#8594; &nbsp; <b>&#91;</b>{(drone.coordinate.arrival_latitude).toFixed(3)}, {(drone.coordinate.arrival_longitude).toFixed(3)}<b>&#93;</b></span>
            </div>
            <div style={{ display: "flex", flexDirection: "row", marginBottom: "1%" }}>
                <span>
                    <b>Medications:</b>
                </span>
                <div className='pacient-diagnosis-item-information' style={{ marginLeft: "2%" }}>
                    {drone.medications.join(', ')}
                </div>
            </div>
            <div className='align-line-row' style={{ marginBottom: "1%" }}>
                <b>Departure date: &nbsp;</b>
                {drone.status === 'PENDING' ? (
                    <span>-----</span>
                ) : (
                    <span className='triage-date-field'>{drone.departure_time}</span>
                )}
            </div>
            <div className='align-line-row' style={{ marginBottom: "1%" }}>
                <b>Arrival date: &nbsp;</b>
                {drone.status === 'PENDING' || drone.status === 'IN_TRANSIT' ? (
                    <span>-----</span>
                ) : (
                    <span className='triage-date-field'>{drone.arrival_time}</span>
                )}
            </div>

        </div>
    );
}