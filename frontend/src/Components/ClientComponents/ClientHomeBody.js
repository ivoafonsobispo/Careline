import './ClientBase.css'
import './ClientHomeBody.css'
import DigitalTwin from './ClientDigitalTwin';
import MeasureStatusBox from './MeasureStatusBox'
import MeasureList from './MeasureList';

import axios from 'axios';
import { useState, useEffect } from 'react';
const baseURL = 'http://localhost:8080/api/patients/1/heartbeats';
const urlDiagnoses = 'http://localhost:8080/api/patients/1/diagnosis';

export default function ClientHomeBody() {
  const [measures, setMeasures] = useState(null);
  const [diagnoses, setDiagnoses] = useState(null);

  useEffect(() => {
      axios.get(baseURL, { 
        headers: {
          'Access-Control-Allow-Origin': '*',
        }, 
        proxy: {
          port: 8080
        } })
        .then(response => {
          // handle the response
          setMeasures(response.data.content);
          console.log(response.data.content);
        })
        .catch(error => {
          // handle the error
          console.log(error);
        });

        axios.get(urlDiagnoses, { 
          headers: {
            'Access-Control-Allow-Origin': '*',
          }, 
          proxy: {
            port: 8080
          } })
          .then(response => {
            // handle the response
            setDiagnoses(response.data.content);
            console.log(response.data.content);
          })
          .catch(error => {
            // handle the error
            console.log(error);
          });
  }, []);

  if (!measures) return null;
  let measuresArray = Object.values(measures);

  if (!diagnoses) return null;
  let diagnosesArray = Object.values(diagnoses);


  return (
    <div className='vertical-container gap-vertical' >
      <div className='horizontal-container gap-horizontal' >
        <DigitalTwin/>
        <div className='vertical-container gap-vertical'>
          <MeasureStatusBox measure={"Heartbeat"}/>
          <MeasureStatusBox measure={"Temperature"}/>
        </div>
      </div>

      <div className='horizontal-container gap-horizontal' style={{maxHeight: "300px"}}>
        <MeasureList title={"Measures"} dataArray={measuresArray}/>
        <MeasureList title={"Diagnoses"} dataArray={diagnosesArray}/>
      </div>
    </div>
  );
}
  