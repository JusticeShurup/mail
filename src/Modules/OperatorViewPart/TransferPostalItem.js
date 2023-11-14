import { useState } from 'react';
import './TransferPostalItem.css'
import { useEffect } from 'react';
import { axiosInstance } from '../../api.config';
import useAuth from '../../hooks/useAuth';

export default function TransferPostalItem({ postalItem, removePostalItem }) {
    const [mailDepartments, setMailDepartments] = useState([{}])

    const { auth } = useAuth();

    useEffect(() => {
        axiosInstance.get("/operator/getAnotherMailDepartments", {
            headers: {
                Authorization: `Bearer ${auth.accessToken}`
            }
        })
            .then((response) => {
                setMailDepartments(response.data);
            })

    }, [])

    function transferPostalItem(e) {
        e.preventDefault();
        
        let mailDepartmentId = e.target[0].value;
        var data = new FormData();
        data.append("postalItemId", postalItem.id);
        data.append("transferMailDepartmentId", mailDepartmentId);
        
        axiosInstance.post("/operator/transferPostalItem", data, {
            headers: {
                Authorization: `Bearer ${auth.accessToken}`
            }
        })
        .then((response) => {
            console.log(response);
        })
        .catch((error) => {
            console.log(error);
        })
    }

    return (
        <div className="transfer-postal-item-page">
            <h1>Перенаправление отправлений</h1>
            {
                postalItem ?
                    (
                        <>
                            <h2>Выберите в какой департамент хотите перенаправить</h2>
                            <div className='mail-department-selector'>
                                <form action='#' onSubmit={(e) => transferPostalItem(e)}>
                                    <select>
                                        {mailDepartments.length > 0 && (
                                            mailDepartments.map(
                                                mailDepartment => (<option key={mailDepartment.id} value={mailDepartment.id}>{mailDepartment.name}</option>
                                                )
                                                )
                                                )}
                                    </select>
                                    <button type='submit'>Отправить</button>
                                </form>
                            </div>
                        </>
                    )
                    : <></>
            }
        </div>
    );
}