/* eslint-disable jsx-a11y/anchor-is-valid */
import React from 'react'
import { useState } from 'react';
import './login.css'
import axios from 'axios';
import { SERVER_PATH } from '../utils/environment';

const CONTEXT_PATH = 'user';

const Login = () => {
  const [loginCredentials, setLoginCredentials] = useState({
    email: '',
    password: ''
  });

  const [isFormComplete, setIsFormComplete] = useState(false);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setLoginCredentials({
      ...loginCredentials,
      [name]: value,
    });

    const { email, password } = loginCredentials;
    setIsFormComplete(email !== '' && password !== '');
    console.log(isFormComplete)
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    axios
      .post(SERVER_PATH + CONTEXT_PATH + `/login`, loginCredentials)
      .then((response) => {
          console.log(response);
      })
      .catch((error) => {
          console.log(error);
      });
  }; 

  return (
    <div className="login-form">
      <form onSubmit={handleSubmit}>
        <h1>Login</h1>
        <div className="content">
          <div className="input-field">
            <input type="email" name="email" value={loginCredentials.email} placeholder='Email' onChange={handleChange} autoComplete="nope"/>
          </div>
          <div className="input-field"> 
            <input type="password" name='password' value={loginCredentials.password} placeholder="Password" onChange={handleChange} autoComplete="new-password"/>
          </div>
          <a href="#" className="link">Forgot Your Password?</a>
        </div>
        <div className="action">
          <button className='sign-in-button' type='submit' disabled={!isFormComplete}>Sign in</button>
        </div>
      </form>
    </div>
  )
}

export default Login