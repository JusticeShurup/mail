import { useState } from 'react'
import { useEffect } from 'react'
import axios from 'axios';
import './LoginPage.css'
import LoginModule from '../Modules/LoginPage/LoginModule';
import RegistrationModule from '../Modules/LoginPage/RegistrationModule';
import { axiosInstance } from '../api.config';

export default function LoginPage() {
    const [isLoginning, setLoginning] = useState(true);

    function tryAuthorize(e) {
        axiosInstance.post('auth/authenticate', {
            username: e.target[0].value,
            password: e.target[1].value
        }).then( function(response) {
          console.log(response)
        }).catch((error) => {
          console.log(error);
        })
    }

    function tryRegister(e) {
        axiosInstance.post('auth/register', {
            firstname: e.target[0].value,
            lastname: e.target[1].value,
            username: e.target[2].value,
            password: e.target[3].value
        }).then( function(response) {
          console.log(response);
        }).catch((error) => {
          console.log(error);
        })
    }


    return (
        <div className='login-page'>
            {
            isLoginning ? (
                <div className='login-form'>
                    <h2>Добро пожаловать!</h2>
                    <form action='#' onSubmit={(e) => tryAuthorize(e)}>
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
                        <button className='module-change-button' onClick={() => setLoginning(false)}>Зарегистрироваться</button> 
                    </div>
                </div>
            ) : (
                <div className='login-form'>
                    <h2>Добро пожаловать!</h2>
                    <form action='#' onSubmit= {(e) => tryRegister(e)}>
                        <div className='input-box'>
                            <label for='firstname'>Имя</label>
                            <input id='firstname' name='firstname' type='text'></input>
                        </div>

                        <div className='input-box'>
                            <label for='lastname'>Фамилия</label>
                            <input id='lastname' name='lastname' type='text'></input>
                        </div>

                        <div className='input-box'>
                            <label for='login'>Login</label>
                            <input id='login' name='login' type='text'></input>
                        </div>
                        <div className='input-box'>
                            <label for='password'>Password</label>
                            <input name='password' type='password'></input>
                        </div>  
                        <div className='input-box'>
                            <label for='confrim-password'>Confirm password</label>
                            <input name='confrim-password' type='password'></input>
                        </div>
                        <button className='login-page-button'>Зарегистрироваться</button>
                        
                    </form>
                    <div className='module-change'> 
                        <p> Уже есть аккаунт? </p>
                        <button className='module-change-button' onClick={() => setLoginning(true)}>Войти</button> 
                    </div>
                </div>
            )
        }
        </div>
    )
}