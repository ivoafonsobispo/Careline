import './InfoList.css'
import './ProfessionalBase.css'

import { PersonCheck, PersonAdd } from 'react-bootstrap-icons';
import PatientItem from './PatientItem';
import Select from 'react-select';
import { useState } from 'react';

const customStyles = {
    option: (provided, state) => ({
        ...provided,
        backgroundColor: state.isFocused ? 'var(--professionalBaseColor)' : 'white',
        color: state.isFocused ? 'white' : 'black',
    }),
};


export default function PatientList({ title, dataArray, setAssociatedPatient, sort }) {
    const handleAssociatedPatient = () => {
        setAssociatedPatient();
    }

    const options = [
        { value: 'severity', label: 'Severity' },
        { value: 'name', label: 'Name' },
        { value: 'nus', label: 'NUS' },
    ];

    const [selectedOption, setSelectedOption] = useState(options[0]);

    const handleSortChange = (selectedOption) => {
        setSelectedOption(selectedOption);
        sort(selectedOption.value, title);
    }

    return (
        <div className="professional-info-list-box">
            <span className='list-title align-line-row'>
                {title === "Associated Patients" ? (
                    <PersonCheck size={20} color="black" />
                ) : (
                    <PersonAdd size={20} color="black" />
                )}
                &nbsp; {title}:

                <div className='align-right'>
                    <Select
                        options={options}
                        value={selectedOption}
                        onChange={handleSortChange}
                        styles={customStyles}
                    />
                </div>
            </span>

            <div className={`professional-measure-list`}>
                {!dataArray || dataArray.length === 0 ? (
                    <div className='no-records'>{title === "Associated Patients" ? "No patients associated yet." : "No available patients."}</div>
                ) : (
                    <>
                        {dataArray.map((patient, index) => {
                            return (
                                <PatientItem key={index} patient={patient} title={title} setAssociatedPatient={handleAssociatedPatient} />
                            )
                        })}
                    </>
                )}

            </div>

        </div>
    );
}