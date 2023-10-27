import React, { useState, useEffect } from 'react'
import axios from 'axios'
import './PostalItemsActions.css'

const PostalItemsActions = () => {
    const [postalItems, setPostalItems] = useState([{}])
    const [mailDepartments, setMailDepartmens] = useState([{}])

    const baseURL = "http://localhost:8080"

    function transferPostalItem(e) {
        e.preventDefault();   

        const postalItemId = e.target[0].value;
        const mailDepartmentId = e.target[1].value;
        
        let customConfig = {
            headers: {
                'Content-Type': 'application/json'
            }
        };
        
        axios.post(baseURL + '/mail/transferPostalItemToMailDepartment?postalItemId=' + postalItemId 
            + '&mailDepartmentId=' + mailDepartmentId)
        .then(function (response) {
            console.log(response)
        })
        .catch (function (error) {
            console.log(error);
        });
        
    }



    useEffect(() => {
        axios.get(baseURL + '/mail/getPostalItems').then((response) => {
            setPostalItems(Array.from(response.data));
            console.log(response.data);
        }).catch((e) => {
            console.log(e)
        })
    }, []);

    useEffect(() => {
        axios.get(baseURL + "/getMailDepartments").then((response) => {
            setMailDepartmens(Array.from(response.data));
            console.log(baseURL);
        }).catch((e)=>{
            console.log(baseURL)
        })
    },[]);

    return (
        <div className='postal-items-actions'>
            <h2>Перенаправление отправлений</h2>
            <form name="PostalItemsTransfer"  action='#' onSubmit={(e) =>{transferPostalItem(e)}}>
                <div className='transfer-container'>
                    <div className='transfer-select-item'>
                        <select name = "PostalItemSelect">
                            {postalItems.length > 0 && (
                                postalItems.map(postalItem => (
                                    <option key={postalItem.id}>{postalItem.id}</option>
                                ))
                            )}
                        </select>
                        <label for ="PostalItemSelect">Что</label>
                    </div>
                    <div className='transfer-select-item'>
                        <select>    
                            {mailDepartments.length > 0 && (
                                mailDepartments.map(
                                    mailDepartment => (<option key={mailDepartment.id}>{mailDepartment.id}</option>
                                    )
                                )
                            )}
                        </select>
                        <label>Куда</label>
                    </div>
                                    
                </div>
                <button className='action-button'>Подтвердить</button>

            </form>
        </div>
    );
}

export default PostalItemsActions;