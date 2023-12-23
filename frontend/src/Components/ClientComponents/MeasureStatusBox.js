import './MeasureStatusBox.css'
import {Heart, ThermometerHalf } from 'react-bootstrap-icons'

export default function MeasureStatusBox({measure, value}) {
    return (
        <div className="client-measure-status-box">
            {measure === "Heartbeat" ? (
                <>
                    <Heart size={35} color={"var(--basecolor)"} style={{marginLeft: "5%"}}/>
                    <div className='status-phrase'>
                        <span>Hearbeat Status: <b>{value} BPM</b> <span style={{float: "inline-end"}}>Good</span></span>
                    </div>
                </>
            ) : (
                <>
                    <ThermometerHalf size={35} color={"var(--basecolor)"} style={{marginLeft: "5%"}}/>
                    <div className='status-phrase'>
                        <span>Temperature Status: <b>{value} °C</b> <span style={{float: "inline-end"}}>Good</span></span>
                    </div>
                </>
            )}
        </div>        
    );
}