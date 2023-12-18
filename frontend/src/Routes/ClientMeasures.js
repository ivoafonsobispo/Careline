import PageTitle from "../Components/PageTitle/PageTitle";
import "../Components/ClientComponents/ClientBase.css";
import MeasureList from "../Components/ClientComponents/MeasureList";

//Day Picker
import { format } from 'date-fns';
import { DayPicker } from 'react-day-picker';
import 'react-day-picker/dist/style.css';
import '../DayPicker.css';

import axios from 'axios';
import { useState, useEffect } from 'react';
const urlHeartbeats = 'http://localhost:8080/api/patients/1/heartbeats';
const urlTemperatures = 'http://localhost:8080/api/patients/1/temperatures';

export default function ClientMeasures() {

    const [selected, setSelected] = useState(new Date());

    let footer = <p>Please pick a day.</p>;
    if (selected) {
        footer = <p>You picked {format(selected, 'PP')}.</p>;
    }

    const [heartbeats, setHeartbeats] = useState(null);
    const [temperatures, setTemperatures] = useState(null);

    useEffect(() => {
        axios.get(urlHeartbeats, { 
          headers: {
            'Access-Control-Allow-Origin': '*',
          }, 
          proxy: {
            port: 8080
          } })
          .then(response => {
            // handle the response
            setHeartbeats(response.data.content);
            console.log(response.data.content);
          })
          .catch(error => {
            // handle the error
            console.log(error);
          });

          axios.get(urlTemperatures, { 
            headers: {
              'Access-Control-Allow-Origin': '*',
            }, 
            proxy: {
              port: 8080
            } })
            .then(response => {
              // handle the response
              setTemperatures(response.data.content);
              console.log(response.data.content);
            })
            .catch(error => {
              // handle the error
              console.log(error);
            });
    }, []);

    if (!heartbeats) return null;
    let heartbeatsArray = Object.values(heartbeats);

    if (!temperatures) return null;
    let temperaturesArray = Object.values(temperatures);

    return(
        <div className="horizontal-container">
            <div className="vertical-container">
                <PageTitle title="Measures"/>
                <div className='App-content'>
                    {/* <div className='vertical-container gap-vertical'style={{maxHeight: "540px"}} > */}
                        <div className='horizontal-container gap-horizontal' style={{maxHeight: "540px"}}>
                            <MeasureList title={"Heartbeat"} dataArray={heartbeatsArray}/>
                            <MeasureList title={"Temperature"} dataArray={temperaturesArray}/>
                        </div>
                    {/* </div> */}
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