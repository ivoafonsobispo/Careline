import classNames from 'classnames';
import './Header.css';

export default function Header({isActive, toggleNavbar}) {

    return(
        <header>
            <div id='logo'>
                <img alt="CL"></img>
            </div>
            <div className='App-header-name'>Welcome back, Helena</div>
            <div className='App-header-avatar'>
                <span className='App-header-avatar-name'>Helena</span>
                <img src="./images/blank-profile-picture.png" alt='user-img' ></img>
            </div>
            <div id='menuIcon' onClick={toggleNavbar}>
                <div className={classNames(isActive ? 'change-bar1' : '', 'bar1')}></div>
                <div className={classNames(isActive ? 'change-bar2' : '', 'bar2')}></div>
                <div className={classNames(isActive ? 'change-bar3' : '', 'bar3')}></div>
            </div>
        </header>
    );
}
