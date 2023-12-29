import './App.css';
import Navbar from './Components/Navbar/Navbar';
import Header from './Components/Header/Header';

import { useState } from 'react';

import { Routes, Route } from 'react-router-dom';
import ClientHome from './Routes/Client/ClientHome';
import ClientDiagnoses from './Routes/Client/ClientDiagnoses'
import ClientMeasures from './Routes/Client/ClientMeasures';
import ClientDrones from './Routes/Client/ClientDrones';
import ClientTriage from './Routes/Client/ClientTriage';
import ClientProfile from './Routes/Client/ClientProfile';

import ProfessionalHome from './Routes/Professional/ProfessionalHome';
import ProfessionalPatients from './Routes/Professional/ProfessionalPatients';
import ProfessionalPatient from './Routes/Professional/ProfessionalPatient';
import ErrorPage from './Routes/ErrorPage';

export default function App() {
  const userType = 'professional';

  const [isToggleActive, setIsActive] = useState(false);
  const toggleNavbar = () => {
    setIsActive(!isToggleActive);
  }

  return (
    <div className='App'>
      <Header isActive={isToggleActive} toggleNavbar={toggleNavbar} userType={userType} />

      <div className='App-body'>
        <Navbar isActive={isToggleActive} userType={userType} />
        <div className='App-content-div'>
          <Routes>
            {userType === 'patient' && (
              <>
                <Route path="/" element={<ClientHome />} />
                <Route path="/diagnoses" element={<ClientDiagnoses />} />
                <Route path="/measures" element={<ClientMeasures />} />
                <Route path="/drones" element={<ClientDrones />} />
                <Route path="/triage" element={<ClientTriage />} />
                <Route path="/profile" element={<ClientProfile />} />
              </>
            )}
            {userType === 'professional' && (
              <>
                <Route path="/" element={<ProfessionalHome />} />
                <Route path="/patients" element={<ProfessionalPatients />} />
                <Route path="/patient" element={<ProfessionalPatient />} />
              </>
            )}
            <Route path="/*" element={<ErrorPage />} />
          </Routes>
        </div>
      </div>
    </div>
  );
}

