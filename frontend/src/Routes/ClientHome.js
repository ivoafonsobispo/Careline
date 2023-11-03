import ClientLeftColumn from "../Components/ClientComponents/LeftColumn";
import ClientRightColumn from "../Components/ClientComponents/RightColumn";
export default function ClientHome(){
    return (
        <div className='App-content'>
            <ClientLeftColumn />
            <ClientRightColumn />
        </div>
    );
}