import PageTitle from "../../Components/PageTitle/PageTitle";
import "../../Components/ProfessionalComponents/ProfessionalBase.css";
import "./ProfessionalProfile.css"

import ProfessionalEditProfileBody from "../../Components/ProfessionalComponents/ProfessionalEditProfileBody";
import ProfessionalProfileBody from "../../Components/ProfessionalComponents/ProfessionalProfileBody";

import { toast, ToastContainer } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

import { useState } from 'react';

import { useSelector } from "react-redux";


export default function ProfessionalProfile() {
    const user = useSelector((state) => state.auth.user);

    const [showForm, setShowForm] = useState(false);

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