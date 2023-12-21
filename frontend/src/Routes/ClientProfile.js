import PageTitle from "../Components/PageTitle/PageTitle";
import "../Components/ClientComponents/ClientBase.css";
import "./ClientProfile.css";
import ClientProfileBody from "../Components/ClientComponents/ClientProfileBody";
import ClientEditProfileBody from "../Components/ClientComponents/ClientEditProfileBody";

import { toast, ToastContainer } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

import axios from 'axios';
import { useState, useEffect } from 'react';
const urlUser = 'http://localhost:8080/api/patients/1';


export default function ClientProfile() {

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
            setUser(response.data);
            console.log(response.data);
          })
          .catch(error => {
            console.log(error);
          });
    }, []);

    if (!user) return null;

    const handleEditProfileClicked = () => {
        setShowForm(!showForm);
    }

    const handleEditProfileSuccess = () => {
      toast.success('Profile updated successfully!', {
          style: {
              fontSize: '16px',
          },
      });
    };

    return(
        <div className="vertical-container">
            <PageTitle title="Profile"/>
            <div className='App-content'>
                {showForm ? 
                (<ClientEditProfileBody user={user} setEditProfileClicked={handleEditProfileClicked} onEditProfileSuccess={handleEditProfileSuccess}/>) :
                (<ClientProfileBody user={user} setEditProfileClicked={handleEditProfileClicked}/>) }
                <ToastContainer />
            </div>
        </div>
    );
}