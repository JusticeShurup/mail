import { useState } from 'react'
import { useEffect } from 'react'
import './LoginPage.css'

export default function LoginPage() {
    const [isLoginning, setLoginning] = useState(true);

    return (
        <div className='login-page'>
            <div className='login-form'>
                <form>
                    <input name='login' type='text'></input>
                    <input name='password' type='password'></input>
                    {
                        !isLoginning ? (
                        <>
                                <input name='confirm-password' type='password'></input>
                        </>
                        ) : <></>
                    }
                </form>
            </div>
        </div>
    )
}