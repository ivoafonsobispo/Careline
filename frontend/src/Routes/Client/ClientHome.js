import ClientHomeBody from "../../Components/ClientComponents/ClientHomeBody";
import PageTitle from '../../Components/PageTitle/PageTitle';
import "../../Components/ClientComponents/ClientBase.css";

import { useState, useEffect } from 'react';

//Day Picker
import { DayPicker } from 'react-day-picker';
import 'react-day-picker/dist/style.css';
import '../../DayPicker.css';

export default function ClientHome() {

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
        footer = <p>You picked {date}.</p>;
    }

    return (
        <div className="horizontal-container">
            <div className="vertical-container">
                <PageTitle title="Home" />
                <div className='App-content'>
                    <ClientHomeBody date={date} />
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