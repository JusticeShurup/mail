import {Link} from 'react-router-dom'
import "./Header.css"

function Header() {
    return (
        <div className="header-container">
            <img className="image" src="././img/mail.png"></img>
            <header>
                <nav>
                    <ul className="nav__links">
                        <li><Link to="/">Главная</Link></li>
                        <li><Link to="/mailDepartments">Отделения</Link></li>
                        <li><Link to="/postalItems">Отправления</Link></li>
                        <li><Link to="/historyMovement">История перемещений</Link></li>
                    </ul>
                </nav>
            </header>
        </div>
    )
}

export default Header;