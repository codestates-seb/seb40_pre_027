import React from 'react';
import styled from 'styled-components';
import Header from '../components/Header';
import smallLogo from '../img/smallLogo.png';
import { FcGoogle } from 'react-icons/fc';
import { FaGithub } from 'react-icons/fa';

const LoginPageComponent = styled.div`
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

background-color: #f1f2f3;
height: 100vh;
width: 100%;
margin-top: -50px;
display: flex;
flex-direction: column;
align-items: center;
justify-content: center;
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

const SignGoogle = styled.div`
width: 310px;
height: 38px;
margin: 15px;
border-radius: 5px;
text-align: center;
line-height: 2.3rem;
border: 1px solid white;
background-color: white;
font-size: 0.9rem;
cursor: pointer;
`;
const SignGithub = styled.div`
width: 310px;
height: 38px;
margin-bottom: 25px;
border-radius: 5px;
text-align: center;
line-height: 2.3rem;
border: 1px solid #2f3337;
background-color: #2f3337;
color: white;
font-size: 0.9rem;
cursor: pointer;
`;

function LoginPage() {

  return (
    <>
      <Header />
      <LoginPageComponent>
        <img className="smallLogo" alt="stack-small-logo" src={smallLogo}></img>
        <SignGoogle>
          <FcGoogle size={30} />
          &nbsp;&nbsp;Sign up with Google
        </SignGoogle>
        <SignGithub>
          <FaGithub size={30} />
          &nbsp;&nbsp;Sign up with Github
        </SignGithub>
        <LoginBox>
          <div className="email">Email</div>
          <input className="inputBox"></input>
          <div className="pw">
            <div className="password">Password</div>
            <div className="forgotPassword">Forgot Password?</div>
          </div>
          <input className="inputBox"></input>
          <button>Log in</button>
        </LoginBox>
        <div className="account">
          <span>Don't have an account?</span>
          <span className="signUp"> Sign up</span>
        </div>
      </LoginPageComponent>
    </>
  );
}

export default LoginPage;
