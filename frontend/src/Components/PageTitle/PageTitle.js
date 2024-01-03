import './PageTitle.css'

import { ChevronLeft } from 'react-bootstrap-icons';
import { NavLink } from 'react-router-dom';

export default function PageTitle({ title }) {
    return (
        <div className="page-title">
            {title === "Patient" ? (
                <NavLink to='/patients' style={{ minWidth: "auto", marginRight: "1%", fontSize: "18px", height: "66%" }}>
                    <ChevronLeft color="var(--professionalBaseColor)" />
                </NavLink>
            ) : title === "Triage Review" ? (
                <NavLink to='/triage' style={{ minWidth: "auto", marginRight: "1%", fontSize: "18px", height: "66%" }}>
                    <ChevronLeft color="var(--professionalBaseColor)" />
                </NavLink>
            ) : title === "Drone" ? (
                <NavLink to='/drones' style={{ minWidth: "auto", marginRight: "1%", fontSize: "18px", height: "66%" }}>
                    <ChevronLeft color="var(--basecolor)" />
                </NavLink>
            ) : (<></>)}
            {title}
        </div>
    );
}