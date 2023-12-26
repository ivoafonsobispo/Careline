import './ClientBase.css'
import './ClientHomeBody.css'
import DigitalTwin from './ClientDigitalTwin';
import MeasureStatusBox from './MeasureStatusBox'
import MeasureList from './MeasureList';

import Stomp from 'stompjs';
import SockJS from 'sockjs-client';

import axios from 'axios';
import { useState, useEffect } from 'react';
const baseURL = 'http://localhost:8080/api/patients/1/heartbeats';
const urlDiagnoses = 'http://localhost:8080/api/patients/1/diagnosis';
const urlLastHeartbeat = 'http://localhost:8080/api/patients/1/heartbeats/latest?size=1';
const urlLastTemperature = 'http://localhost:8080/api/patients/1/temperatures/latest?size=1';

export default function ClientHomeBody() {
  const [measures, setMeasures] = useState(null);
  const [diagnoses, setDiagnoses] = useState(null);

  // Heartbeat (value and severity)
  const [lastHeartbeat, setLastHeartbeat] = useState(null);
  const [heartbeatSeverity, setHeartbeatSeverity] = useState(null);

  // Temperature (value and severity)
  const [lastTemperature, setLastTemperature] = useState(null);
  const [temperatureSeverity, setTemperatureSeverity] = useState(null);

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
      }
    })
      .then(response => {
        setMeasures(response.data.content);
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
      }
    })
      .then(response => {
        setDiagnoses(response.data.content);
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
      }
    })
      .then(response => {
        let heartbeatObject = response.data.content[0];
        let heartbeatValue = heartbeatObject.heartbeat;
        setLastHeartbeat(heartbeatObject.heartbeat);
        setHeartbeatSeverity(heartbeatObject.severity);

        // 60 BPM <=> 1 segundo = 1 beat
        // 80 BPM <=> 1 segundo = 1.33 beats
        // Para tornar mais suave: retirar apenas metade da diferença de 60 para 80
        // ex: 1s - ((80 / 70) - 1s) <=> 1s - (1,14 - 1s) = 1s - 0.14 = 0.86s
        setAnimationSpeed(1 - ((heartbeatValue / (60 + ((heartbeatValue - 60) / 2))) - 1));
        // console.log(1 - ((lastHeartbeat/(60 + ((lastHeartbeat-60)/2))) - 1));
      })
      .catch(error => {
        console.log(error);
      });

    axios.get(urlLastTemperature, {
      headers: {
        'Access-Control-Allow-Origin': '*',
      },
      proxy: {
        port: 8080
      }
    })
      .then(response => {
        let temperatureObject = response.data.content[0];
        setLastTemperature(temperatureObject.temperature);
        setTemperatureSeverity(temperatureObject.severity);
        // console.log("Last Temperature:");
        // console.log(response.data.content);
      })
      .catch(error => {
        console.log(error);
      });
  }, []);

  useEffect(() => {
    const socket = new SockJS('http://localhost:8080/websocket-endpoint');
    const stompClient = Stomp.over(socket);

    stompClient.connect({}, () => {
      stompClient.subscribe('/topic/heartbeats', (message) => {
        let newHeartbeat = JSON.parse(message.body);
        setLastHeartbeat(newHeartbeat.heartbeat);
        setHeartbeatSeverity(newHeartbeat.severity);
      });

      stompClient.subscribe('/topic/temperatures', (message) => {
        let newTemperature = JSON.parse(message.body);
        setLastTemperature(newTemperature.temperature);
        setTemperatureSeverity(newTemperature.severity);
      });

      stompClient.subscribe('/topic/diagnosis', (message) => {
        let newDiagnosis = JSON.parse(message.body);
        setDiagnoses((prevDiagnoses) => [newDiagnosis, ...prevDiagnoses]);
      });
    });

    return () => {
      stompClient.disconnect();
    };
  }, []);

  if (!measures) return null;
  if (!diagnoses) return null;
  if (!lastHeartbeat) return null;
  if (!lastTemperature) return null;

  return (
    <div className='vertical-container gap-vertical' >
      <div className='horizontal-container gap-horizontal' >
        <DigitalTwin value={lastHeartbeat} heartStyle={heartStyle} />
        <div className='vertical-container gap-vertical'>
          <MeasureStatusBox measure={"Heartbeat"} value={lastHeartbeat} severity={heartbeatSeverity} />
          <MeasureStatusBox measure={"Temperature"} value={lastTemperature} severity={temperatureSeverity} />
        </div>
      </div>

      <div className='horizontal-container gap-horizontal' style={{ maxHeight: "300px" }}>
        <MeasureList title={"Measures"} dataArray={measures} />
        <MeasureList title={"Diagnoses"} dataArray={diagnoses} />
      </div>
    </div>
  );
}
