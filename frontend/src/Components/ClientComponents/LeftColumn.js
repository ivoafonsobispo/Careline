import './Column.css'
import './LeftColumn.css'

function ClientLeftColumn() {
    return (
        <div className='App-client-column'>
          <div className='App-client-human-layout'>Human Layout</div>
          <div className='App-client-measure-buttons'>
            <div className='App-client-measure-button'>A</div>
            <div className='App-client-measure-button'>B</div>
          </div>
        </div>
    );
}

export default ClientLeftColumn;