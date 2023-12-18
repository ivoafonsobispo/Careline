import ClientHomeBody from "../Components/ClientComponents/ClientHomeBody";
import PageTitle from '../Components/PageTitle/PageTitle';
import "../Components/ClientComponents/ClientBase.css";

export default function ClientHome(){
    return (
        <div className="vertical-container">
            <PageTitle title="Home"/>
            <div className='App-content'>
                <ClientHomeBody/>
            </div>
        </div>
    );
}