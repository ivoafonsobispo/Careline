import './ClientBase.css'
import './ClientHomeBody.css'
import DigitalTwin from './ClientDigitalTwin';
import MeasureStatusBox from './MeasureStatusBox'
import MeasureList from './MeasureList';

import axios from 'axios';
import { useState, useEffect } from 'react';
const baseURL = 'http://localhost:8080/api/patients/1/heartbeats';
const urlDiagnoses = 'http://localhost:8080/api/patients/1/diagnosis';
const urlLastHeartbeat = 'http://localhost:8080/api/patients/1/heartbeats/latest?size=1';
const urlLastTemperature = 'http://localhost:8080/api/patients/1/temperatures/latest?size=1';

export default function ClientHomeBody() {
  const [measures, setMeasures] = useState(null);
  const [diagnoses, setDiagnoses] = useState(null);
  const [lastHeartbeat, setLastHeartbeat] = useState(null);
  const [lastTemperature, setLastTemperature] = useState(null);

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
          console.log("Measures:");
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
            console.log("Diagnoses:");
            console.log(response.data.content);
          })
          .catch(error => {
            // handle the error
            console.log(error);
          });

          axios.get(urlLastHeartbeat, { 
            headers: {
              'Access-Control-Allow-Origin': '*',
            }, 
            proxy: {
              port: 8080
            } })
            .then(response => {
              // handle the response
              setLastHeartbeat(response.data.content);
              console.log("Last Heartbeat:");
              console.log(response.data.content);
            })
            .catch(error => {
              // handle the error
              console.log(error);
            });
          axios.get(urlLastTemperature, { 
            headers: {
              'Access-Control-Allow-Origin': '*',
            }, 
            proxy: {
              port: 8080
            } })
            .then(response => {
              // handle the response
              setLastTemperature(response.data.content);
              console.log("Last Temperature:");
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

  if (!lastHeartbeat) return null;
  let lastHeartbeatValue = Object.values(lastHeartbeat)[0];

  if (!lastTemperature) return null;
  let lastTemperatureValue = Object.values(lastTemperature)[0];

  return (
    <div className='vertical-container gap-vertical' >
      <div className='horizontal-container gap-horizontal' >
        <DigitalTwin value={lastHeartbeatValue.heartbeat}/>
        <div className='vertical-container gap-vertical'>
          <MeasureStatusBox measure={"Heartbeat"} value={lastHeartbeatValue.heartbeat}/>
          <MeasureStatusBox measure={"Temperature"} value={lastTemperatureValue.temperature}/>
        </div>
      </div>

      <div className='horizontal-container gap-horizontal' style={{maxHeight: "300px"}}>
        <MeasureList title={"Measures"} dataArray={measuresArray}/>
        <MeasureList title={"Diagnoses"} dataArray={diagnosesArray}/>
      </div>
    </div>
  );
}
  