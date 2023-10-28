<<<<<<< HEAD
import logo from './logo.svg';
import './App.css';
=======
import './App.css';
import Navbar from './Components/Navbar/Navbar';
import Header from './Components/Header/Header';
import ClientLeftColumn from './Components/ClientComponents/LeftColumn';
import ClientRightColumn from './Components/ClientComponents/RightColumn';
import VerticalBar from './VerticalBar';
>>>>>>> 687fd8c4c2eb82b30f6d338123edc32273c1c29f

function App() {
  return (
    <div className="App">
<<<<<<< HEAD
      <div className='App-side-bar'>
        <div>A</div>
        <div>B</div>
        <div>C</div>
      </div>

      <div className='App-body'>
        <div className='App-header'>
          <div className='App-header-name'>Welcome back, Helena</div>
          <div className='App-header-logo'>Image</div>
        </div>  
        <div className='App-content'>
          <div className='App-client-colum'>
            <div className='App-client-human-layout'>Human Layout</div>
            <div className='App-client-measure-buttons'>
              <div className='App-client-measure-button'>A</div>
              <div className='App-client-measure-button'>B</div>
            </div>
          </div>
          <div className='App-divider-bar'></div>
          <div className='App-client-column'>
            <div className='App-client-datalist'></div>
            <hr></hr>
            <div className='App-client-datalist'></div>
          </div>
=======
      <Navbar />

      <div className='App-body'>
        <Header /> 
        <div className='App-content'>
          <ClientLeftColumn />
          <VerticalBar />
          <ClientRightColumn />
>>>>>>> 687fd8c4c2eb82b30f6d338123edc32273c1c29f
        </div>
      </div>
    </div>
  );
}

export default App;
