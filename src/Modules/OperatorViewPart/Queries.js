import { useEffect, useState } from 'react';
import useAuth from '../../hooks/useAuth';
import './Queries.css'
import { axiosInstance } from '../../api.config';
import SwitchButton from '../Tools/SwitchButton';


export default function Queries() {
    const [postalItems, setPostalItems] = useState([{}]);
    const {auth} = useAuth();

    const [state, setState] = useState(false);
    const [isLoaded, setIsLoaded] = useState(false);



    useEffect(() => {
        axiosInstance.get('/operator/getConsiderationToTakePostalItems', {
            headers:{
                Authorization: `Bearer ${auth.accessToken}`
            }
        }).then((response) => {
            setPostalItems(Array.from(response.data));
            setIsLoaded(true);
        }).catch((error) => {
            console.log(error);
        })
    }, [])  




    return ( 
        <div className="postal-item-queries-page">
            <SwitchButton left="Регистрация" right="Выдача"></SwitchButton>
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
                    isLoaded && postalItems.map((postalItem) =>
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
                                    <button>Подтвердить</button>
                                    <button>Отклонить</button>
                                </td>
                                
                            </tr>
                        )
                    )
                }


            </table>
        </div>
    );
} 