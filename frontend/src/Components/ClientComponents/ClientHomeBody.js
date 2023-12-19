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

  const [animationSpeed, setAnimationSpeed] = useState(1);
  const heartStyle = {
    animation: `growAndFade ${animationSpeed}s ease-in-out infinite alternate`,
  };

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
        let lastHeartbeatValue = Object.values(response.data.content)[0];
        console.log(lastHeartbeatValue);

        // 60 BPM <=> 1 segundo = 1 beat
        // 80 BPM <=> 1 segundo = 1.33 beats
        // Para tornar mais suave: retirar apenas metade da diferença de 60 para 80
        // ex: 1s - ((80 / 70) - 1s) <=> 1s - (1,14 - 1s) = 1s - 0.14 = 0.86s
        setAnimationSpeed(1 - ((lastHeartbeatValue.heartbeat/(60 + ((lastHeartbeatValue.heartbeat-60)/2))) - 1));
        console.log(1 - ((lastHeartbeatValue.heartbeat/(60 + ((lastHeartbeatValue.heartbeat-60)/2))) - 1));
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
        <DigitalTwin value={lastHeartbeatValue.heartbeat} heartStyle={heartStyle}/>
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
  