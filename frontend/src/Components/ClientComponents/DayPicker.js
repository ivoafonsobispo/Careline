import './DayPicker.css'
import Day from "./Day"
import { ArrowLeft, ArrowRight } from 'react-bootstrap-icons';

export default function DayPicker() {

    return (
        <div className="App-client-day-picker">
            <div className='App-client-day-picker-header'>
                <div>Schedule</div>
                <div className='App-client-day-picker-arrows'>
                    <ArrowLeft />
                    <ArrowRight />
                </div>
            </div>
            <div className='App-client-day-picker-days'>
                <Day dayName="Mon" day={6}/>
                <Day dayName="Tue" day={7} isSelected={true}/>
                <Day dayName="Wed" day={8}/>
                <Day dayName="Thu" day={9}/>
                <Day dayName="Fri" day={10}/>
                <Day dayName="Sat" day={11}/>
                <Day dayName="Sun" day={12}/>
            </div>
            
        </div>
    );
}