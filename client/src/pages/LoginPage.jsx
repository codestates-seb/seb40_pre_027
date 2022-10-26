import React from "react";
import styled from "styled-components";
import Header from "../components/Header"
import smallLogo from "../img/smallLogo.png"
import { GoogleLoginButton } from "react-social-login-buttons";
import { GithubLoginButton } from "react-social-login-buttons";

function LoginPage() {
  const LoginPageComponent = styled.div`
    .smallLogo{
      width: 50px;
      height: 50px;
      margin-top: 40px;
      margin-bottom: 20px;
    }

    .account{
      margin-top: 20px;
      margin-bottom: 50px;
    }
    .signUp{
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

  `
  
  
  const LoginBox = styled.div`
    .email{
      font-size: 19px;
    }
    .pw{ 
      display: flex;
      flex-direction: row;
      justify-content: space-between;
    }
    .password{
      font-size: 19px;
    }
    .forgotPassword{
      font-size: 14px;
      color: #0074cc;
    }
    .inputBox{
      width: 100%;
      margin-bottom: 20px;
    }
    button{
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
  `
  

return (
    <>
      <Header />
      <LoginPageComponent>
        <img className='smallLogo' alt='stack-small-logo' src={smallLogo}></img>
        <GoogleLoginButton  
          style={{
            width: "288px",
            height: "36px",
            marginTop: "4px", 
            marginBottom: "4px", 
            padding: "10px"
          }}/>
        <GithubLoginButton 
        style={{
          width: "288px",
          height: "36px",
          marginTop: "4px", 
          marginBottom: "4px", 
          padding: "10px"
        }}
        />
        <LoginBox>
          <div className='email'>Email</div>
          <input className='inputBox'></input>
          <div className='pw'>
          <div className='password'>Password</div>
          <div className='forgotPassword'>Forgot Password?</div>
          </div>
          <input className='inputBox'></input>
          <button>Log in</button>
        </LoginBox>
        <div className='account'>
          <span>Don't have an account?</span>
          <span className='signUp'> Sign up</span>
        </div>

      </LoginPageComponent>
      
    </>
  )
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
