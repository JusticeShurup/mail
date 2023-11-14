import { useState } from 'react';
import './OperatorPostalItems.css'
import { useEffect } from 'react';
import { axiosInstance } from '../../api.config';
import { resolvePath } from 'react-router-dom';
import useAuth from '../../hooks/useAuth';
import TransferPostalItem from './TransferPostalItem';

export default function OperatorPostalItems() {
    const [postalItems, setPostalItems] = useState([{}])
    const { auth } = useAuth();

    const [isLoaded, setIsLoaded] = useState(false)

    const [transferPostalItem, setTransferPostalItem] = useState(null);

    useEffect(() => {
        axiosInstance.get('/operator/getMailDepartmentPostalItems', {
            headers: {
                Authorization: `Bearer ${auth.accessToken}`
            }
        }).then((response) => {
            setPostalItems(Array.from(response.data));
            console.log(postalItems);

        }).catch((error) => {
            console.log(error);
        })
        setIsLoaded(true);
    }, [])

    function removePostalItem(postalItem) {
        setPostalItems(postalItems.filter(p => p.id != postalItem.id));
    }

    return (
        <>
        <div className="operator-postal-items-page">
            <h1>Отправления в вашем отделении</h1>
            <table>
                <tr>
                    <th>ID</th>
                    <th>Тип посылки</th>
                    <th>Индекс получателя</th>
                    <th>Адрес получателя</th>
                    <th>Имя получателя</th>
                    <th>Имя отправителя</th>
                    <th>Статус</th>
                    <th>Действия</th>
                </tr>
                
                {
                    isLoaded && postalItems[0].mailDepartment && (postalItems.map(postalItem => (
                        <tr>
                            <td key={postalItem.id}>{postalItem.id}</td>
                            <td key={postalItem.id}>{postalItem.postalType}</td>
                            <td key={postalItem.id}>{postalItem.recipientIndex}</td>
                            <td key={postalItem.id}>{postalItem.recipientAddress}</td>
                            <td key={postalItem.id}>{postalItem.recipientName}</td>
                            <td key={postalItem.id}>{postalItem.senderName}</td>
                            <td key={postalItem.id}>{postalItem.taken}</td>
                            <td key={postalItem.id}><button onClick={(e) => setTransferPostalItem(postalItem)}>Перенаправить</button></td>
                        </tr>
                    )))
                }
                
            </table>
        </div>
        <TransferPostalItem postalItem={transferPostalItem}/>
        </>
    );

}