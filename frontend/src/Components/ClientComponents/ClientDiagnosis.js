import './ClientDiagnosis.css'
import './ClientBase.css'

import {Paperclip, Pen, Download} from 'react-bootstrap-icons'


import axios from 'axios';
import { useEffect } from 'react';


export default function ClientDiagnosis({id, description, prescriptions, professional, date}) {
    const urlPdf = `http://localhost:8080/api/patients/1/diagnosis/${id}/pdf`;

    // const [pdf, setPdf] = useState(null);
    const pdfDownloadClick = () => {
        axios.get(urlPdf, { 
          headers: {
            'Access-Control-Allow-Origin': '*',
          }, 
          proxy: {
            port: 8080
          } })
          .then(response => {
            // handle the response
            console.log(response.data);
          })
          .catch(error => {
            // handle the error
            console.log(error);
          });
    };

    return (
        <div className="client-diagnosis-box">
            <div className='align-line-row'>
                <span className='align-line-row'><Paperclip size={20}/> Diagnosis {id}</span>
                <span className='diagnosis-download-button' onClick={pdfDownloadClick}><Download size={20}/></span>
            </div>
            <hr className='diagnosis-hr'></hr>
            
            <div className='horizontal-container client-diagnosis-information-box'>
                <div >
                <span>Description: </span>
                </div>
                <div className='client-diagnosis-information'>
                    <span>{description}</span>
                </div>

                <div >
                    <span>Prescriptions: </span>
                </div>
                <div className='client-diagnosis-information'>
                    <span>{prescriptions.join(', ')}</span>
                </div>
            </div>
            <hr className='diagnosis-hr'></hr>
            
            <div>
                <span className='align-line-row'><Pen size={15}/> &nbsp; By: {professional}</span>
            </div>
            
            <span className='diagnosis-date'>{date}</span>
        </div>        
    );
}