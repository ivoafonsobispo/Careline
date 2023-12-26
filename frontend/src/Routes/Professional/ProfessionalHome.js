import PageTitle from "../../Components/PageTitle/PageTitle";
import "../../Components/ProfessionalComponents/ProfessionalBase.css";
import ProfessionalHomeBody from "../../Components/ProfessionalComponents/ProfessionalHomeBody";

import {useState} from 'react';

//Day Picker
import { format } from 'date-fns';
import { DayPicker } from 'react-day-picker';
import 'react-day-picker/dist/style.css';
import '../../DayPicker.css';

export default function ProfessionalHome() {

    const [selected, setSelected] = useState(new Date());

    let footer = <p>Please pick a day.</p>;
    if (selected) {
        footer = <p>You picked {format(selected, 'PP')}.</p>;
    }

    return(
        <div className="horizontal-container">
            <div className="vertical-container">
                <PageTitle title="Home"/>
                <div className='App-content'>
                    <ProfessionalHomeBody/>
                </div>
            </div>
            <div className='day-picker' style={{background: "var(--professionalLightColor)"}}>
            <DayPicker
                mode="single"
                selected={selected}
                onSelect={setSelected}
                footer={footer}
                className='day-picker-style-professional'
            />
            </div>
        </div>
    );
}