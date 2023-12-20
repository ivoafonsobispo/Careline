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

    const handleEditProfileClicked = () => {
        setShowForm(!showForm);
    }

    return(
        <div className="vertical-container">
            <PageTitle title="Profile"/>
            <div className='App-content'>
                {showForm ? 
                (<ClientEditProfileBody user={user} editProfileClicked={showForm} setEditProfileClicked={handleEditProfileClicked}/>) :
                (<ClientProfileBody user={user} editProfileClicked={showForm} setEditProfileClicked={handleEditProfileClicked}/>) }
              
            </div>
        </div>
    );
}