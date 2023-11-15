import './OperatorMovementHistory.css'
import { useEffect, useState } from "react";
import { axiosInstance } from '../../api.config';
import useAuth from '../../hooks/useAuth';

export default function OperatorMovementHistory() {
    const [movementHistory, setMovementHistory] = useState([]);
    const [postalItems, setPostalItems] = useState([]);


    const [isLoaded, setIsLoaded] = useState(false)

    const { auth } = useAuth();

    console.log(postalItems);

    function getPostalItems() {
        let postalItems = new Array();
        movementHistory.forEach((movementHistoryItem, index) => {

            if (!postalItems.find(p => p.id == movementHistoryItem.postalItem.id)) {
                postalItems.push(movementHistoryItem.postalItem);
                postalItems[postalItems.length - 1].movementHistory = new Array();
                postalItems[postalItems.length - 1].movementHistory.push(movementHistoryItem);
            }
            else {
                postalItems.find(p => p.id == movementHistoryItem.postalItem.id).movementHistory.push(movementHistoryItem);
            }
        });
        return postalItems;
    }

    useEffect(() => {
        axiosInstance.get("/operator/getMailDepartmentMovementHistory", {
            headers: {
                Authorization: `Bearer ${auth.accessToken}`
            }
        }).then((response) => {
            setMovementHistory(Array.from(response.data));
        })
        .catch((error) => {
            console.log(error);
        })
    }, [])

    useEffect(() => {
        if (movementHistory.length > 0) {
            setPostalItems(getPostalItems());
            setIsLoaded(true);
        }
    }, [movementHistory])


    return (
        <div className="operator-movement-history-page">
            {
                isLoaded && postalItems.length > 0 ? postalItems.map((postalItem, postalItemindex) => (
                    <>
                        <h1>История посылки {postalItem.id}</h1>
                        <table>
                            <tr>
                                <th>ID истории</th>
                                <th>Статус</th>
                                <th>ID посылки</th>
                            </tr>
                            {
                                postalItem.movementHistory ? postalItem.movementHistory.map((movementHistoryItem, index) => (
                                    <tr key={index}>
                                        <td>{movementHistoryItem.id}</td>
                                        <td>{movementHistoryItem.movementType}</td>
                                        <td>{movementHistoryItem.postalItem ?
                                            movementHistoryItem.postalItem.id : "N/A"}</td>
                                    </tr>
                                )) : <></>
                            }
                        </table>
                    </>
                )) : <></>
            }
        </div>
    );
}