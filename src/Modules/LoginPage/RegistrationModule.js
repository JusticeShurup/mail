import '../../Pages/LoginPage.css'


export default function RegistrationModule() {

    const BASEURL = 'http://localhost:8080/api/v1/auth';

    return (
        <div className='login-form'>
            <h2>Добро пожаловать!</h2>
            <form onSubmit>
                <div className='input-box'>
                    <label for='login'>Login</label>
                    <input id='login' name='login' type='text'></input>
                </div>
                <div className='input-box'>
                    <label for='password'>Password</label>
                    <input name='password' type='password'></input>
                </div>  
                <div className='input-box'>
                    <label for='password'>Confirm password</label>
                    <input name='password' type='password'></input>
                </div>
                <button className='login-page-button'>Зарегистрироваться</button>

            </form>
        </div>
    )

}