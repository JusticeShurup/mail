import { useState } from 'react';
import './UserRegistryPostalItem.css'
import useAuth from '../../hooks/useAuth';
import { useEffect } from 'react';
import { axiosInstance } from '../../api.config';



export default function UserRegistryPostalItem() {
    const [mailDepartments, setMailDepartments] = useState([{}])
    const { auth } = useAuth();

    function registryPostalItem(e) {
        e.preventDefault();
        const data = new FormData(e.target);

        const PostalItem = Object.fromEntries(data.entries());
        PostalItem.senderName = auth.user;
        PostalItem.taken = false;

        
        let MailDepartment = JSON.parse(PostalItem.mailDepartment);
        
        PostalItem.mailDepartment = MailDepartment;

        console.log(JSON.stringify(PostalItem));

        let customConfig = {
            headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${auth.accessToken}`
            }
        };
        
        axiosInstance.post('/user/registryPostalItem', JSON.stringify(PostalItem), customConfig)
        .then(function (response) {
            console.log(response)
        })
        .catch (function (error) {
            console.log(error);
        });
        
    }

    useEffect(() => {
        let customConfig = {
            headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${auth.accessToken}`
            }
        };
        axiosInstance.get("/mail/getMailDepartments", customConfig).then((response) => {
            setMailDepartments(Array.from(response.data));
        }).catch((e)=>{
        })
    },[]);

    return (
        <div className="user-registry-postal-item-page">
            <h2>Регистрация отправления</h2>
            <div className="registry-form">
                <form action='#' onSubmit={(e) =>{registryPostalItem(e)} }>
                    <div className="select-item">
                        <select name="postalType" id="postalTypes">
                            <option>LETTER</option>
                            <option>PARCEL</option>
                            <option>PARCELPOST</option>
                            <option>POSTCARD</option>
                        </select>
                        <label for="postalTypes">Выберите тип посылки</label>
                    </div>
                    <div className="select-item">
                        <select name="recipientIndex" id="departments">
                            {
                                mailDepartments.map((mailDepartment) => {
                                    return <option value={mailDepartment.index}>{mailDepartment.index}</option>
                                })
                            }
                        </select>
                        <label for="departments">Выберите индекс получателя</label>
                    </div>
                    <div className="input-group">
                        <input name="recipientAddress" id="address_collumn" type="text"/>
                        <label for="address_collumn">Введите адрес получателя</label>
                    </div>
                    <div className="input-group">
                        <input name="recipientName" id="name_collumn" type="text"/>
                        <label for="name_collumn">Введите имя получателя</label>
                    </div>
                    <div className="select-item">
                        <select name="mailDepartment" id="departments">
                            {
                                mailDepartments.map((mailDepartment) => {
                                    return <option value={JSON.stringify(mailDepartment)}>{mailDepartment.name}</option>
                                })
                            }
                        </select>
                        <label for="departments">Выберите отделение</label>
                    </div>

                    <button type='submit'>Зарегистрировать посылку</button>
                </form>
            </div>
        </div>
    );
}