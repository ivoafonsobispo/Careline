import './Column.css'
import './RightColumn.css'
import MeasureDataList from './MeasureDataList';
import DiagnosesList from './DiagnosesList';

export default function ClientRightColumn() {
    return (
        <div className='App-client-column'>
            <div className='App-client-datalist'>
                <span>Measured Data List</span>
                <MeasureDataList />
            </div>
            <hr className='App-client-horizontal-bar'></hr>
            <div className='App-client-datalist'>
                <span>Diagnoses List</span>
                <DiagnosesList />
            </div>
        </div>
    );
}
