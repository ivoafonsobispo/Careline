import './MeasureStatusBox.css'
import {Heart, ThermometerHalf } from 'react-bootstrap-icons'

export default function MeasureStatusBox({measure}) {
    return (
        <div className="client-measure-status-box">
            {measure == "Heartbeat" ? (
                <>
                    <Heart size={35} color={"var(--basecolor)"} style={{marginLeft: "5%"}}/>
                    <div className='status-phrase'>
                        <span>Hearbeat Status: 70 BPM <span style={{float: "inline-end"}}>Good</span></span>
                    </div>
                </>
            ) : (
                <>
                    <ThermometerHalf size={35} color={"var(--basecolor)"} style={{marginLeft: "5%"}}/>
                    <div className='status-phrase'>
                        <span>Temperature Status: 34 °C <span style={{float: "inline-end"}}>Good</span></span>
                    </div>
                </>
            )}
        </div>        
    );
}