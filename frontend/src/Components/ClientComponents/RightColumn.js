import './Column.css'
import './RightColumn.css'

export default function ClientRightColumn() {
    return (
        <div className='App-client-column'>
            <div className='App-client-datalist'>
                <span>Measured Data List</span>
            </div>
            <hr className='App-client-horizontal-bar'></hr>
            <div className='App-client-datalist'>
                <span>Diagnoses List</span>
            </div>
        </div>
    );
}
