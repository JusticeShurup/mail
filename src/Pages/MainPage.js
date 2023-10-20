import "./MainPage.css"
import Modal from "../Modules/Modal";
import { useState } from "react";
function MainPage() {
    const [modalActive, setModalActive] = useState(false)

    console.log("Сменилась страница");
    return (
        <div className="mainpage">
            <button className="modal-button" onClick={() => setModalActive(true)}>Зарегистрировать отправление</button>
            <Modal active={modalActive} setActive={setModalActive}/>
        </div>
    );
};

export default MainPage;