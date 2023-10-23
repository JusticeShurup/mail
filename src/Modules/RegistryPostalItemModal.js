import React, { useEffect, useState } from 'react';
import "./RegistryPostalItemModal.css";
import axios from 'axios';




const RegistryPostalItemModal = ({active, setActive}) => {
    const [mailDepartments, setMailDepartmens] = useState([{}])
    
    const baseURL = "http://localhost:8080"
    
    

    
    function registryPostalItem(e) {
        e.preventDefault();
        const data = new FormData(e.target);

        const PostalItem = Object.fromEntries(data.entries());
        PostalItem.taken = false;
        
        let MailDepartment = JSON.parse(PostalItem.mailDepartment);
        
        PostalItem.mailDepartment = MailDepartment;

        console.log(JSON.stringify(PostalItem));

        let customConfig = {
            headers: {
            'Content-Type': 'application/json'
            }
        };
        
        axios.post(baseURL + '/registryPostalItem', JSON.stringify(PostalItem), customConfig)
        .then(function (response) {
            console.log(response)
        })
        .catch (function (error) {
            console.log(error);
        });
        
    }


    useEffect(() => {
        axios.get(baseURL + "/getMailDepartments").then((response) => {
            setMailDepartmens(Array.from(response.data));
            console.log(baseURL);
        }).catch((e)=>{
            console.log(baseURL)
        })
    },[]);

    


    return (
        <div className = {active ? "modal active" : "modal"} onClick={() => setActive(false )}>
            <div className={active ? "modal__content active" : "modal"} onClick={e => e.stopPropagation()}>
                <h1>Введите данные об отправлении</h1>
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
    )
}

export default RegistryPostalItemModal;