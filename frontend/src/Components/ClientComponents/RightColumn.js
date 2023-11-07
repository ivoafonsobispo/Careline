import './Column.css'
import './RightColumn.css'
import MeasureDataList from './MeasureDataList';
import DiagnosesList from './DiagnosesList';
import DayPicker from './DayPicker';

export default function ClientRightColumn() {
    return (
        <div className='App-client-column App-client-column-right'>
            <DayPicker />
            <div className='App-client-lists'>
                <div className='App-client-datalist'>
                    <span>Measured Data List</span>
                    <MeasureDataList />
                </div>
                {/* <hr className='App-client-horizontal-bar'></hr> */}
                <div className='App-client-datalist'>
                    <span>Diagnoses List</span>
                    <DiagnosesList />
                </div>
            </div>
        </div>
    );
}
