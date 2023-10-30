import './Navbar.css'

import {HouseFill, HeartFill, FileBarGraphFill, DatabaseFill} from 'react-bootstrap-icons'
import { NavLink } from 'react-router-dom';

export default function Navbar({isActive}) {

    return(
        <nav style={{ 'maxHeight': isActive ? '200px': '0px'}} id='navbar'>
            <ul>
                <NavLink to='/'>
                    <li className='navbar-item'> 
                        <HouseFill size={25}/>
                        <span className='navbar-item-text'>Home</span>
                    </li>
                </NavLink>
                <li className='navbar-item'>
                    <HeartFill size={25}/>
                    <span className='navbar-item-text'>Beats Per Minute</span>
                </li>
                <NavLink to='/diagnoses'>
                    <li className='navbar-item'>
                        <FileBarGraphFill size={25}/>
                        <span className='navbar-item-text'>Diagnoses</span>
                    </li>
                </NavLink>
                <li className='navbar-item'>
                    <DatabaseFill size={25}/>
                    <span className='navbar-item-text'>Measured Data</span>
                </li>
            </ul>
        </nav>
    );
}
