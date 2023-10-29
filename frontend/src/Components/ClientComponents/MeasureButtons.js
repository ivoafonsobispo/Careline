import './MeasureButtons.css'

import {HeartFill, ThermometerHigh, PlusLg} from 'react-bootstrap-icons'

export default function MeasureButton() {
    return (
        <div className='App-client-measure-buttons'>
            <button className='App-client-measure-button'><HeartFill size={25} className='measure-button-bootstrap-icon'/></button>
            <button className='App-client-measure-button'><ThermometerHigh size={25} className='measure-button-bootstrap-icon'/></button>
            <button className='App-client-measure-button'><PlusLg size={25} className='measure-button-bootstrap-icon'/></button>
        </div>
    );
}