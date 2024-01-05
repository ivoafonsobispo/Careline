import { useState } from "react";
import "../../Routes/Login.css";

import classNames from 'classnames';
import { Eye, EyeSlash, BoxArrowInRight } from 'react-bootstrap-icons';

import { toast } from "react-toastify";
import { useNavigate } from "react-router-dom";

export default function SignUp({ setOtherView }) {
    const navigate = useNavigate();

    const isNumber = (value) => /^\d+$/.test(value);

    const [name, setName] = useState('');
    const [isValidName, setIsValidName] = useState(false);
    const [nameTouched, setNameTouched] = useState(false);
    const handleNameChange = (e) => {
        setName(e.target.value);
        const nameRegex = /^(?!\s*$)^[a-zA-Z\s]+$/;
        const isValidName = nameRegex.test(e.target.value);
        setIsValidName(isValidName);
        setNameTouched(true);
    };

    const [email, setEmail] = useState('');
    const [isValidEmail, setIsValidEmail] = useState(false);
    const [emailTouched, setEmailTouched] = useState(false);
    const handleEmailChange = (e) => {
        setEmail(e.target.value);
        const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        const isValidEmail = emailRegex.test(e.target.value);
        setIsValidEmail(isValidEmail);
        setEmailTouched(true);
    };

    const [password, setPassword] = useState('');
    const [isValidPassword, setIsValidPassword] = useState(false);
    const [passwordTouched, setPasswordTouched] = useState(false);
    const handlePasswordChange = (e) => {
        setPassword(e.target.value);
        const passwordRegex = /^[^\s@]/;
        const isValidPassword = passwordRegex.test(e.target.value);
        setIsValidPassword(isValidPassword);
        setPasswordTouched(true);
    };
    const [showPassword, setShowPassword] = useState(false);
    const togglePasswordVisibility = () => {
        setShowPassword(!showPassword);
    };

    const [nus, setNus] = useState('');
    const [isValidNus, setIsValidNus] = useState(false);
    const [nusTouched, setNusTouched] = useState(false);
    const handleNusChange = (e) => {
        // Check if the entered value is a number
        if ((isNumber(e.target.value) && e.target.value.length <= 9) || e.target.value === '') {
            setNus(e.target.value);
            const nusRegex = /^\d{9}$/;
            const isValidNus = nusRegex.test(e.target.value);
            setIsValidNus(isValidNus);
            setNusTouched(true);
        }

    };

    // const [errorMessage, setErrorMessage] = useState('');
    const handleSignUp = async () => {
        try {
            const response = await fetch('http://10.20.229.55/api/signup/patient', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({
                    "name": name,
                    "email": email,
                    "password": password,
                    "nus": nus
                }),
            });

            if (response.ok) {
                // Login successful, handle the success (e.g., redirect or set user token)
                navigate('/');

                toast.success('Account created successfully!', {
                    style: {
                        fontSize: '16px',
                    },
                });

            } else {
                // Login failed, handle the error
                console.log(response);
                // const errorData = await response.json();
                // setErrorMessage(errorData.message || 'Login failed');
            }
        } catch (error) {
            console.error('Error during login:', error);
        }
    };

    return (
        <div className="login-right-content">
            <h2 style={{ margin: "0 auto" }}>Sign Up</h2>
            <div className="login-fields-body-signup">
                {/* Name */}
                <div className="horizontal-container" style={{ alignItems: "center" }}>
                    <span className="profile-field-title">Name:</span>
                    <input
                        className={classNames('profile-field', !isValidName && nameTouched ? 'profile-field-border' : '')}
                        type="text"
                        name="name"
                        value={name}
                        onChange={handleNameChange}
                    />
                </div>
                {!isValidName && nameTouched ? <p className='field-error-message'>Name is invalid</p> : <></>}

                {/* Email */}
                <div className="horizontal-container" style={{ alignItems: "center" }}>
                    <span className="profile-field-title">Email:</span>
                    <input
                        className={classNames('profile-field', !isValidEmail && emailTouched ? 'profile-field-border' : '')}
                        type="email"
                        name="email"
                        value={email}
                        onChange={handleEmailChange}
                    />
                </div>
                {!isValidEmail && emailTouched ? <p className='field-error-message'>Email is invalid</p> : <></>}

                {/* Password */}
                <div className="horizontal-container" style={{ alignItems: "center" }}>
                    <span className="profile-field-title">Password:</span>
                    <span className={classNames('profile-field', !isValidPassword && passwordTouched ? 'profile-field-border' : '')} style={{ alignItems: "center", display: "flex" }}>
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
                {!isValidPassword && passwordTouched ? <p className='field-error-message'>Password is invalid</p> : <></>}

                {/* NUS */}
                <div className="horizontal-container" style={{ alignItems: "center" }}>
                    <span className="profile-field-title">NUS:</span>
                    <input
                        className={classNames('profile-field', !isValidNus && nusTouched ? 'profile-field-border' : '')}
                        type="type"
                        name="nus"
                        value={nus}
                        onChange={handleNusChange}
                    />
                </div>
                {!isValidNus && nusTouched ? <p className='field-error-message'>NUS is invalid</p> : <></>}

                {/* Edit Button */}
                <div className="vertical-container" style={{ alignItems: "center" }}>
                    <button className={classNames("profile-button align-line-row", !(isValidName && isValidEmail && isValidNus && isValidPassword) ? "profile-button-disabled" : "")}
                        disabled={!(isValidName && isValidEmail && isValidNus && isValidPassword)}
                        style={{ width: "20%" }}
                        onClick={handleSignUp}>
                        <span className="align-line-row" style={{ margin: "0 auto" }}><BoxArrowInRight size={25} color="white" /> &nbsp; Sign Up</span>
                    </button>
                    <button className="login-subbutton" onClick={() => setOtherView()}>
                        Already have an account? Sign in
                    </button>
                </div>
            </div>
        </div>
    );
}