import PageTitle from "../../Components/PageTitle/PageTitle";
import "../../Components/ProfessionalComponents/ProfessionalBase.css";

import axios from 'axios';
import { useState, useEffect } from 'react';
const urlPatient = 'http://localhost:8080/api/professionals/1/patients/';

export default function ProfessionalPatient() {
    const [patient, setPatient] = useState(null);

    // axios.get(urlAvailablePatients, {
    //     headers: {
    //         'Access-Control-Allow-Origin': '*',
    //     },
    //     proxy: {
    //         port: 8080
    //     }
    // })
    //     .then(response => {
    //         setAvailablePatients(response.data.content);
    //         console.log(response.data.content);
    //     })
    //     .catch(error => {
    //         console.log(error);
    //     });

    return (
        <div className="vertical-container">
            <PageTitle title="Patient"/>
            <div className='App-content'>
                <div className="vertical-container">
                    TODO
                </div>
            </div>
        </div>
    );
}