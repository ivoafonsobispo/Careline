import PageTitle from "../../Components/PageTitle/PageTitle";
import "../../Components/ClientComponents/ClientBase.css";
import classNames from "classnames";
import ClientDroneComponent from "../../Components/ClientComponents/ClientDroneComponent";

import { useState } from 'react';

//Day Picker
import { format } from 'date-fns';
import { DayPicker } from 'react-day-picker';
import 'react-day-picker/dist/style.css';
import '../../DayPicker.css';

export default function ClientDrones() {
    const [selected, setSelected] = useState(new Date());

    let footer = <p>Please pick a day.</p>;
    if (selected) {
        footer = <p>You picked {format(selected, 'PP')}.</p>;
    }

    const [selectedButton, setButton] = useState("all"); // all; urtriage; rtriage

    return (

        <div className="horizontal-container">
            <div className="vertical-container">
                <PageTitle title="Drones" />
                <div className='App-content'>
                    <div className="vertical-container">
                        <div className="align-line-row" style={{ marginBottom: "2%", gap: "2%" }}>
                            <button className={classNames("triage-button", selectedButton !== 'all' ? "triage-button-inactive" : "")} onClick={() => setButton("all")}>All Drones</button>
                            <button className={classNames("triage-button", selectedButton !== 'urtriage' ? "triage-button-inactive" : "")} onClick={() => setButton("urtriage")}>Drones Inshipping</button>
                            <button className={classNames("triage-button", selectedButton !== 'rtriage' ? "triage-button-inactive" : "")} onClick={() => setButton("rtriage")}>Drones Shipped</button>
                        </div>
                        <div className="vertical-container diagnoses-list" style={{ maxHeight: "470px" }}>
                            {selectedButton === 'all' ? (
                                <>
                                    <ClientDroneComponent />
                                    <ClientDroneComponent />
                                </>
                            ) : selectedButton === 'urtriage' ? (
                                <>
                                    <ClientDroneComponent />
                                </>
                            ) : (
                                <>
                                    <ClientDroneComponent />
                                </>
                            )}
                        </div>
                    </div>
                </div>
            </div>
            <div className='day-picker'>
                <DayPicker
                    mode="single"
                    selected={selected}
                    onSelect={setSelected}
                    footer={footer}
                    className='day-picker-style'
                />
            </div>
        </div>
    );
}