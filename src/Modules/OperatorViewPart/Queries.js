import { useEffect, useState } from 'react';
import useAuth from '../../hooks/useAuth';
import './Queries.css'
import { axiosInstance } from '../../api.config';
import SwitchButton from '../Tools/SwitchButton';
import RegistryPostalItemModal from '../RegistryPostalItemModal';


export default function Queries() {
    const [postalItemsToRegistry, setPostalItemsToRegistry] = useState([{}]);
    const [postalItemsToTake, setPostalItemsToTake] = useState([{}]);
    const { auth } = useAuth();

    const [typeState, setTypeState] = useState(false)
    const [isLoaded, setIsLoaded] = useState(false);

    const handleButtonClick = (state) => {
        setTypeState(state);
    }


    useEffect(() => {
        axiosInstance.get('/operator/getConsiderationToRegistryPostalItems', {
            headers: {
                Authorization: `Bearer ${auth.accessToken}`
            }
        }).then((response) => {
            setPostalItemsToRegistry(Array.from(response.data));
        }).catch((error) => {
            console.log(error);
        }).then((result) => {
            axiosInstance.get('/operator/getConsiderationToTakePostalItems', {
                headers: {
                    Authorization: `Bearer ${auth.accessToken}`
                }
            }).then((response) => {
                setPostalItemsToTake(Array.from(response.data));
                setIsLoaded(true);
            }).catch((error) => {
                console.log(error);
            });
        })
        console.log(postalItemsToRegistry);
    }, [])

    function confirmRegistryPostalItem(postalItem) {
        var bodyFormData = new FormData();
        bodyFormData.append("postalItemId", postalItem.id);
        axiosInstance.post(`/operator/confirmRegistryPostalItem`, bodyFormData, {
            headers: {
                Authorization: `Bearer ${auth.accessToken}`
            }
        }).then((response) => {
            if (response.status == 200) {
                setPostalItemsToRegistry(postalItemsToRegistry.filter(p => p.id != postalItem.id));
            }
        }).catch((error) => {
            console.log(error);
        })

    }

    function declineRegistryPostalItem(postalItem) {
        var bodyFormData = new FormData();
        bodyFormData.append("postalItemId", postalItem.id);
        axiosInstance.post(`/operator/declineRegistryPostalItem`, bodyFormData, {
            headers: {
                Authorization: `Bearer ${auth.accessToken}`
            }
        }).then((response) => {
            console.log(response);
        }).catch((error) => {
            console.log(error);
        })
    }

    function renderPostalItems() {

        function returnPostalItemsToRegistry() {
            return postalItemsToRegistry.map((postalItem) =>
            (
                <tr>
                    <td key={postalItem.id}>{postalItem.id}</td>
                    <td key={postalItem.id}>{postalItem.postalType}</td>
                    <td key={postalItem.id}>{postalItem.recipientIndex}</td>
                    <td key={postalItem.id}>{postalItem.recipientAddress}</td>
                    <td key={postalItem.id}>{postalItem.senderName}</td>
                    <td key={postalItem.id}>{postalItem.recipientName}</td>
                    <td key={postalItem.id}>Требует подтверждения</td>
                    <td>
                        <button onClick={(e) => confirmRegistryPostalItem(postalItem)}>Подтвердить</button>
                        <button onClick={(e) => declineRegistryPostalItem(postalItem)}>Отклонить</button>
                    </td>
                </tr>
            ))
        }

        function returnPostalItemsToTake() {
            return postalItemsToTake.map((postalItem) =>
            (
                <tr>
                    <td key={postalItem.id}>{postalItem.id}</td>
                    <td key={postalItem.id}>{postalItem.postalType}</td>
                    <td key={postalItem.id}>{postalItem.recipientIndex}</td>
                    <td key={postalItem.id}>{postalItem.recipientAddress}</td>
                    <td key={postalItem.id}>{postalItem.senderName}</td>
                    <td key={postalItem.id}>{postalItem.recipientName}</td>
                    <td key={postalItem.id}>Требует подтверждения</td>
                    <td>
                        <button onClick={(e) => confirmRegistryPostalItem(postalItem)}>Выдать</button>
                    </td>
                </tr>
            ))
        }

        return (
            <>
                {!typeState ? returnPostalItemsToRegistry() : returnPostalItemsToTake()}
            </>
        )
    }


    return (
        <div className="postal-item-queries-page">
            <SwitchButton left="Регистрация" right="Выдача" onStateChange={handleButtonClick}></SwitchButton>
            <table>
                <tr>
                    <th>ID</th>
                    <th>Тип посылки</th>
                    <th>Индекс получателя</th>
                    <th>Адрес получателя</th>
                    <th>Имя отправителя</th>
                    <th>Имя получателя</th>
                    <th>Статус</th>
                    <th>Действие</th>
                </tr>
                {
                    isLoaded && renderPostalItems()
                }
            </table>
        </div>
    );
} 