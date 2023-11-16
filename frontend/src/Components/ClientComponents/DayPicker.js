import './DayPicker.css'
import Day from "./Day"
import axios from 'axios';
import { useState, useEffect } from 'react';

const baseURL = "http://localhost:4000/v1/previousweek";

export default function DayPicker() {

    const [days, setDays] = useState(null);

    useEffect(() => {
        axios.get(baseURL).then((response) => {
            setDays(response.data);
        });
    }, []);

    if (!days) return null;

    let daysArray = Object.values(days)

    return (
        <div className="App-client-day-picker">
            <div className='App-client-day-picker-header'>
                <div>Schedule</div>
            </div>
            <div className='App-client-day-picker-days'>
                {daysArray.map(day => {
                    return (
                        <Day key={day.Day} dayName={day.Weekday} day={day.Day} />
                    )
                })}
            </div>

        </div>
    );
}