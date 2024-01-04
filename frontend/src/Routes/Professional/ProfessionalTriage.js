import PageTitle from "../../Components/PageTitle/PageTitle";
import "../../Components/ClientComponents/ClientBase.css";
import ProfessionalTriageComponent from "../../Components/ProfessionalComponents/ProfessionalTriageComponent";
import "./ProfessionalTriage.css"

import { useState } from 'react';
import { ToastContainer } from "react-toastify";

//Day Picker
import { format } from 'date-fns';
import { DayPicker } from 'react-day-picker';
import 'react-day-picker/dist/style.css';
import '../../DayPicker.css';
import classNames from "classnames";

export default function ProfessionalTriage() {
    const [selected, setSelected] = useState(new Date());

    let footer = <p>Please pick a day.</p>;
    if (selected) {
        footer = <p>You picked {format(selected, 'PP')}.</p>;
    }

    const [selectedButton, setButton] = useState("all"); // all; urtriage; rtriage

    return (
        <div className="horizontal-container">
            <div className="vertical-container">
                <PageTitle title="Triage" />
                <div className='App-content'>
                    <div className="vertical-container">
                        <div className="align-line-row" style={{ marginBottom: "2%", gap: "2%" }}>
                            <button className={classNames("professional-triage-button", selectedButton !== 'all' ? "professional-triage-button-inactive" : "")} onClick={() => setButton("all")}>All Triage</button>
                            <button className={classNames("professional-triage-button", selectedButton !== 'urtriage' ? "professional-triage-button-inactive" : "")} onClick={() => setButton("urtriage")}>Unreviewed Triage</button>
                            <button className={classNames("professional-triage-button", selectedButton !== 'rtriage' ? "professional-triage-button-inactive" : "")} onClick={() => setButton("rtriage")}>Reviewed Triage</button>
                        </div>
                        <div className="vertical-container diagnoses-list" style={{ maxHeight: "470px" }}>
                            {selectedButton === 'all' ? (
                                <>
                                    <ProfessionalTriageComponent status={"Reviewed"} />
                                    <ProfessionalTriageComponent status={"Unreviewed"} />
                                </>
                            ) : selectedButton === 'urtriage' ? (
                                <>
                                    <ProfessionalTriageComponent status={"Unreviewed"} />
                                </>
                            ) : (
                                <>
                                    <ProfessionalTriageComponent status={"Reviewed"} />
                                </>
                            )}
                        </div>
                    </div>
                </div>
            </div>
            <div className='day-picker' style={{ background: "var(--professionalLightColor)" }}>
                <DayPicker
                    mode="single"
                    selected={selected}
                    onSelect={setSelected}
                    footer={footer}
                    className='day-picker-style-professional'
                />
            </div>
            <ToastContainer />
        </div>
    );
}