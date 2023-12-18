import PageTitle from "../Components/PageTitle/PageTitle";
import "../Components/ClientComponents/ClientBase.css";
import MeasureList from "../Components/ClientComponents/MeasureList";

export default function ClientMeasures() {
    return(
        <div className="vertical-container">
            <PageTitle title="Measures"/>
            <div className='App-content'>
                {/* <div className='vertical-container gap-vertical'style={{maxHeight: "540px"}} > */}
                    <div className='horizontal-container gap-horizontal' style={{maxHeight: "540px"}}>
                        <MeasureList title={"Heartbeat"} />
                        <MeasureList title={"Temperature"} />
                    </div>
                {/* </div> */}
            </div>
        </div>
    );
}