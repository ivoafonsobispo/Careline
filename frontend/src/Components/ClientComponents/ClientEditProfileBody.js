import classNames from 'classnames';
import './ClientEditProfileBody.css'

import { Eye, EyeSlash, Check, X } from 'react-bootstrap-icons';
import { useState } from 'react';

import { useSelector } from 'react-redux';

export default function ClientEditProfileBody({ user, setEditProfileClicked, onEditProfileSuccess }) {
    const token = useSelector((state) => state.auth.token);	

    const isNumber = (value) => /^\d+$/.test(value);

    const [name, setName] = useState(user.name);
    const [isValidName, setIsValidName] = useState(true);
    const handleNameChange = (e) => {
        setName(e.target.value);
        const nameRegex = /^(?!\s*$)^[a-zA-Z\s]+$/;
        const isValidName = nameRegex.test(e.target.value);
        setIsValidName(isValidName);
    };

    const [email, setEmail] = useState(user.email);
    const [isValidEmail, setIsValidEmail] = useState(true);
    const handleEmailChange = (e) => {
        setEmail(e.target.value);
        const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        const isValidEmail = emailRegex.test(e.target.value);
        setIsValidEmail(isValidEmail);
    };

    const [password, setPassword] = useState(user.password);
    const [isValidPassword, setIsValidPassword] = useState(true);
    const handlePasswordChange = (e) => {
        setPassword(e.target.value);
        const passwordRegex = /^[^\s@]/;
        const isValidPassword = passwordRegex.test(e.target.value);
        setIsValidPassword(isValidPassword);
    };
    const [showPassword, setShowPassword] = useState(false);
    const togglePasswordVisibility = () => {
        setShowPassword(!showPassword);
    };

    const [nus, setNus] = useState(user.nus);
    const [isValidNus, setIsValidNus] = useState(true);
    const handleNusChange = (e) => {
        // Check if the entered value is a number
        if ((isNumber(e.target.value) && e.target.value.length <= 9) || e.target.value === '') {
            setNus(e.target.value);
            const nusRegex = /^\d{9}$/;
            const isValidNus = nusRegex.test(e.target.value);
            setIsValidNus(isValidNus);
        }

    };

    const handlePostRequest = async () => {
        try {
            const updatedFields = {};

            if (name !== user.name) {
                updatedFields.name = name;
                console.log(updatedFields.name);
            }

            if (email !== user.email) {
                updatedFields.email = email;
                console.log(updatedFields.email);
            }

            if (password !== user.password) {
                updatedFields.password = password;
                console.log(updatedFields.password);
            }

            if (nus !== user.nus) {
                updatedFields.nus = nus;
            }

            if (Object.keys(updatedFields).length === 0) {
                console.warn('No fields to update');
                return;
            }

            console.log(JSON.stringify(updatedFields));

            const response = await fetch(`http://10.20.229.55/api/patients/${user.id}`, {
                method: 'PATCH',
                headers: {
                    'Content-Type': 'application/json',
                    Authorization: `Bearer ${token}`,
                },
                body: JSON.stringify(updatedFields),
            });

            if (!response.ok) {
                // Handle non-successful response here
                console.error('Error:', response.statusText);
                return;
            }

            // Assuming the server responds with JSON data
            const data = await response.json();
            user.name = data.name;
            user.email = data.email;
            user.nus = data.nus;
            user.password = data.password;

            setEditProfileClicked();
            onEditProfileSuccess();
        } catch (error) {
            // Handle errors during the fetch
            console.error('Error:', error);
        }
    };

    return (
        <div className="vertical-container profile-container">
            {/* Name */}
            <div className="horizontal-container" style={{ alignItems: "center" }}>
                <span className="profile-field-title">Name:</span>
                <input
                    className={classNames('profile-field', isValidName ? '' : 'profile-field-border')}
                    type="text"
                    name="name"
                    value={name}
                    onChange={handleNameChange}
                />
            </div>
            {isValidName ? <></> : <p className='field-error-message'>Name is invalid</p>}

            {/* Email */}
            <div className="horizontal-container" style={{ alignItems: "center" }}>
                <span className="profile-field-title">Email:</span>
                <input
                    className={classNames('profile-field', isValidEmail ? '' : 'profile-field-border')}
                    type="email"
                    name="email"
                    value={email}
                    onChange={handleEmailChange}
                />
            </div>
            {isValidEmail ? <></> : <p className='field-error-message'>Email is invalid</p>}

            {/* Password */}
            <div className="horizontal-container" style={{ alignItems: "center" }}>
                <span className="profile-field-title">Password:</span>
                <span className={classNames('profile-field', isValidPassword ? '' : 'profile-field-border')} style={{ alignItems: "center", display: "flex" }}>
                    <input
                        type={showPassword ? 'text' : 'password'}
                        value={password}
                        onChange={handlePasswordChange}
                        style={{ border: "0", fontSize: "16px", fontFamily: "inherit", width: "100%" }}
                    />
                    <button type="button" onClick={togglePasswordVisibility} style={{ background: "none", border: "0", cursor: "pointer" }}>
                        {showPassword ? <EyeSlash size={25} color="var(--basecolor)" /> : <Eye size={25} color="var(--basecolor)" />}
                    </button>
                </span>
            </div>
            {isValidPassword ? <></> : <p className='field-error-message'>Password is invalid</p>}

            {/* NUS */}
            <div className="horizontal-container" style={{ alignItems: "center" }}>
                <span className="profile-field-title">NUS:</span>
                <input
                    className={classNames('profile-field', isValidNus ? '' : 'profile-field-border')}
                    type="type"
                    name="nus"
                    value={nus}
                    onChange={handleNusChange}
                />
            </div>
            {isValidNus ? <></> : <p className='field-error-message'>NUS is invalid</p>}

            {/* Edit Button */}
            <div className="horizontal-container" style={{ alignItems: "center" }}>
                <button className="profile-button align-line-row" onClick={() => setEditProfileClicked()}>
                    <X size={25} color="white" /> &nbsp; Back
                </button>
                <button className={classNames("profile-button align-line-row", !(isValidName && isValidEmail && isValidNus && isValidPassword) ? "profile-button-disabled" : "")} onClick={handlePostRequest} disabled={!(isValidName && isValidEmail && isValidNus && isValidPassword)}>
                    <Check size={25} color="white" /> &nbsp; Edit Profile
                </button>
            </div>
        </div>


    );
}