import PageTitle from "../Components/PageTitle/PageTitle";
import "../Components/ClientComponents/ClientBase.css";
import "./ClientProfile.css";

import {Eye, EyeSlash} from 'react-bootstrap-icons';

import axios from 'axios';
import { useState, useEffect } from 'react';
const urlUser = 'http://localhost:8080/api/patients/1';


export default function ClientDrones() {

    const [password, setPassword] = useState('');
    const [showPassword, setShowPassword] = useState(false);
    const togglePasswordVisibility = () => {
        setShowPassword(!showPassword);
    };

    const [user, setUser] = useState(null);

    useEffect(() => {
        axios.get(urlUser, { 
          headers: {
            'Access-Control-Allow-Origin': '*',
          }, 
          proxy: {
            port: 8080
          } })
          .then(response => {
            // handle the response
            setUser(response.data);
            console.log(response.data);
          })
          .catch(error => {
            // handle the error
            console.log(error);
          });
    }, []);

    if (!user) return null;

    return(
        <div className="vertical-container">
            <PageTitle title="Profile"/>
            <div className='App-content'>
                <div className="vertical-container profile-container">
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
                            style={{border: "0", fontSize: "20px", pointerEvents: "none", fontFamily: "inherit", width: "100%"}}
                            readonly="readonly"
                            
                            />
                            <button type="button" onClick={togglePasswordVisibility} style={{background: "none",  border: "0", cursor: "pointer"}}>
                                {showPassword ? <EyeSlash size={25} className='navbar-svg' color="var(--basecolor)"/> : <Eye size={25} className='navbar-svg' color="var(--basecolor)"/>}
                            </button>
                        </span>
                    </div>
                    <div className="horizontal-container" style={{alignItems: "center"}}>
                        <span className="profile-field-title">NUS:</span> <span className="profile-field">{user.nus}</span>
                    </div>
                </div>
            </div>
        </div>
    );
}