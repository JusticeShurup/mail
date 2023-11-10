import {Link} from 'react-router-dom'
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
            <li><Link to="/">Главная</Link></li>
            <li><Link to="/user/registryPostalItem">Новая посылка</Link></li>
            <li><Link to="/user/postalItems">Отправления</Link></li>
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



function Header() {
    const { auth } = useAuth();

    return (
        <div className="header-container">
            <img className="image" src="././img/mail.png"></img>
            <header>
                <nav>
                    <ul className="nav__links">
                        {
                            (auth?.roles) ? (auth.roles[0] === "USER" ? UserHeader() : <></> ) 
                            : (
                                <>
                                <li><Link to="/">Главная</Link></li>
                                <li><Link to="/mailDepartments">Отделения</Link></li>
                                <li><Link to="/postalItems">Отправления</Link></li>
                                <li><Link to="/historyMovements">История перемещений</Link></li>
                                <li><Link to="/login">Вход</Link></li>
                                </>
                                 )
                        }
                        
                    </ul>
                </nav>
            </header>
        </div>
    )
}

export default Header;