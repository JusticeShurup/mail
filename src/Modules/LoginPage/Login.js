import './LoginPage.css'
import axios from 'axios';
import { AuthProvider, AuthContext } from '../../context/AuthProvider';
import useAuth from '../../hooks/useAuth';
import { Link, useNavigate, useLocation } from 'react-router-dom';

import { useRef, useState, useEffect } from 'react';

const USER_REGEX = /^[A-z][A-z0-9-_]{3,23}$/;
const PWD_REGEX = /^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%]).{8,24}$/;


export default function Login(props) {
    const { setAuth } = useAuth();

    const navigate = useNavigate();
    const location = useLocation();
    const from = location.state?.from?.pathname || "/";

    const userRef = useRef();
    const errRef = useRef();

    const [user, setUser] = useState('');
    const [pwd, setPwd] = useState('');
    const [errMsg, setErrMsg] = useState('');

    const BASEURL = 'http://localhost:8080/api/v1/auth';
    
    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            const response = await axios.post(BASEURL + '/authenticate',
                {
                    username: user,
                    password: pwd
                }
            );
            console.log(JSON.stringify(response?.data));
            //console.log(JSON.stringify(response));
            const accessToken = response?.data?.accessToken;
            const roles = response?.data?.roles;
            setAuth({ user, pwd, roles, accessToken });
            setUser('');
            setPwd('');
            navigate(from, {replace: true});
        } catch (err) {
            if (!err?.response) {
                setErrMsg('No Server Response');
            } else if (err.response?.status === 400) {
                setErrMsg('Missing Username or Password');
            } else if (err.response?.status === 401) {
                setErrMsg('Unauthorized');
            } else {
                setErrMsg('Login Failed');
            }
            
        }
    }
    

    return (
        <div className='login-form'>
            <h2>Добро пожаловать!</h2>
            <form onSubmit={(e) => handleSubmit(e)}>
                <div className='input-box'>
                    <label htmlFor='login'>Login</label>
                    <input 
                    type="text"
                    id="username"
                    ref={userRef}
                    autoComplete="off"
                    onChange={(e) => setUser(e.target.value)}
                    value={user}
                    required
                    ></input>
                </div>
                <div className='input-box'>
                    <label htmlFor='password'>Password</label>
                    <input 
                    type="password"
                    id="password"
                    onChange={(e) => setPwd(e.target.value)}
                    value={pwd}
                    required
                    ></input>
                </div>
                <button className='login-page-button'>Войти</button>
            
            </form>
            <div className='module-change'> 
                <p> Впервые у нас? </p>
                <Link to="/registration">Зарегистрироваться</Link> 
            </div>
        </div>
    )
} 