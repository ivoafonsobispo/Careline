import './App.css';
import Navbar from './Components/Navbar/Navbar';
import Header from './Components/Header/Header';

import {useState} from 'react';
import { Outlet } from 'react-router-dom';

//Day Picker
import { format } from 'date-fns';
import { DayPicker } from 'react-day-picker';
import 'react-day-picker/dist/style.css';
import './DayPicker.css';

export default function App() {
  const [isToggleActive, setIsActive] = useState(false);

  const toggleNavbar = () => {
    setIsActive(!isToggleActive);
  }

  const [selected, setSelected] = useState(new Date());

  let footer = <p>Please pick a day.</p>;
  if (selected) {
    footer = <p>You picked {format(selected, 'PP')}.</p>;
  }
  

  return (
    <div className='App'>
      <Header isActive={isToggleActive} toggleNavbar={toggleNavbar}/> 

      <div className='App-body'>
          <Navbar isActive={isToggleActive} />
        <div className='App-content-div'>
          <Outlet />
        </div>
        <div className='day-picker'>
          <DayPicker
            mode="single"
            selected={selected}
            onSelect={setSelected}
            footer={footer}
            className='day-picker-style'
          />
        </div>
      </div>
    </div>
  );
}

