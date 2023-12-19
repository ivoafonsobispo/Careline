import './ClientDigitalTwin.css'

export default function DigitalTwin({value}) {
    return (
        <div className="client-digital-twin-box">
            <img src="/images/heart.png" className="digital-twin-img" alt="DT"/>
            <span>{value} BPM</span>
        </div>        
    );
}