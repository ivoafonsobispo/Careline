import logo from './logo.svg';
import './App.css';

function App() {
  return (
    <div className="App">
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
        </div>
      </div>
    </div>
  );
}

export default App;
