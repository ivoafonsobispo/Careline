import './MeasureDataList.css'
import MeasureDataItem from './MeasureDataItem';
import axios from 'axios';
import { useState, useEffect } from 'react';

const baseURL = "http://localhost:4000/v1/heartbeat";

function MeasureDataList() {

    const [heartbeats, setHeartbeats] = useState(null);

    useEffect(() => {
        axios.get(baseURL).then((response) => {
            setHeartbeats(response.data);
        });
    }, []);

    if (!heartbeats) return null;

    return (

        <div className="App-client-measure-data-list">
            {heartbeats.heartbeats.map(heartbeat => {
                return (
                    <MeasureDataItem key={heartbeat.id} label={'Beats Per Minute'} data={heartbeat.heartbeat} date={heartbeat.created_at} />
                );
            })}
        </div>
    );
}

export default MeasureDataList;