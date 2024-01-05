import PageTitle from "../../Components/PageTitle/PageTitle";
import PatientList from "../../Components/ProfessionalComponents/PatientList";
import "../../Components/ProfessionalComponents/ProfessionalBase.css";

import axios from 'axios';
import { useState, useEffect } from 'react';

import { useSelector } from "react-redux";

export default function ProfessionalPatients() {
    const token = useSelector((state) => state.auth.token);

    const [availablePatients, setAvailablePatients] = useState(null);
    const [associatedPatients, setAssociatedPatients] = useState(null);
    const [sortAssociated, setSortAssociated] = useState("");
    const [sortAvailable, setSortAvailable] = useState("");

    const getPatients = () => {
        axios.get(`http://10.20.229.55/api/professionals/1/patients/available?sort=${sortAvailable}`, {
            headers: {
                'Access-Control-Allow-Origin': '*',
                Authorization: `Bearer ${token}`,
            },
            proxy: {
                port: 8080
            }
        })
            .then(response => {
                setAvailablePatients(response.data.content);
                // console.log(response.data.content);
            })
            .catch(error => {
                console.log(error);
            });
        axios.get(`http://10.20.229.55/api/professionals/1/patients?sort=${sortAssociated}`, {
            headers: {
                'Access-Control-Allow-Origin': '*',
                Authorization: `Bearer ${token}`,
            },
            proxy: {
                port: 8080
            }
        })
            .then(response => {
                setAssociatedPatients(response.data.content);
            })
            .catch(error => {
                console.log(error);
            });
    };

    useEffect(() => {
        getPatients();
    }, [sortAvailable, sortAssociated]);

    const handleAssociatedPatient = () => {
        getPatients();
    }

    const handleSort = (selectedOption, listname) => {
        if (listname === 'Available Patients'){
            setSortAvailable(selectedOption);
        } else {
            setSortAssociated(selectedOption);            
        }
    }

    return (
        <div className="vertical-container">
            <PageTitle title="Patients" />
            <div className='App-content'>
                <PatientList title={"Associated Patients"} dataArray={associatedPatients} sort={handleSort}/>
                <PatientList title={"Available Patients"} dataArray={availablePatients} setAssociatedPatient={handleAssociatedPatient} sort={handleSort} />
            </div>
        </div>
    );
}