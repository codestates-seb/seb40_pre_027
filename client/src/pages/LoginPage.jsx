import React, { useState, useEffect } from 'react';
import styled from 'styled-components';
import Header from '../components/Header';
import smallLogo from '../img/smallLogo.png';
import SocialLogin from '../components/SocialLogin';
import LinkStyle from '../components/LinkStyle';
import axios from 'axios';


const LoginPageComponent = styled.div`
  height: 100vh;
  overflow: hidden;
  .smallLogo {
    width: 50px;
    height: 50px;
    margin-top: 40px;
    margin-bottom: 20px;
  }

  .account {
    margin-top: 20px;
    margin-bottom: 50px;
    font-size: 13px;
  }
  .signUp {
    color: #0074cc;
  }

  .login {
    width: 100%;
    height: 100%;

    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    /* margin-top: 50px; */
  }
  background-color: #f1f2f3;
`;

const LoginBox = styled.div`
  .email {
    font-size: 15px;
    font-weight: bold;
  }
  .pw {
    display: flex;
    flex-direction: row;
    justify-content: space-between;
  }
  .password {
    font-size: 15px;
    font-weight: bold;
  }
  .forgotPassword {
    font-size: 12px;
    color: #0074cc;
  }
  .inputBox {
    width: 100%;
    margin-bottom: 20px;
  }
  button {
    width: 100%;
    height: 37.8px;
    background-color: #0a95ff;
    color: #ffffff;
    margin: 2px 0px;
    padding: 10.4px;
    border-radius: 5px;
    border: 0;
    outline: 0;
    cursor: pointer;
  }

  background-color: #ffffff;
  width: 310px;
  margin: 24px;
  padding: 24px;
  border-radius: 5%;
`;

function LoginPage() {  
  useEffect(() => {
    axios.get('user/login')
    .then((res) =>
    console.log(res.data));
  }, []);

  // email, password 확인
  const [account, setAccount] = useState({
    email: "",
    password: "",
  });


  const onChangeAccount = (e) => {
  setAccount({
    ...account,
    [e.target.name]: e.target.value
  })
  
  console.log(account)
  }

  

  return (
    
    <>
      <LoginPageComponent>
        <Header />
        <div className="login">
          <LinkStyle path="/">
            <img
              className="smallLogo"
              alt="stack-small-logo"
              src={smallLogo}
            ></img>
          </LinkStyle>
          <SocialLogin social="google">Log in with Google</SocialLogin>
          <SocialLogin social="github">Log in with Github</SocialLogin>
          <LoginBox>
            <div className="email">Email</div>
            <input 
              className="inputBox" 
              name="email" 
              onChange={onChangeAccount}
              >
            </input>
            <div className="pw">
              <div className="password">Password</div>
              <div className="forgotPassword">Forgot Password?</div>
            </div>
            <input 
              className="inputBox" 
              name="password" 
              onChange={onChangeAccount}
              >
              </input>
            <button onClick={onChangeAccount} >Log in</button>
          </LoginBox>
          <div className="account">
            <span>Don't have an account?</span>
            <LinkStyle path="/sign">
              <span className="signUp"> Sign up</span>
            </LinkStyle>
          </div>
        </div>
      </LoginPageComponent>
    </>
  );
}

export default LoginPage;

