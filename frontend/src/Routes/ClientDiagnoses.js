import PageTitle from "../Components/PageTitle/PageTitle";
import "../Components/ClientComponents/ClientBase.css";
import "./ClientDiagnoses.css";
import ClientDiagnosis from "../Components/ClientComponents/ClientDiagnosis";

import {useState} from 'react';

//Day Picker
import { format } from 'date-fns';
import { DayPicker } from 'react-day-picker';
import 'react-day-picker/dist/style.css';
import '../DayPicker.css';


import axios from 'axios';
import { useEffect } from 'react';
const urlDiagnoses = 'http://localhost:8080/api/patients/1/diagnosis';

export default function ClientDiagnoses() {

    const [selected, setSelected] = useState(new Date());

    let footer = <p>Please pick a day.</p>;
    if (selected) {
        footer = <p>You picked {format(selected, 'PP')}.</p>;
    }

    const [diagnoses, setDiagnoses] = useState(null);
    useEffect(() => {
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

    if (!diagnoses) return null;
    let diagnosesArray = Object.values(diagnoses);

    return(
        <div className="horizontal-container">
            <div className="vertical-container">
                <PageTitle title="Diagnoses"/>
                <div className='App-content'>
                    <div className="vertical-container diagnoses-list" style={{maxHeight: "540px"}}>
                        {diagnosesArray.map(diagnosis => {
                            return (
                                <ClientDiagnosis id={diagnosis.id} description={diagnosis.diagnosis} prescriptions={diagnosis.prescriptions} professional={diagnosis.professional ? diagnosis.professional.name : "Unknown Professional"} date={diagnosis.created_at}/>
                            )
                        })}
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