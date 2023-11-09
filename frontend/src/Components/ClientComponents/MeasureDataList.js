import './MeasureDataList.css'
import MeasureDataItem from './MeasureDataItem';

export default function MeasureDataList() {
    return (
        <div className="App-client-measure-data-list">
            <MeasureDataItem label={'Beats Per Minute'} data={60} date={'10:00 AM'}/>
            <MeasureDataItem label={'Body Temperature'} data={30} date={'10:00 AM'}/>
            <MeasureDataItem label={'Beats Per Minute'} data={60} date={'9:00 AM'}/>
            <MeasureDataItem label={'Beats Per Minute'} data={60} date={'9:00 AM'}/>
            <MeasureDataItem label={'Beats Per Minute'} data={60} date={'9:00 AM'}/>
        </div>
    );
}