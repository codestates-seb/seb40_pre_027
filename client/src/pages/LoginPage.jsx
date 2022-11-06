import React, { useState } from 'react';
import styled from 'styled-components';
import Header from '../components/Header';
import smallLogo from '../img/smallLogo.png';
import SocialLogin from '../components/SocialLogin';
import LinkStyle from '../components/LinkStyle';
import axios from 'axios';

// 로그인 후 리디렉션을 위한 useNavigate훅 import
import { useNavigate } from 'react-router-dom';

//redux 관련 import
import { useSelector, useDispatch } from 'react-redux';
import { loginActions } from '../store/login';

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
  const api = process.env.REACT_APP_API_URL;
  axios.defaults.withCredentials = true;
  //useDispatch훅 dispatch 변수에 할당, useSelector를 통한 isLogin 상태 할당
  const dispatch = useDispatch();
  const isLogin = useSelector((state) => state.login.isLogin);
  //useNavigate훅 navigate 변수에 할당
  const navigate = useNavigate();

  const [isCorrect, setIsCorrect] = useState({
    emailCorrect: true,
    passwordCorrect: true,
  });

  // email, password 확인
  const [account, setAccount] = useState({
    email: '',
    password: '',
  });

  const onChangeAccount = (e) => {
    setAccount({
      ...account,
      [e.target.name]: e.target.value,
    });
  };

  // 로그인 요청
  async function getLogin() {
    try {
      await axios
        .post(`${api}/user/login`, {
          email: account.email,
          password: account.password,
        })
        .then((res) => {
          //dispatch로 로그인 상태 redux에 저장, 로컬에 토큰 저장
          dispatch(loginActions.login());
          localStorage.clear();
          localStorage.setItem('accessToken', res.headers.access);
          localStorage.setItem('refreshToken', res.headers.refresh);
          localStorage.setItem('memberId', res.data.memberId);
          navigate('/');
        });
    } catch (error) {
      if (error.response.status === 401) {
        alert('이메일 혹은 비밀번호가 일치하지 않습니다.');
        console.log(error);
      } else {
        alert(error.response.status);
        console.log(error);
      }
    }
  }

  // 유효성 검사
  const LoginHandler = (e) => {
    e.preventDefault();
    if (account.email.trim().length === 0 || !account.email.includes('@')) {
      setIsCorrect((prev) => {
        return { ...prev, emailCorrect: false };
      });
      return;
    }
    if (account.password.length < 8) {
      setIsCorrect((prev) => {
        return { ...prev, passwordCorrect: false };
      });
      return;
    } else {
      setIsCorrect({
        emailCorrect: true,
        passwordCorrect: true,
      });
    }
    getLogin();
    setAccount({
      email: '',
      password: '',
    });
  };

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
            <form onSubmit={LoginHandler}>
              <div className="email">Email</div>
              <input
                className="inputBox"
                name="email"
                onChange={onChangeAccount}
                value={account.email}
              />
              {!isCorrect.emailCorrect && (
                <div>올바른 이메일 형식을 입력하세요.</div>
              )}
              <div className="pw">
                <div className="password">Password</div>
                <div className="forgotPassword">Forgot Password?</div>
              </div>
              <input
                type="password"
                className="inputBox"
                name="password"
                onChange={onChangeAccount}
                value={account.password}
              />
              {!isCorrect.passwordCorrect && (
                <div>비밀번호는 8자 이상을 입력하세요.</div>
              )}
              <button onClick={onChangeAccount}>Log in</button>
            </form>
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
