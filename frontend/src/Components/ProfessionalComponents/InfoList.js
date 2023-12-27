import './InfoList.css'
import './ProfessionalBase.css'

import { NavLink } from 'react-router-dom';

import { Person } from 'react-bootstrap-icons';
import InfoItem from './InfoItem';

export default function InfoList({ title, dataArray }) {
    return (
        <div className="professional-info-list-box">
            <span className='list-title align-line-row'>
                <Person size={20} color="black" /> &nbsp; {title}:
            </span>

            <div className={`professional-measure-list ${dataArray.length > 3 ? 'two-columns' : ''}`}>
                {dataArray.map((patient, index) => {
                    return (
                        <InfoItem key={index} patient={patient} />
                    )
                })}
                <div  className={`App-professional-info-list-item `} style={{ display: "flex", flexDirection: "column" }}>
                    <NavLink to='/patients' style={{ backgroundColor: "white" }}>
                        <span className='info-list-navlink'>Show More</span>
                    </NavLink>
                </div>
            </div>

        </div>
    );
}