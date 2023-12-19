import './ClientProfileBody.css'

import {Eye, EyeSlash, Pencil} from 'react-bootstrap-icons';
import { useState, useEffect } from 'react';

export default function ClientProfileBody({user}) {
    const [password, setPassword] = useState('');
    const [showPassword, setShowPassword] = useState(false);
    const togglePasswordVisibility = () => {
        setShowPassword(!showPassword);
    };

    return (
        <div className="vertical-container" style={{gap: "5%"}}>
            <div className="horizontal-container" style={{alignItems: "center"}}>
                <span className="profile-field-title">Name:</span> <span className="profile-field">{user.name}</span>
            </div>
            <div className="horizontal-container" style={{alignItems: "center"}}>
                <span className="profile-field-title">Email:</span> <span className="profile-field">{user.email}</span>
            </div>
            <div className="horizontal-container" style={{alignItems: "center"}}>
                <span className="profile-field-title">Password:</span> 
                <span className="profile-field" style={{alignItems: "center", display: "flex"}}>
                    <input
                    type={showPassword ? 'text' : 'password'}
                    value={user.password}
                    onChange={(e) => setPassword(e.target.value)}
                    style={{border: "0", fontSize: "16px", pointerEvents: "none", fontFamily: "inherit", width: "100%"}}
                    readOnly="readonly"
                    
                    />
                    <button type="button" onClick={togglePasswordVisibility} style={{background: "none",  border: "0", cursor: "pointer"}}>
                        {showPassword ? <EyeSlash size={25} color="var(--basecolor)"/> : <Eye size={25} color="var(--basecolor)"/>}
                    </button>
                </span>
            </div>
            <div className="horizontal-container" style={{alignItems: "center"}}>
                <span className="profile-field-title">NUS:</span> <span className="profile-field">{user.nus}</span>
            </div>
        </div>
    );
}