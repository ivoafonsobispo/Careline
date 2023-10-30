import './App.css';
import Navbar from './Components/Navbar/Navbar';
import Header from './Components/Header/Header';
// import ClientLeftColumn from './Components/ClientComponents/LeftColumn';
// import ClientRightColumn from './Components/ClientComponents/RightColumn';
// import VerticalBar from './VerticalBar';

import {useState} from 'react';
import { Outlet } from 'react-router-dom';



export default function App() {
  const [isToggleActive, setIsActive] = useState(false);

  const toggleNavbar = () => {
    setIsActive(!isToggleActive);
  }
  

  return (
    <div className='App'>
      <Header isActive={isToggleActive} toggleNavbar={toggleNavbar}/> 

      <div className='App-body'>
          <Navbar isActive={isToggleActive} />
        <div className='App-content-div'>
          <Outlet />
        </div>
      </div>
    </div>
  );
}

