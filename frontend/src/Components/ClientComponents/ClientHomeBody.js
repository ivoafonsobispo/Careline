import './ClientBase.css'
import './ClientHomeBody.css'
import DigitalTwin from './ClientDigitalTwin';
import MeasureStatusBox from './MeasureStatusBox'
import MeasureList from './MeasureList';

import axios from 'axios';
import { useState, useEffect } from 'react';
const baseURL = 'http://localhost:8080/api/patients/1/heartbeats';

export default function ClientHomeBody() {
  const [measures, setMeasures] = useState(null);

  // useEffect(() => {
  //     axios.get(baseURL, { 
  //       headers: {
  //         'Access-Control-Allow-Origin': '*',
  //       }, 
  //       proxy: {
  //         port: 8080
  //       } })
  //       .then(response => {
  //         // handle the response
  //         setMeasures(response.data);
  //       })
  //       .catch(error => {
  //         // handle the error
  //         console.log(error);
  //       });
  // }, []);

  // if (!measures) return null;

  // let measuresArray = Object.values(measures)


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
          <MeasureList title={"Measures"}/>
          <MeasureList title={"Diagnoses"}/>
        </div>
      </div>
    );
  }
  