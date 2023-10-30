import ClientLeftColumn from "../Components/ClientComponents/LeftColumn";
import VerticalBar from "../VerticalBar";
import ClientRightColumn from "../Components/ClientComponents/RightColumn";

export default function ClientHome(){
    return (
        <div className='App-content' >
            <ClientLeftColumn />
            <VerticalBar />
            <ClientRightColumn />
        </div>
    );
}