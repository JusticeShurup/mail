import { useEffect } from 'react';
import './AdminMailDepartments.css'
import { axiosInstance } from '../../api.config';
import useAuth from '../../hooks/useAuth';
import { useState } from 'react';
import { Link } from 'react-router-dom';

export default function AdminOperators() {
    const [operators, setOperators] = useState([{}]);
    
    const { auth } = useAuth();

    useEffect(() => {
        axiosInstance.get("/admin/getOperators", {
            headers: {
                Authorization: `Bearer ${auth.accessToken}`
            }
        }).then((response) => {
            setOperators(Array.from(response.data));
            console.log(response.data);
        }).catch((error) => {
            console.log(error);
        })
    }, [])

    return (
        <div className="admin-operators"> 
            <h1>Операторы нашей сети</h1>
            <button><Link to ="/admin/registryOperator">Добавить оператора</Link></button>
            <table>
                <tr>
                    <th>ID</th>
                    <th>Имя</th>
                    <th>Фамилия</th>
                    <th>Почтовое отделение</th>
                </tr>
                {
                    operators.length > 0 ? operators.map((operator, index) => (
                        <tr key={index}>
                            <td>{operator.id}</td>
                            <td>{operator.firstname}</td>
                            <td>{operator.lastname}</td>
                            <td>{operator.mailDepartment ? operator.mailDepartment.name : "N/A"}</td>
                        </tr>
                    )) : <></>
                }   
            </table>
        </div>
    )
}