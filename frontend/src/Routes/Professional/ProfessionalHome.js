import PageTitle from "../../Components/PageTitle/PageTitle";
import "../../Components/ProfessionalComponents/ProfessionalBase.css";
import ProfessionalHomeBody from "../../Components/ProfessionalComponents/ProfessionalHomeBody";


export default function ProfessionalHome() {


    return (
        <div className="vertical-container">
            <PageTitle title="Home" />
            <div className='App-content'>
                <ProfessionalHomeBody />
            </div>
        </div>
    );
}