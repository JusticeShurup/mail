import "./MainPage.css"
import RegistryPostalItemModal from "../Modules/RegistryPostalItemModal";
import { useState } from "react";

function MainPage() {
    const [modalActive, setModalActive] = useState(false)

    return (
        <div className="mainpage">
            <h1>Добро пожаловать!</h1>
        </div>
    );
};

export default MainPage;