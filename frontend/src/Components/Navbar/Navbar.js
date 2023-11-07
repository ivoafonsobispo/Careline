import './Navbar.css'

import {HouseFill, HeartFill, FileBarGraphFill, DatabaseFill, PersonFill} from 'react-bootstrap-icons'
import { NavLink } from 'react-router-dom';

export default function Navbar({isActive}) {

    return(
        <nav style={{ 'maxHeight': isActive ? '200px': '0px'}} id='navbar'>
            <ul className='navbar-logo-area'>
                <li className='navbar-logo-name'>CareLine</li>
                <img />
                <hr></hr>
            </ul>

            <ul className='navbar-buttons-area'>
                <NavLink to='/'>
                    <li className='navbar-item'> 
                        <HouseFill size={25} className='navbar-svg'/>
                        <span className='navbar-item-text'>Home</span>
                    </li>
                </NavLink>
                <NavLink to='/diagnoses'>
                    <li className='navbar-item'>
                        <HeartFill size={25} className='navbar-svg'/>
                        <span className='navbar-item-text'>Beats Per Minute</span>
                    </li>
                </NavLink>
                <NavLink to='/diagnoses'>
                    <li className='navbar-item'>
                        <FileBarGraphFill size={25} className='navbar-svg'/>
                        <span className='navbar-item-text'>Diagnoses</span>
                    </li>
                </NavLink>
                <NavLink to='/diagnoses'>
                    <li className='navbar-item'>
                        <DatabaseFill size={25} className='navbar-svg'/>
                        <span className='navbar-item-text'>Measured Data</span>
                    </li>
                </NavLink>
                <NavLink to='/diagnoses' className={'navbar-last-item'}>
                    <li className='navbar-item'>
                        <PersonFill size={25} className='navbar-svg'/>
                        <span className='navbar-item-text'>Helena</span>
                    </li>
                </NavLink>
            </ul>
        </nav>
    );
}
