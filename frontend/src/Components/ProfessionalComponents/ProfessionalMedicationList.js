import './ProfessionalBase.css'

import { Capsule } from 'react-bootstrap-icons';
import MedicationItem from './MedicationItem';

export default function ProfessionalMedicationList({ dataArray, onDeleteMedication }) {
    const handleDeleteMedication = (index) => {
        onDeleteMedication(index);
    };

    return (
        <div className="professional-info-list-box">
            <span className='list-title align-line-row' style={{ fontSize: "16px" }}>
                <Capsule size={15} color="black" /> &nbsp; Current Medications:
            </span>

            <div className={`professional-measure-list`} >
                {!dataArray || dataArray.length === 0 ? (
                    <span className='no-records'>No prescripted medications yet.</span>
                ) : (
                    <>
                        {dataArray.map((medication, index) => {
                            return (
                                <MedicationItem key={index} index={index} medication={medication} onDelete={handleDeleteMedication} />
                            )
                        })}
                    </>
                )}
            </div>

        </div>
    );
}