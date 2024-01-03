import './App.css';
import Navbar from './Components/Navbar/Navbar';
import Header from './Components/Header/Header';

import { useState } from 'react';

import { Routes, Route } from 'react-router-dom';
import ClientHome from './Routes/Client/ClientHome';
import ClientDiagnoses from './Routes/Client/ClientDiagnoses'
import ClientMeasures from './Routes/Client/ClientMeasures';
import ClientDrones from './Routes/Client/ClientDrones';
import ClientDrone from './Routes/Client/ClientDrone';
import ClientTriage from './Routes/Client/ClientTriage';
import ClientProfile from './Routes/Client/ClientProfile';

import ProfessionalHome from './Routes/Professional/ProfessionalHome';
import ProfessionalPatients from './Routes/Professional/ProfessionalPatients';
import ProfessionalPatient from './Routes/Professional/ProfessionalPatient';
import ErrorPage from './Routes/ErrorPage';
import ProfessionalProfile from './Routes/Professional/ProfessionalProfile';
import ProfessionalTriage from './Routes/Professional/ProfessionalTriage';
import ProfessionalTriageReview from './Routes/Professional/ProfessionalTriageReview';
import ProfessionalDiagnoses from './Routes/Professional/ProfessionalDiagnoses';
import ProfessionalDiagnosis from './Routes/Professional/ProfessionalDiagnosis';

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
                <Route path="/drones/:id" element={<ClientDrone />} />
                <Route path="/triage" element={<ClientTriage />} />
                <Route path="/profile" element={<ClientProfile />} />
              </>
            )}
            {userType === 'professional' && (
              <>
                <Route path="/" element={<ProfessionalHome />} />
                <Route path="/patients" element={<ProfessionalPatients />} />
                <Route path="/patient/:id" element={<ProfessionalPatient />} />
                <Route path="/profile" element={<ProfessionalProfile />} />
                <Route path="/diagnoses" element={<ProfessionalDiagnoses />} />
                <Route path="/diagnosis" element={<ProfessionalDiagnosis />} />
                <Route path="/triage" element={<ProfessionalTriage />} />
                <Route path="/triage/:id/review" element={<ProfessionalTriageReview />} />
              </>
            )}
            <Route path="/*" element={<ErrorPage />} />
          </Routes>
        </div>
      </div>
    </div>
  );
}

