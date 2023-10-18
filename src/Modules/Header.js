import {Link} from 'react-router-dom'
import "./Header.css"

function Header() {
    return (
        <div className="header-container">
            <img className="image" src="././img/mail (1).png"></img>
            <header>
                <nav>
                    <ul className="nav__links">
                        <li><Link to="/">Главная</Link></li>
                        <li><Link to="/departments">Отделения</Link></li>
                        <li><Link to="/">Отправления</Link></li>
                    </ul>
                </nav>
            </header>
        </div>
    )
}

export default Header;