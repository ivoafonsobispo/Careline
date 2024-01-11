import { useState } from "react";
import "../../Routes/Login.css";

import { useNavigate } from "react-router-dom";

import classNames from 'classnames';
import { Eye, EyeSlash, BoxArrowInRight } from 'react-bootstrap-icons';

import { useDispatch } from 'react-redux'
import { tokenSetter, userSetter } from '../../TokenSlice'

import { ToastContainer } from "react-toastify";

export default function SignIn({ setOtherView }) {

    const navigate = useNavigate();

    const isNumber = (value) => /^\d+$/.test(value);

    const [password, setPassword] = useState('');
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

    const [nus, setNus] = useState('');
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

    // const [errorMessage, setErrorMessage] = useState('');

    // const token = useSelector((state) => state.token.value)
    const dispatch = useDispatch()
    const handleLogin = async () => {
        console.log(nus)
        console.log(password)
        console.log(JSON.stringify({
            nus,
            password,
        }));

        try {
            const response = await fetch('/signin/patient', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({
                    nus,
                    password,
                }),
            });

            if (response.ok) {
                // Login successful, handle the success (e.g., redirect or set user token)
                console.log('Login successful');
                const responseData = await response.json();

                console.log(responseData.token);
                console.log(`Bearer ${responseData.token}`);
                dispatch(tokenSetter(responseData.token));

                const patientResponse = await fetch(`/patients/nus/${nus}`, {
                    method: 'GET',
                    headers: {
                        'Content-Type': 'application/json',
                        Authorization: `Bearer ${responseData.token}`,
                    },
                });

                console.log(responseData.token);

                const patientResponseData = await patientResponse.json();
                patientResponseData.type = "patient";
                dispatch(userSetter(patientResponseData));

                navigate('/');

            } else {
                // Login failed, handle the error~
                console.log(response);
                const secondResponse = await fetch('/signin/professional', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json',
                    },
                    body: JSON.stringify({
                        nus,
                        password,
                    }),
                })

                if (secondResponse.ok) {
                    // Login successful, handle the success (e.g., redirect or set user token)
                    console.log('Login successful');
                    const secondResponseData = await secondResponse.json();

                    console.log(secondResponseData.token);
                    console.log(`Bearer ${secondResponseData.token}`);
                    dispatch(tokenSetter(secondResponseData.token));

                    const professionalResponse = await fetch(`/professionals/nus/${nus}`, {
                        method: 'GET',
                        headers: {
                            'Content-Type': 'application/json',
                            Authorization: `Bearer ${secondResponseData.token}`,
                        },
                    });


                    const professionalResponseData = await professionalResponse.json();
                    professionalResponseData.type = "professional"; 
                    console.log(professionalResponseData);
                    dispatch(userSetter(professionalResponseData));

                    navigate('/');
                }
            }
        } catch (error) {
            console.error('Error during login:', error);
        }
    };

    return (
        <div className="login-right-content">
            <h2 style={{ margin: "0 auto" }}>Sign in</h2>
            <div className="login-fields-body">
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

                {/* Edit Button */}
                <div className="vertical-container" style={{ alignItems: "center" }}>
                    <button className={classNames("profile-button align-line-row", !(isValidNus && isValidPassword) ? "profile-button-disabled" : "")}
                        disabled={!(isValidNus && isValidPassword)}
                        style={{ width: "20%" }}
                        onClick={handleLogin}>
                        <span className="align-line-row" style={{ margin: "0 auto" }}><BoxArrowInRight size={25} color="white" /> &nbsp; Sign In</span>
                    </button>
                    <button className="login-subbutton" onClick={() => setOtherView()}>
                        Don't have an account? Sign up
                    </button>
                </div>
            </div>
            <ToastContainer />
        </div>
    );
}