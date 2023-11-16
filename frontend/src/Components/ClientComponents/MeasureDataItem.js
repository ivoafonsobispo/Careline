import './MeasureDataItem.css'

export default function MeasureDataItem({ label, data, date }) {

    console.log("Label: " + label)
    console.log("Data: " + data)
    console.log("Date: " + date)
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