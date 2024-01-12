import './ProfessionalBase.css'
import './ProfessionalHomeBody.css'
import InfoList from './InfoList'

import axios from 'axios';
import { useState, useEffect } from 'react';

import { useSelector } from 'react-redux';

export default function ProfessionalHomeBody() {
  const token = useSelector((state) => state.auth.token);
  const user = useSelector((state) => state.auth.user);

  const urlAvailablePatients = `/professionals/${user.id}/patients`;

  const [availablePatients, setAvailablePatients] = useState(null);

  useEffect(() => {

    axios.get(urlAvailablePatients, {
      headers: {
        'Access-Control-Allow-Origin': '*',
        Authorization: `Bearer ${token}`,
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
  }, [urlAvailablePatients, token]);

  return (
    <div className='vertical-container gap-vertical' >
      <div className='align-line-row gap-horizontal professional-home-information' >
        <div className='vertical-container' style={{ margin: "0 2%" }}>
          <span className='info-line'>Total triage: <span className='info-line-value'>3</span></span>
          <span className='info-line'>Unreviewed triage: <span className='info-line-value'>2</span></span>
          <span className='info-line'>Reviewed triage: <span className='info-line-value'>1</span></span>
        </div>
        <div className='vertical-bar'></div>
        <div className='vertical-container' style={{ margin: "0 2%" }}>
          <span className='info-line'>Total patients: <span className='info-line-value'>{availablePatients === null || availablePatients.length === 0 ? '---' : availablePatients.length}</span></span>
          <span className='info-line'>Seriously ill patients: <span className='info-line-value'>1</span></span>
          <span className='info-line'>Healthy patients: <span className='info-line-value'>2</span></span>
        </div>
      </div>

      <div className='horizontal-container gap-horizontal' style={{ maxHeight: "300px" }}>
        {!availablePatients || availablePatients.length === 0 ? (
          <div className='no-records'>No patients associated yet.</div>
        ) : (
          <>
            <InfoList title={"Patients"} dataArray={availablePatients} />
          </>
        )}
      </div>
    </div>
  );
}
