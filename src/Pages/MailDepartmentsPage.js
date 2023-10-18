import MailDepartmentCard from '../Modules/MailDepartmentsPage/MailDepartmentCard';
import React, { useEffect, useState } from 'react';

const MailDepartmentsPage = () => {
    const [mailDepartments, setMailDepartmens] = useState([])

    const baseURL = "http://localhost:8080/getMailDepartments"

    const fetchMailDepartmentsData = () =>{
        fetch(baseURL)
            .then(response => {
                return response.json();
            })
            .then(data =>{
                setMailDepartmens(data)
            })
    }

    useEffect(() => {
        fetchMailDepartmentsData()
    }, []) 

    console.log(mailDepartments);
    return (
        <div className="departments-page">
            {mailDepartments.length > 0 && (
                <ul>
                {mailDepartments.map(mailDepartment => (
                    <li key={mailDepartment.id}>{mailDepartment.name}</li>
                ))}
                </ul>
            )}
        </div>
    );
};
export default MailDepartmentsPage;