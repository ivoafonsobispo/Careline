import './ProfessionalBase.css'
import './InfoList.css'
import './MedicationItem.css'

import { Trash } from 'react-bootstrap-icons';

export default function InfoItem({ index, medication, onDelete }) {
    const handleDelete = () => {
        onDelete(index);
    };

    return (
        <div className="App-professional-info-list-item align-line-row">
            <span>{medication.name} - {medication.dosage} x {medication.frequency} | {medication.duration}</span>
            <div style={{ marginLeft: "auto" }}>
                <Trash className='medication-item-button' onClick={handleDelete} />
            </div>
        </div>
    );
}
