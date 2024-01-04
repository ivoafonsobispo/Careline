import PageTitle from "../../Components/PageTitle/PageTitle";
import "../../Components/ProfessionalComponents/ProfessionalBase.css";
import "./ProfessionalProfile.css"

import ProfessionalEditProfileBody from "../../Components/ProfessionalComponents/ProfessionalEditProfileBody";
import ProfessionalProfileBody from "../../Components/ProfessionalComponents/ProfessionalProfileBody";

import { toast, ToastContainer } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

import axios from 'axios';
import { useState, useEffect } from 'react';
const urlUser = 'http://localhost:8080/api/professionals/1';

export default function ProfessionalProfile() {

    const [user, setUser] = useState(null);
    const [showForm, setShowForm] = useState(false);

    useEffect(() => {
        axios.get(urlUser, {
            headers: {
                'Access-Control-Allow-Origin': '*',
            },
            proxy: {
                port: 8080
            }
        })
            .then(response => {
                setUser(response.data);
                // console.log(response.data);
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

    return (
        <div className="horizontal-container">
            <div className="vertical-container">
                <PageTitle title="Profile" />
                <div className='App-content'>
                    {showForm ?
                        (<ProfessionalEditProfileBody user={user} setEditProfileClicked={handleEditProfileClicked} onEditProfileSuccess={handleEditProfileSuccess} />) :
                        (<ProfessionalProfileBody user={user} setEditProfileClicked={handleEditProfileClicked} />)}
                    <ToastContainer />
                </div>
            </div>
        </div>
    );
}