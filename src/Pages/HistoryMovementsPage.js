import React, { useEffect, useState } from 'react';
import axios from 'axios'
import './HistoryMovementsPage.css'

const HistoryMovementsPage = () => {
    const [postalItemsMovements, setHistoryMovements] = useState([[{}]]);
    const [loaded, setLoaded] = useState(false);

    const baseURL = "http://localhost:8080"


    useEffect(() => {
        if (loaded) return;
        axios.get(baseURL + '/getPostalItemsMovementHistory').then((response) => {
            let matrix = [];
            for (var i in response.data) {
                let massiv = Array.from(response.data[i]);
                massiv = massiv.sort((elem1, elem2) => elem1['id'] > elem2['id'] ? 1 : -1)
                matrix.push(massiv);
            }
            setHistoryMovements(matrix);
            setLoaded(true);
            console.log(matrix);

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
                            <th>ID департамента</th>
                        </tr>
                        {
                            postalItemMovementHistory.length > 0 && postalItemMovementHistory[0].mailDepartment && postalItemMovementHistory[0].postalItem && (
                                postalItemMovementHistory.map(movementHistoryItem => (
                                    <tr>
                                        <td key={movementHistoryItem.id}>{movementHistoryItem.id}</td>
                                        <td key={movementHistoryItem.id}>{movementHistoryItem.movementType}</td>
                                        <td key={movementHistoryItem.id}>{movementHistoryItem.postalItem.id}</td>
                                        <td key={movementHistoryItem.id}>{movementHistoryItem.mailDepartment.id}</td>
                                    </tr>
                                )))
                        }
                    </table>
                ))
            }
        </div>
    );
};
export default HistoryMovementsPage;