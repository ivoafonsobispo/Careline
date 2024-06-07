import PageTitle from "../../Components/PageTitle/PageTitle";
import "../../Components/ClientComponents/ClientBase.css";
import "./ClientProfile.css";
import ClientProfileBody from "../../Components/ClientComponents/ClientProfileBody";
import ClientEditProfileBody from "../../Components/ClientComponents/ClientEditProfileBody";

import { toast, ToastContainer } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

import { useState } from 'react';


export default function ClientProfile() {
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
          (<ClientEditProfileBody setEditProfileClicked={handleEditProfileClicked} onEditProfileSuccess={handleEditProfileSuccess} />) :
          (<ClientProfileBody setEditProfileClicked={handleEditProfileClicked} />)}
        <ToastContainer />
      </div>
    </div>
  );
}