import './Navbar.css'

import {HouseFill, HeartFill, FileBarGraphFill, MapFill, PersonFill, FileEarmarkMedicalFill} from 'react-bootstrap-icons'
import { NavLink } from 'react-router-dom';

export default function Navbar({isActive}) {

    return(
        <nav style={{ 'maxHeight': isActive ? '200px': '0px'}} id='navbar'>
            <ul className='navbar-logo-area'>
                <li className='navbar-logo-name'>CareLine</li>
                <hr></hr>
            </ul>

            <ul className='navbar-buttons-area'>
                <NavLink to='/'>
                    <li className='navbar-item'> 
                        <HouseFill size={25} className='navbar-svg'/>
                        <span className='navbar-item-text'>Home</span>
                    </li>
                </NavLink>
                <NavLink to='/measures'>
                    <li className='navbar-item'>
                        <HeartFill size={25} className='navbar-svg'/>
                        <span className='navbar-item-text'>Measures</span>
                    </li>
                </NavLink>
                <NavLink to='/diagnoses'>
                    <li className='navbar-item'>
                        <FileBarGraphFill size={25} className='navbar-svg'/>
                        <span className='navbar-item-text'>Diagnoses</span>
                    </li>
                </NavLink>
                <NavLink to='/triage'>
                    <li className='navbar-item'>
                        <FileEarmarkMedicalFill size={25} className='navbar-svg'/>
                        <span className='navbar-item-text'>Triage</span>
                    </li>
                </NavLink>
                <NavLink to='/drones'>
                    <li className='navbar-item'>
                        <MapFill size={25} className='navbar-svg'/>
                        <span className='navbar-item-text'>Drones</span>
                    </li>
                </NavLink>
                <NavLink to='/profile' className={'navbar-last-item'}>
                    <li className='navbar-item'>
                        <PersonFill size={25} className='navbar-svg'/>
                        <span className='navbar-item-text'>Helena</span>
                    </li>
                </NavLink>
            </ul>
        </nav>
    );
}
