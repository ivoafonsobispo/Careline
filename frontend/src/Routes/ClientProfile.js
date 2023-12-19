import PageTitle from "../Components/PageTitle/PageTitle";
import "../Components/ClientComponents/ClientBase.css";
import "./ClientProfile.css";
import ClientProfileBody from "../Components/ClientComponents/ClientProfileBody";
import ClientEditProfileBody from "../Components/ClientComponents/ClientEditProfileBody";

import {Pencil, Check} from 'react-bootstrap-icons';

import axios from 'axios';
import { useState, useEffect } from 'react';
const urlUser = 'http://localhost:8080/api/patients/1';


export default function ClientDrones() {

    const [user, setUser] = useState(null);
    const [showForm, setShowForm] = useState(false);

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

    const editProfileClicked = () => {
        setShowForm(!showForm);
    }

    return(
        <div className="vertical-container">
            <PageTitle title="Profile"/>
            <div className='App-content'>
                <div className="vertical-container profile-container">
                    {showForm ? (<ClientEditProfileBody user={user}/>) :(<ClientProfileBody user={user}/>) }
                    

                    <div className="horizontal-container" style={{alignItems: "center"}}>
                        <button onClick={editProfileClicked} className="profile-button">{showForm ? <Check size={15} color="white"/> : <Pencil size={15} color="white"/>} &nbsp; Edit Profile</button>
                    </div>
                </div>
            </div>
        </div>
    );
}