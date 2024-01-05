import PageTitle from "../../Components/PageTitle/PageTitle";
import "../../Components/ClientComponents/ClientBase.css";
import MeasureList from "../../Components/ClientComponents/MeasureList";

//Day Picker
import { format } from 'date-fns';
import { DayPicker } from 'react-day-picker';
import 'react-day-picker/dist/style.css';
import '../../DayPicker.css';

import Stomp from 'stompjs';
import SockJS from 'sockjs-client';

import axios from 'axios';
import { useState, useEffect } from 'react';

import { useSelector } from "react-redux";

export default function ClientMeasures() {
  const token = useSelector((state) => state.auth.token);

  const [selected, setSelected] = useState(new Date());
  const [date, setDate] = useState("2023-12-25");

  useEffect(() => {
    if (selected) {
      const year = selected.getFullYear();
      const month = String(selected.getMonth() + 1).padStart(2, '0');
      const day = String(selected.getDate()).padStart(2, '0');
      const formattedDate = `${year}-${month}-${day}`;
      setDate(formattedDate);
      console.log(formattedDate);
    }
  }, [selected]);

  let footer = <p>Please pick a day.</p>;
  if (selected) {
    footer = <p>You picked {format(selected, 'PP')}.</p>;
  }

  console.log(date);

  const urlHeartbeats = `http://10.20.229.55/api/patients/1/heartbeats/date/${date}`;
  const urlTemperatures = `http://10.20.229.55/api/patients/1/temperatures/date/${date}`;

  const [heartbeats, setHeartbeats] = useState(null);
  const [temperatures, setTemperatures] = useState(null);

  useEffect(() => {
    axios.get(urlHeartbeats, {
      headers: {
        'Access-Control-Allow-Origin': '*',
        Authorization: `Bearer ${token}`,
      },
      proxy: {
        port: 8080
      }
    })
      .then(response => {
        setHeartbeats(response.data.content);
        // console.log(response.data.content);
      })
      .catch(error => {
        console.log(error);
      });

    axios.get(urlTemperatures, {
      headers: {
        'Access-Control-Allow-Origin': '*',
        Authorization: `Bearer ${token}`,
      },
      proxy: {
        port: 8080
      }
    })
      .then(response => {
        setTemperatures(response.data.content);
        // console.log(response.data.content);
      })
      .catch(error => {
        console.log(error);
      });
  }, [urlHeartbeats, urlTemperatures, token]);


  useEffect(() => {
    try {
      const socket = new SockJS('http://10.20.229.55/websocket-endpoint');
      const stompClient = Stomp.over(socket);

      stompClient.connect({}, () => {
        stompClient.subscribe('/topic/heartbeats', (message) => {
          let newHeartbeat = JSON.parse(message.body);
          setHeartbeats((prevHeartbeats) => [newHeartbeat, ...prevHeartbeats]);
        });

        stompClient.subscribe('/topic/temperatures', (message) => {
          let newTemperature = JSON.parse(message.body);
          setTemperatures((prevTemperatures) => [newTemperature, ...prevTemperatures]);
        });
      });

      return () => {
        stompClient.disconnect();
      };
    } catch (error) {
      console.error('WebSocket connection error:', error);
      // Handle the error here, e.g., show a user-friendly message or retry the connection
    }
  }, []);


  // if (!heartbeats) return null;
  // if (!temperatures) return null;

  return (
    <div className="horizontal-container">
      <div className="vertical-container">
        <PageTitle title="Measures" />
        <div className='App-content'>
          <div className='horizontal-container gap-horizontal' style={{ maxHeight: "540px" }}>
            <MeasureList title={"Heartbeat"} dataArray={heartbeats} />
            <MeasureList title={"Temperature"} dataArray={temperatures} />
          </div>
        </div>
      </div>
      <div className='day-picker'>
        <DayPicker
          mode="single"
          selected={selected}
          onSelect={setSelected}
          footer={footer}
          className='day-picker-style'
        />
      </div>
    </div>
  );
}