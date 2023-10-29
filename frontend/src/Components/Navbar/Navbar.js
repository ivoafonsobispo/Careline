import './Navbar.css'

import {HouseFill, HeartFill, FileBarGraphFill, DatabaseFill} from 'react-bootstrap-icons'

export default function Navbar({isActive}) {

    return(
        <nav style={{ 'maxHeight': isActive ? '200px': '0px'}}>
            <ul>
                <li className='selected navbar-item'>
                    <HouseFill size={25}/>
                    <span className='navbar-item-text'>Home</span>
                </li>
                <li className='navbar-item'>
                    <HeartFill size={25}/>
                    <span className='navbar-item-text'>Beats Per Minute</span>
                </li>
                <li className='navbar-item'>
                    <FileBarGraphFill size={25}/>
                    <span className='navbar-item-text'>Diagnoses</span>
                </li>
                <li className='navbar-item'>
                    <DatabaseFill size={25}/>
                    <span className='navbar-item-text'>Measured Data</span>
                </li>
            </ul>
        </nav>
    );
}
