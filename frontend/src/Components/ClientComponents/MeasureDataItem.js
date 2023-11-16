import './MeasureDataItem.css'

export default function MeasureDataItem({ label, data, date }) {

    return (
        <div className="App-client-measure-data-list-item">
            <div className='measure-data-values'>
                <span>{label}: </span>
                <span className='measure-data'>{data}</span>
            </div>
            <span className='measure-data-date'>{date}</span>
        </div>
    );
}