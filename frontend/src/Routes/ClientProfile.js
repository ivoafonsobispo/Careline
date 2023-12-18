import PageTitle from "../Components/PageTitle/PageTitle";
import "../Components/ClientComponents/ClientBase.css";
import "./ClientProfile.css";

export default function ClientDrones() {
    return(
        <div className="vertical-container">
            <PageTitle title="Profile"/>
            <div className='App-content'>
                <div className="vertical-container profile-container">
                    <span className="profile-field"><span className="profile-field-title">Name:</span> Helena</span>
                    <span className="profile-field"><span className="profile-field-title">Email:</span> Helena@gmail.com</span>
                    <span className="profile-field"><span className="profile-field-title">Password:</span> *******</span>
                    <span className="profile-field"><span className="profile-field-title">NUS:</span> 111228282</span>
                </div>
            </div>
        </div>
    );
}