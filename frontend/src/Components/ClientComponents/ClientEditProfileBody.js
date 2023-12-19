import './ClientEditProfileBody.css'

import {Eye, EyeSlash, Pencil} from 'react-bootstrap-icons';
import { useState, useEffect } from 'react';

export default function ClientEditProfileBody({user}) {
    const isNumber = (value) => /^\d+$/.test(value);

    const [name, setName] = useState(user.name);
    const handleNameChange = (e) => {
        setName(e.target.value);
    };

    const [email, setEmail] = useState(user.email);
    const handleEmailChange = (e) => {
        setEmail(e.target.value);
    };

    const [password, setPassword] = useState(user.password);
    const handlePasswordChange = (e) => {
        setPassword(e.target.value);
    };
    const [showPassword, setShowPassword] = useState(false);
    const togglePasswordVisibility = () => {
        setShowPassword(!showPassword);
    };

    const [nus, setNus] = useState(user.nus);
    const handleNusChange = (e) => {
        // Check if the entered value is a number
        if (isNumber(e.target.value)  && e.target.value.length <= 9 || e.target.value === '') {
            setNus(e.target.value);
        }
    };

    return (
        <div className="vertical-container" style={{gap: "5%"}}>
            <div className="horizontal-container" style={{alignItems: "center"}}>
                <span className="profile-field-title">Name:</span>
                <input
                    className="profile-field"
                    type="text"
                    name="name"
                    value={name}
                    onChange={handleNameChange}
                />
            </div>
            <div className="horizontal-container" style={{alignItems: "center"}}>
                <span className="profile-field-title">Email:</span>
                <input
                    className="profile-field"
                    type="text"
                    name="email"
                    value={email}
                    onChange={handleEmailChange}
                />
            </div>
            <div className="horizontal-container" style={{alignItems: "center"}}>
                <span className="profile-field-title">Password:</span> 
                <span className="profile-field" style={{alignItems: "center", display: "flex"}}>
                    <input
                    type={showPassword ? 'text' : 'password'}
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                    style={{border: "0", fontSize: "16px",fontFamily: "inherit", width: "100%"}}
                    />
                    <button type="button" onClick={togglePasswordVisibility} style={{background: "none",  border: "0", cursor: "pointer"}}>
                        {showPassword ? <EyeSlash size={25} color="var(--basecolor)"/> : <Eye size={25} color="var(--basecolor)"/>}
                    </button>
                </span>
            </div>
            <div className="horizontal-container" style={{alignItems: "center"}}>
                <span className="profile-field-title">NUS:</span> 
                <input
                    className="profile-field"
                    type="type"
                    name="nus"
                    value={nus}
                    onChange={handleNusChange}
                />
            </div>
        </div>
    );
}