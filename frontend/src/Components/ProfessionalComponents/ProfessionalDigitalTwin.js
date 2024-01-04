import './ProfessionalDigitalTwin.css'

export default function DigitalTwin({ value, heartStyle }) {

    return (
        <div className="professional-digital-twin-box">
            <img src="/images/heart.png" className="digital-twin-img" alt="DT" style={heartStyle} />
            <span>{value ? value : "---"} BPM</span>
        </div>
    );
}