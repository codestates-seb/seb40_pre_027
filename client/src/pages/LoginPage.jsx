import React from 'react';
import styled from 'styled-components';
import Header from '../components/Header';
import smallLogo from '../img/smallLogo.png';
import { FcGoogle } from 'react-icons/fc';
import { FaGithub } from 'react-icons/fa';
{
  /* <SignGoogle>
<FcGoogle size={30} />
&nbsp;&nbsp;Sign up with Google
</SignGoogle>
<SignGithub>
<FaGithub size={30} />
&nbsp;&nbsp;Sign up with Github
</SignGithub> */
}

function LoginPage() {
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
    }
    .signUp {
      color: #0074cc;
    }

    background-color: #f1f2f3;
    height: 100%;
    width: 100%;
    padding: 100px;
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
  `;

  const LoginBox = styled.div`
    .email {
      font-size: 19px;
    }
    .pw {
      display: flex;
      flex-direction: row;
      justify-content: space-between;
    }
    .password {
      font-size: 19px;
    }
    .forgotPassword {
      font-size: 14px;
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
    width: 288px;
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

// import React from "react";
// import styled from "styled-components";
// import Header from "../components/Header"
// import smallLogo from "../img/smallLogo.png"

// function LoginPage() {
//   const LoginPageComponent = styled.div`
//     .smallLogo{
//       width: 50px;
//       height: 50px;
//     }
//     .googleButton{
//       width: 280px;
//       height: 37.8px;
//       margin: 4px 0px;
//       padding: 10px;

//       background-color: #fff;
//       position: relative;
//     display: inline-block;
//     padding: 0.8em;
//     outline: none;
//     font-family: inherit;
//     font-size: var(--fs-body1);
//     font-weight: normal;
//     line-height: var(--lh-sm);
//     text-align: center;
//     text-decoration: none;
//     cursor: pointer;
//     user-select: none;
//     }
//     .githubButton{
//       width: 280px;
//       height: 37.8px;
//       margin: 4px 0px;
//       padding: 10px;

//       background-color: #2f3337;

//     }

//     padding: 40px;
//     height: 100vh;
//     display: flex;
//     flex-direction: column;
//     align-items: center;
//     justify-content: center;

//   `

//   // const GoogleLoginButtonComponent = {

//   // }
// return (
//     <>
//       <Header />
//       <LoginPageComponent>
//         <img className='smallLogo' alt='stack-small-logo' src={smallLogo}></img>
//         <button className='googleButton'>Log in with Google</button>
//         <button className='githubButton'>Log in with GitHub</button>

//       </LoginPageComponent>

//     </>
//   )
// }

// export default LoginPage;
