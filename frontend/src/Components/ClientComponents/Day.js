import classNames from 'classnames';
import './Day.css'

export default function Day({dayName,day,isSelected}) {
    
    return (
        <div className={classNames('App-client-day-picker-day', isSelected ? 'App-client-day-picker-selected' : '')}>
            <span>{day}</span>
            <span className='day-name'>{dayName}</span>
        </div>        
    );
}