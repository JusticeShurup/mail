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
                    isLoaded && postalItems[0].mailDepartment && (postalItems.map((postalItem, index) => (
                        <tr key={index}>
                            <td>{postalItem.id}</td>
                            <td>{postalItem.postalType}</td>
                            <td>{postalItem.recipientIndex}</td>
                            <td>{postalItem.recipientAddress}</td>
                            <td>{postalItem.recipientName}</td>
                            <td>{postalItem.senderName}</td>
                            <td>{postalItem.taken}</td>
                            <td><button onClick={(e) => setTransferPostalItem(postalItem)}>Перенаправить</button></td>
                        </tr>
                    )))
                }
                
            </table>
        </div>
        <TransferPostalItem postalItem={transferPostalItem}/>
        </>
    );

}