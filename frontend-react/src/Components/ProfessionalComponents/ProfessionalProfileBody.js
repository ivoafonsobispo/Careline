import './ProfessionalProfileBody.css'

import { Pencil } from 'react-bootstrap-icons';

import { useSelector } from 'react-redux';

export default function ProfessionalProfileBody({ setEditProfileClicked }) {
    const user = useSelector((state) => state.auth.user);	

    return (
        <div className="vertical-container profile-container">
            {/* Name */}
            <div className="horizontal-container" style={{ alignItems: "center" }}>
                <span className="profile-field-title">Name:</span> <span className="professional-profile-field">{user.name}</span>
            </div>

            {/* Email */}
            <div className="horizontal-container" style={{ alignItems: "center" }}>
                <span className="profile-field-title">Email:</span> <span className="professional-profile-field">{user.email}</span>
            </div>

            {/* NUS */}
            <div className="horizontal-container" style={{ alignItems: "center" }}>
                <span className="profile-field-title">NUS:</span> <span className="professional-profile-field">{user.nus}</span>
            </div>

            {/* Edit Button */}
            <div className="horizontal-container" style={{ alignItems: "center" }}>
                <button className="professional-profile-button align-line-row" onClick={() => setEditProfileClicked()}>
                    <Pencil size={15} color="white" style={{ marginLeft: "3%" }} /> &nbsp; Edit Profile
                </button>
            </div>
        </div>
    );
}