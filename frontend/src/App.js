import './App.css';
import Navbar from './Components/Navbar/Navbar';
import Header from './Components/Header/Header';

import { useState, useEffect } from 'react';

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
import Login from './Routes/Login';

import { useSelector } from 'react-redux';
import { useNavigate } from 'react-router-dom';

export default function App() {

  const [isToggleActive, setIsActive] = useState(false);
  const toggleNavbar = () => {
    setIsActive(!isToggleActive);
  }

  const navigate = useNavigate();
  const token = useSelector((state) => state.auth.token);
  const user = useSelector((state) => state.auth.user);

  useEffect(() => {
    // Check if the token is null
    if (token === null) {
      // Redirect to the '/signin' route
      navigate('/signin');
    }
  }, [token, navigate]);

  return (
    <div className={token !== null && user !== null ? 'App' : ''}>
      {token !== null && user !== null ? (
        <Header isActive={isToggleActive} toggleNavbar={toggleNavbar} userType={user.type} />
      ) : (<></>)}

      <div className={token !== null && user !== null ? 'App-body' : ''}>
        {token !== null && user !== null ? (
          <Navbar isActive={isToggleActive} />
        ) : (<></>)}
        <div className={token !== null && user !== null ? 'App-content-div' : ''}>
          <Routes>
            {user !== null && user.type === 'patient' && (
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
            {user !== null && user.type === 'professional' && (
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
            <Route path="/signin" element={<Login />} />
            <Route path="/*" element={<ErrorPage />} />
          </Routes>
        </div>
      </div>

    </div>
  );
}

