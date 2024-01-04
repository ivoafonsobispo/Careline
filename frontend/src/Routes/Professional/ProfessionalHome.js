import PageTitle from "../../Components/PageTitle/PageTitle";
import "../../Components/ProfessionalComponents/ProfessionalBase.css";
import ProfessionalHomeBody from "../../Components/ProfessionalComponents/ProfessionalHomeBody";

import { useState } from 'react';

//Day Picker
import { format } from 'date-fns';
import { DayPicker } from 'react-day-picker';
import 'react-day-picker/dist/style.css';
import '../../DayPicker.css';

export default function ProfessionalHome() {


    return (
        <div className="vertical-container">
            <PageTitle title="Home" />
            <div className='App-content'>
                <ProfessionalHomeBody />
            </div>
        </div>
    );
}