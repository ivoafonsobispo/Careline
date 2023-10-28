import './App.css';
import Navbar from './Components/Navbar/Navbar';
import Header from './Components/Header/Header';
import ClientLeftColumn from './Components/ClientComponents/LeftColumn';
import ClientRightColumn from './Components/ClientComponents/RightColumn';
import VerticalBar from './VerticalBar';

function App() {
  return (
    <div className="App">
      <Navbar />

      <div className='App-body'>
        <Header /> 
        <div className='App-content'>
          <ClientLeftColumn />
          <VerticalBar />
          <ClientRightColumn />
        </div>
      </div>
    </div>
  );
}

export default App;
