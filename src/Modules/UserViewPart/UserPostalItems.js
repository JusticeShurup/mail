import { useEffect, useState } from "react";
import SwitchButton from "../Tools/SwitchButton"
import "./UserPostalItems.css"
import useAuth from "../../hooks/useAuth";
import { axiosInstance } from "../../api.config";

export default function UserPostalItems() {
    const [postalItemsType, setPostalItemsType] = useState(false)
    const [postalItems, setPostalItems] = useState([{}])
    const { auth } = useAuth()

    const [typeState, setTypeState] = useState(false)

    const handleButtonClick =(typeState) => {
        setTypeState(typeState);
    }

    useEffect(() => {
        axiosInstance.get('/mail/getPostalItems', {
            headers:{
                Authorization: `Bearer ${auth.accessToken}`
            }
        }).then((response) => {
            setPostalItems(Array.from(response.data));
            console.log(response.data);
        }).catch((e)=>{
            console.log(e);
        })
    },[]);

    return (
      <div className="user-postal-items-page">
        <h2>Ваши отправления</h2>
        <div className="postal-items-type">
            <SwitchButton onStateChange ={handleButtonClick}/>
        </div>
        <div className="">
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
                            {/*
                            <button onClick={e => takePostalItem(postalItem)}>
                            {
                                postalItem.taken ? "Забран" : (postalItem.recipientIndex === postalItem.mailDepartment.index ? "Можно забрать" : "В доставке")   
                            } 
                            </button>
                        */}
                        </td>
                        <td key={postalItem.id}>{postalItem.mailDepartment.name}</td>
                        </tr>
                    ))) 
                }
                </table>
        </div>
      </div>  
    )
}