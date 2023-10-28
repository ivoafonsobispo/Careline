import './Navbar.css'

export default function Navbar({isActive}) {

    return(
        <nav style={{ 'maxHeight': isActive ? '200px': '0px'}}>
            <ul>
                <li className='selected'>
                    <i className='fas fa-info-circle'></i>
                    <span>A</span>
                </li>
                <li>B</li>
                <li>C</li>
            </ul>
        </nav>
    );
}
