import MailDepartmentCard from '../Modules/MailDepartmentsPage/MailDepartmentCard';
import React, { useEffect, useState } from 'react';
import axios from 'axios'

const MailDepartmentsPage = () => {
    const [mailDepartments, setMailDepartmens] = useState([{}])

    const baseURL = "http://localhost:8080/getMailDepartments"

    React.useEffect(() => {
        axios.get(baseURL).then((response) => {
            setMailDepartmens(Array.from(response.data));
            console.log(response.data);
        }).catch((e)=>{
            console.log(e)
        })
    },[]);

    return (
        <div className="departments-page">
            {mailDepartments.length > 0 && (
                <ul>
                {mailDepartments.map(mailDepartment => (
                    <li key={mailDepartment.id}>{mailDepartment.name} {mailDepartment.index} {mailDepartment.address}</li>
                ))}
                </ul>
            )}
        </div>
    );
};
export default MailDepartmentsPage;