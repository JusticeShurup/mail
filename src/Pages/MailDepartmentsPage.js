import MailDepartmentCard from '../Modules/MailDepartmentsPage/MailDepartmentCard';
import React, { useEffect, useState } from 'react';
import { axiosInstance } from '../api.config';

import axios from 'axios'

const MailDepartmentsPage = () => {
    const [mailDepartments, setMailDepartmens] = useState([{}])

    React.useEffect(() => {
        axiosInstance.get('/mail/getMailDepartments').then((response) => {
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