import { useState } from 'react'
import { useEffect } from 'react'
import axios from 'axios';
import './LoginPage.css'

export default function LoginPage() {
    const [isLoginning, setLoginning] = useState(true);

    const BASEURL = 'http://localhost:8080/api/v1';

    function registry() {
        setLoginning(!isLoginning);
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
        <div className='login-page'>
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
                    {
                        !isLoginning ? (        
                            <div className='input-box'>
                                <label for='password'>Confirm password</label>
                                <input name='password' type='password'></input>
                            </div>
                            
                        ) : <></>
                    }
                    <button className='login-page-button'>{isLoginning ? "Войти" : "Зарегистрироваться"}</button>
                    {
                        isLoginning ? (
                            <div>
                                <p>Впервые у нас?</p>
                                <a onClick={registry}>Зарегистрироваться</a>
                            </div>
                        ) : 
                        <div>
                            <p>Есть аккаунт?</p>
                            <a onClick={registry}>Войти</a>
                        </div>
                    }
                
                </form>
            </div>
        </div>
    )
}