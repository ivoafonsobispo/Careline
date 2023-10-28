import './Column.css'
import './RightColumn.css'

export default function ClientRightColumn() {
    return (
        <div className='App-client-column'>
            <div className='App-client-datalist'>Lista Dados</div>
            <hr className='App-client-horizontal-bar'></hr>
            <div className='App-client-datalist'>Lista Diagnósticos</div>
        </div>
    );
}
