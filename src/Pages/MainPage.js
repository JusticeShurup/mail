import "./MainPage.css"
import RegistryPostalItemModal from "../Modules/RegistryPostalItemModal";
import { useState } from "react";

function MainPage() {
    const [modalActive, setModalActive] = useState(false)

    return (
        <div className="mainpage">
            <button className="modal-button" onClick={() => setModalActive(true)}>Зарегистрировать отправление</button>
            <RegistryPostalItemModal active={modalActive} setActive={setModalActive}/>
        </div>
    );
};

export default MainPage;