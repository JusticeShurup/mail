import "./MainPage.css"
import Modal from "../Modules/Modal";
import { useState } from "react";
function MainPage() {
    const [modalActive, setModalActive] = useState(true)

    console.log("Сменилась страница");
    return (
        <div className="mainpage">
            <button onClick={() => setModalActive(true)}>Зарегистрировать отправление</button>
            <Modal active={modalActive} setActive={setModalActive}/>
        </div>
    );
};

export default MainPage;