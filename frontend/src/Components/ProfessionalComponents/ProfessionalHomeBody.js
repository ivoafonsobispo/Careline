import './ProfessionalBase.css'
import './ProfessionalHomeBody.css'
import InfoList from './InfoList'

import axios from 'axios';
import { useState, useEffect } from 'react';
const urlAvailablePatients = 'http://localhost:8080/api/professionals/1/patients/available';

export default function ProfessionalHomeBody() {
  const [availablePatients, setAvailablePatients] = useState(null);

  useEffect(() => {

    axios.get(urlAvailablePatients, {
      headers: {
        'Access-Control-Allow-Origin': '*',
      },
      proxy: {
        port: 8080
      }
    })
      .then(response => {
        setAvailablePatients(response.data.content);
        // console.log("Available Patients:");
        // console.log(response.data.content);
      })
      .catch(error => {
        console.log(error);
      });
  }, []);


  if (!availablePatients) return null;

  return (
    <div className='vertical-container gap-vertical' >
      <div className='horizontal-container gap-horizontal' style={{ height: "25%" }}>
        <div className='vertical-container gap-vertical'>
          <span className='professional-home-information'>Unreviewed triage: 2</span>
          <span className='professional-home-information'>Associated patients: 1</span>
        </div>
        <div className='vertical-container gap-vertical'>
          <span className='professional-home-information'>Seriously ill patients: 2</span>
          <span className='professional-home-information'>Drones in shipping: 1</span>
        </div>
      </div>

      <div className='horizontal-container gap-horizontal' style={{ maxHeight: "300px" }}>
        <InfoList title={"Patients"} dataArray={availablePatients} />
        <InfoList title={"Diagnoses"} dataArray={[{ "id": "1" }]} />
      </div>
    </div>
  );
}
