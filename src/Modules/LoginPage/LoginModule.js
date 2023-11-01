import { Link } from 'react-router-dom';
import '../../Pages/LoginPage.css'
import axios from 'axios';

export default function LoginModule(props) {
    
    const BASEURL = 'http://localhost:8080/api/v1/auth';

    function tryAuthorize(e) {
        axios.post(BASEURL + '/authenticate', {
            username: e.target[0].value,
            password: e.target[1].value
        }).then( function(response) {
          console.log(response)
        }).catch((error) => {
          console.log(error);
        })
    }

    function tryAuthorize(e) {
        axios.post(BASEURL + '/auth/authenticate', {
            username: e.target[0].value,
            password: e.target[1].value
        }).then( function(response) {
          console.log(response)
        }).catch((error) => {
          console.log(error);
        })
    }

    return (
        <div className='login-form'>
            <h2>Добро пожаловать!</h2>
            <form onSubmit={(e) => tryAuthorize(e)}>
                <div className='input-box'>
                    <label for='login'>Login</label>
                    <input id='login' name='login' type='text'></input>
                </div>
                <div className='input-box'>
                    <label for='password'>Password</label>
                    <input name='password' type='password'></input>
                </div>
                <button className='login-page-button'>Войти</button>
            
            </form>
            <div className='module-change'> 
                <p> Впервые у нас? </p>
                <button className='module-change-button' formAction={() => props.setLoggining(false)} >Зарегистрироваться</button> 
            </div>
        </div>
    )
} 