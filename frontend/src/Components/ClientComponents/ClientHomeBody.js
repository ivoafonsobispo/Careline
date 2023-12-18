import './ClientBase.css'
import './ClientHomeBody.css'
import DigitalTwin from './ClientDigitalTwin';
import MeasureStatusBox from './MeasureStatusBox'
import MeasureList from './MeasureList';

export default function ClientHomeBody() {
    return (
      <div className='vertical-container gap-vertical' >

        <div className='horizontal-container gap-horizontal' >
          <DigitalTwin/>
          <div className='vertical-container gap-vertical'>
            <MeasureStatusBox measure={"Heartbeat"}/>
            <MeasureStatusBox measure={"Temperature"}/>
          </div>
        </div>
  
        <div className='horizontal-container gap-horizontal' style={{maxHeight: "300px"}}>
          <MeasureList title={"Measures"}/>
          <MeasureList title={"Diagnoses"}/>
        </div>
      </div>
    );
  }
  