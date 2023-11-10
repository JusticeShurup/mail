import { useState, useEffect } from "react";
import { axiosInstance } from "../../api.config";
import './UserMovementHistory.js'
import useAuth from "../../hooks/useAuth";

export default function UserMovementHistory() {
    const [postalItemsMovements, setHistoryMovements] = useState([[{}]]);
    const [loaded, setLoaded] = useState(false);

    const {auth} = useAuth();

    useEffect(() => {
        if (loaded) return;
        axiosInstance.get('/user/getUserPostalItemsMovementHistory', {
            headers:{
                Authorization: `Bearer ${auth.accessToken}`
            }
        }).then((response) => {
            let matrix = [];
            for (var i in response.data) {
                let massiv = Array.from(response.data[i]);
                massiv = massiv.sort((elem1, elem2) => elem1['id'] > elem2['id'] ? 1 : -1)
                matrix.push(massiv);
            }
            setHistoryMovements(matrix);
            setLoaded(true);

        }).catch((e) => {
            console.log(e);
        });
    }, []);


    return (
        <div className="history-movement-info">
            {
                postalItemsMovements.map(postalItemMovementHistory => (
                    <table>
                        <tr>
                            <th>ID истории</th>
                            <th>Тип перемещения</th>
                            <th>ID отправления</th>
                            <th>Название департамента</th>
                        </tr>
                        {
                            postalItemMovementHistory.length > 0 && postalItemMovementHistory[0].mailDepartment && postalItemMovementHistory[0].postalItem && (
                                postalItemMovementHistory.map(movementHistoryItem => (
                                    <tr>
                                        <td key={movementHistoryItem.id}>{movementHistoryItem.id}</td>
                                        <td key={movementHistoryItem.id}>{movementHistoryItem.movementType}</td>
                                        <td key={movementHistoryItem.id}>{movementHistoryItem.postalItem.id}</td>
                                        <td key={movementHistoryItem.id}>{movementHistoryItem.mailDepartment.name}</td>
                                    </tr>
                                )))
                        }
                    </table>
                ))
            }
        </div>
    );
} 