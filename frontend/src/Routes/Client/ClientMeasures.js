import PageTitle from "../../Components/PageTitle/PageTitle";
import "../../Components/ClientComponents/ClientBase.css";
import MeasureList from "../../Components/ClientComponents/MeasureList";

//Day Picker
import { format } from 'date-fns';
import { DayPicker } from 'react-day-picker';
import 'react-day-picker/dist/style.css';
import '../../DayPicker.css';

import useWebSocket from 'react-use-websocket';

import axios from 'axios';
import { useState, useEffect, useRef } from 'react';
const urlHeartbeats = 'http://localhost:8080/api/patients/1/heartbeats/latest';
const urlTemperatures = 'http://localhost:8080/api/patients/1/temperatures/latest';


export default function ClientMeasures() {
  const socketUrl = 'ws://localhost:8080/app/websocket-endpoint/topic/heartbeats';

  const { lastMessage, readyState, onMessage } = useWebSocket(socketUrl, {
    onMessage: (event) => {
      console.log('Received message:', event.data);
      // You can perform additional actions with the received message here
    },
  });


  const [selected, setSelected] = useState(new Date());

  let footer = <p>Please pick a day.</p>;
  if (selected) {
    footer = <p>You picked {format(selected, 'PP')}.</p>;
  }

  // const handleMessage = (event) => {
  //   console.log('Received message:', event.data);

  // };

  // // Set up the event listener
  // onMessage(handleMessage);

  const [heartbeats, setHeartbeats] = useState(null);
  const [temperatures, setTemperatures] = useState(null);

  useEffect(() => {
    axios.get(urlHeartbeats, {
      headers: {
        'Access-Control-Allow-Origin': '*',
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

    // const socket = new WebSocket("ws://localhost:8080");

    // // Connection opened
    // socket.addEventListener("open", (event) => {
    //   socket.send("Connection established")
    // })

    // // Listen for messages
    // socket.addEventListener("message", (event) => {
    //   console.log("Message from server ", event.data)
    // })

    // connection.current = ws

    // return () => connection.close()
  }, []);

  if (!heartbeats) return null;
  if (!temperatures) return null;

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