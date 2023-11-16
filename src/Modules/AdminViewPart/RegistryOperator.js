import { useEffect } from "react";
import { axiosInstance } from "../../api.config";
import useAuth from "../../hooks/useAuth"
import { useState } from "react";

export default function RegistryOperator() {
    const [mailDepartments, setMailDepartments] = useState([{}])
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

    function AddOperator(e) {
        e.preventDefault();

        const data = new FormData(e.target);
        
        const operator = Object.fromEntries(data.entries()); 
        operator.mailDepartmentId = parseInt(e.target[4].value);

        axiosInstance.post("/admin/registerOperator", JSON.stringify(operator) ,{
            headers:{
                Authorization: `Bearer ${auth.accessToken}`,
                "Content-Type": "application/json"  
            }
        }).then((response) => {
            console.log(response);
        }).catch((error) => {
            console.log(error);
        })
        


    }

    return (
        <div className="registry-operator">
            <h1>Введите данные оператора</h1>
            <form onSubmit={(e) => AddOperator(e)}>
                <input name="firstname" placeholder="Имя" type="text"></input>
                <input name="lastname" placeholder="Фамилия" type="text"></input>
                <input name="username" placeholder="Username" type="text"></input>
                <input name="password" placeholder="Пароль" type="password"></input>
                <select>
                    {mailDepartments.length > 0 && (
                        mailDepartments.map(
                            mailDepartment => (<option name="mailDepatmentId" key={mailDepartment.id} value={mailDepartment.id}>{mailDepartment.name}</option>
                            )
                            )
                            )}
                </select>
                <button type="Submit">Подтвердить</button>
            </form>
        </div>
    )
}