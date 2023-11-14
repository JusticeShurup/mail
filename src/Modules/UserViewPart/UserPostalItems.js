import { useEffect, useState } from "react";
import SwitchButton from "../Tools/SwitchButton"
import "./UserPostalItems.css"
import useAuth from "../../hooks/useAuth";
import { axiosInstance } from "../../api.config";

export default function UserPostalItems() {
    const [postalItems, setPostalItems] = useState([{}]);

    const [isLoaded, setIsLoaded] = useState(false)
    const { auth } = useAuth()

    const [typeState, setTypeState] = useState(false)

    const handleButtonClick = (state) => {
        setTypeState(state);
    }

    function makeOrderToTake(postalItem) {
        axiosInstance.post("/user/makeOrderToTakePostalItem?id=" + postalItem.id, {
            headers: {
                Authorization: `Bearer ${auth.accessToken}`
            }
        }).then((response) => {
            console.log(response.code);
        }).catch((error) => {
            console.log(error);
        })
    }

    useEffect(() => {
        axiosInstance.get('/user/getUserPostalItems', {
            headers: {
                Authorization: `Bearer ${auth.accessToken}`
            }
        }).then((response) => {
            setPostalItems(Array.from(response.data));
            console.log(response.data);
        }).catch((e) => {
            console.log(e);
        })
        setIsLoaded(true);
    }, [])


    return (
        <div className="user-postal-items-page">
            <h2>Ваши отправления</h2>
            <div className="postal-items-type">
                <SwitchButton
                    left="Входяищие"
                    right="Исходящие"
                    onStateChange={handleButtonClick} />
            </div>
            <div className="">
                <table>
                    <tr>
                        <th>ID</th>
                        <th>Тип посылки</th>
                        <th>Индекс получателя</th>
                        <th>Адрес получателя</th>
                        <th>{typeState ? "Имя получателя" : "Имя отправителя"}</th>
                        <th>Статус</th>
                        <th>Текущий департамент</th>
                    </tr>
                    {isLoaded && postalItems[0].mailDepartment
                        && (

                            typeState ? postalItems.map(outgoingPostalItem => (
                                outgoingPostalItem.senderName === auth.user &&
                                <tr>
                                    <td key={outgoingPostalItem.id}>{outgoingPostalItem.id}</td>
                                    <td key={outgoingPostalItem.id}>{outgoingPostalItem.postalType}</td>
                                    <td key={outgoingPostalItem.id}>{outgoingPostalItem.recipientIndex}</td>
                                    <td key={outgoingPostalItem.id}>{outgoingPostalItem.recipientAddress}</td>
                                    <td key={outgoingPostalItem.id}>{outgoingPostalItem.recipientName}</td>
                                    <td key={outgoingPostalItem.id}>
                                        {

                                            outgoingPostalItem.taken ? "Забран" :
                                                (outgoingPostalItem.recipientIndex === outgoingPostalItem.mailDepartment.index ?
                                                    "Доставлен" : "В доставке")
                                        }
                                    </td>
                                    <td key={outgoingPostalItem.id}>{outgoingPostalItem.mailDepartment.name}</td>
                                </tr>
                            )) : postalItems.map(incomingPostalItem => (
                                incomingPostalItem.recipientName === auth.user &&
                                <tr>
                                    <td key={incomingPostalItem.id}>{incomingPostalItem.id}</td>
                                    <td key={incomingPostalItem.id}>{incomingPostalItem.postalType}</td>
                                    <td key={incomingPostalItem.id}>{incomingPostalItem.recipientIndex}</td>
                                    <td key={incomingPostalItem.id}>{incomingPostalItem.recipientAddress}</td>
                                    <td key={incomingPostalItem.id}>{incomingPostalItem.senderName}</td>
                                    <td key={incomingPostalItem.id}>
                                        {
                                            incomingPostalItem.taken ? "Забран" :
                                                (incomingPostalItem.recipientIndex === incomingPostalItem.mailDepartment.index ?
                                                    <button onClick={e => makeOrderToTake(incomingPostalItem)}>Отправить запрос на взятие</button> : "В доставке")

                                        }
                                    </td>
                                    <td key={incomingPostalItem.id}>{incomingPostalItem.mailDepartment.name}</td>
                                </tr>
                            )))
                    }
                </table>
            </div>
        </div>
    )
}