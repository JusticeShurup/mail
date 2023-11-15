import { axiosInstance } from "../../api.config";
import useAuth from "../../hooks/useAuth"



export default function RegistryDepartment() {

    const { auth } = useAuth();

    function AddDepartment(e) {
        e.preventDefault();

        const data = new FormData(e.target);

        const mailDepartment = Object.fromEntries(data.entries());

        console.log(JSON.stringify(mailDepartment));
        
        axiosInstance.post("/admin/registerMailDepartment", JSON.stringify(mailDepartment) ,{
            headers:{
                Authorization: `Bearer ${auth.accessToken}`
            }
        }).then((response) => {
            
        })

    }


    return (
        <div className="registry-department">
            <h1>Введите данные департамента</h1>
            <form onSubmit={(e) => AddDepartment(e)}>
                <input name="address" placeholder="Адрес"></input>
                <input name="index" placeholder="Индекс" minLength={6} maxLength={6}></input>
                <input name="name" placeholder="Название"></input>
                <button type="Submit">Подтвердить</button>
            </form>
        </div>
    )
}