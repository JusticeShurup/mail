import { useEffect } from 'react';
import './AdminMailDepartments.css'
import { axiosInstance } from '../../api.config';
import useAuth from '../../hooks/useAuth';
import { useState } from 'react';
import { Link, useLocation } from 'react-router-dom';

export default function AdminMailDepartments() {
    const [mailDepartments, setMailDepartments] = useState([{}]);
    
    const { auth } = useAuth();

    useEffect(() => {
        axiosInstance.get('/admin/getMailDepartments', {
            headers: {
                Authorization: `Bearer ${auth.accessToken}`
            }
        }).then((response) => {
            setMailDepartments(Array.from(response.data));
        }).catch((error) => {
            console.log(error)
        })  
    }, []) 



    return (
        <div className="admin-mail-departments">
            <h1>Департаменты нашей сети</h1>
            <button><Link to ="/admin/registryMailDepartment">Добавить департамент</Link></button>
            <table>
                <tr>
                    <th>ID департамента</th>
                    <th>Адрес</th>
                    <th>Почтовый индекс</th>
                    <th>Название департамента</th>
                </tr>
                {
                    mailDepartments.length > 0 ? mailDepartments.map((mailDepartment, index) => (
                        <tr key={index}>
                            <td>{mailDepartment.id}</td>
                            <td>{mailDepartment.address}</td>
                            <td>{mailDepartment.index}</td>
                            <td>{mailDepartment.name}</td>
                        </tr>
                    )) : <></>
                }
            </table>
        </div>
    )
}