import React, {useState, useEffect} from 'react'
import axios from 'axios'
import './PostalItemsInfo.css'

const PostalItemsInfo = () => {
    const [postalItems, setPostalItems] = useState([{}])

    const baseURL = "http://localhost:8080"

    

    useEffect(() => {
        axios.get(baseURL + '/mail/getPostalItems').then((response) => {
            setPostalItems(Array.from(response.data));
            console.log(response.data);
        }).catch((e)=>{
            console.log(e);
        })
    },[]);

    function takePostalItem(postalItem) {
        postalItem.taken = (postalItem.taken === false && postalItem.recipientIndex === postalItem.mailDepartment.index)
        axios.post(baseURL + '/mail/takePostalItemById?postalItemId=' + postalItem.id).then((response) => {
            console.log(response.data);
        }).catch((e)=>{
            console.log(e);
        })
        
    }

    return (
        <div className='postal-items-info'>
            <table>
                <tr>
                    <th>ID</th>
                    <th>Тип посылки</th>
                    <th>Адрес получателя</th>
                    <th>Индекс получателя</th>
                    <th>Имя получателя</th>
                    <th>Статус</th>
                    <th>Текущий департамент</th>
                </tr>
                {postalItems.length > 0 && postalItems[0].mailDepartment && (
                    
                    postalItems.map(postalItem => (    
                        <tr>
                        <td key={postalItem.id}>{postalItem.id}</td>
                        <td key={postalItem.id}>{postalItem.postalType}</td>
                        <td key={postalItem.id}>{postalItem.recipientIndex}</td>
                        <td key={postalItem.id}>{postalItem.recipientAddress}</td>
                        <td key={postalItem.id}>{postalItem.recipientName}</td>
                        <td key={postalItem.id}>
                            <button onClick={e => takePostalItem(postalItem)}>
                            {
                                postalItem.taken ? "Забран" : (postalItem.recipientIndex === postalItem.mailDepartment.index ? "Можно забрать" : "В доставке")   
                            } 
                            </button>
                        
                        </td>
                        <td key={postalItem.id}>{postalItem.mailDepartment.name}</td>
                        </tr>
                    ))) 
                }
                
            </table>
        </div>
    );
}

export default PostalItemsInfo;