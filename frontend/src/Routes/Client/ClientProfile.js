import PageTitle from "../../Components/PageTitle/PageTitle";
import "../../Components/ClientComponents/ClientBase.css";
import "./ClientProfile.css";
import ClientProfileBody from "../../Components/ClientComponents/ClientProfileBody";
import ClientEditProfileBody from "../../Components/ClientComponents/ClientEditProfileBody";

import { toast, ToastContainer } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

import axios from 'axios';
import { useState, useEffect } from 'react';

import { useSelector } from "react-redux";


export default function ClientProfile() {
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
    <div className="vertical-container">
      <PageTitle title="Profile" />
      <div className='App-content'>
        {showForm ?
          (<ClientEditProfileBody user={user} setEditProfileClicked={handleEditProfileClicked} onEditProfileSuccess={handleEditProfileSuccess} />) :
          (<ClientProfileBody user={user} setEditProfileClicked={handleEditProfileClicked} />)}
        <ToastContainer />
      </div>
    </div>
  );
}