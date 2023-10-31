import './Column.css'
import './LeftColumn.css'
import MeasureButtons from './MeasureButtons.js'

export default function ClientLeftColumn() {
    return (
        <div className='App-client-column App-client-column-left'>
          <div className='App-client-human-layout'></div>

          <MeasureButtons />
        </div>
    );
}
