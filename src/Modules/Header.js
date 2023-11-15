import { Link } from 'react-router-dom'
import "./Header.css"
import useAuth from '../hooks/useAuth';


function UserHeader() {
    return (
        <>
            <li><Link to="/user/registryPostalItem">Зарегистрировать отправление</Link></li>
            <li><Link to="/user/postalItems">Отправления</Link></li>
            <li><Link to="/user/postalItemsMovementHistories">Перемещения</Link></li>
            <li><Link to="/login">Выход</Link></li>
        </>
    );
}

function OperatorHeader() {
    return (
        <>
            <li><Link to="/operator/queries">Запросы</Link></li>
            <li><Link to="/operator/postalItems">Отправления</Link></li>
            <li><Link to="/operator/movementHistory">История перемещений</Link></li>
            <li><Link to="/login">Выход</Link></li>
        </>
    )
}

function AdminHeader() {
    return (
        <>
            <li><Link to="/">Главная</Link></li>
            <li><Link to="/user/registryPostalItem">Новая посылка</Link></li>
            <li><Link to="/user/postalItems">Отправления</Link></li>
            <li><Link to="/login">Выход</Link></li>
        </>
    )
}

function DefaultHeader() {
    return (
        <>
            <li><Link to="/">Главная</Link></li>
            <li><Link to="/mailDepartments">Отделения</Link></li>
            <li><Link to="/postalItems">Отправления</Link></li>
            <li><Link to="/historyMovements">История перемещений</Link></li>
            <li><Link to="/login">Вход</Link></li>
        </>
    )
}



function Header() {
    const { auth } = useAuth();

    var headers = {
        "USER": UserHeader(),
        "OPERATOR": OperatorHeader(),
        "ADMIN": AdminHeader()
    };

    return (
        <div className="header-container">
            <img className="image" src="././img/mail.png"></img>
            <header>
                <nav>
                    <ul className="nav__links">
                        {
                            (auth?.roles) ? (headers[auth?.roles[0]]) : DefaultHeader() 
                        }

                    </ul>
                </nav>
            </header>
        </div>
    )
}

export default Header;