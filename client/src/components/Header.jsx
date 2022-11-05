import React, { useState } from 'react';
import styled from 'styled-components';
import Button from './Button';
import { GoSearch } from 'react-icons/go';
import Logo from '../img/Logo.png';
import { Link, useNavigate } from 'react-router-dom';
import UserCard from './UserCard';

//redux 관련 import
import { useSelector, useDispatch } from 'react-redux';
import { loginActions } from '../store/login';
import { searchActions } from '../store/search';
import { tagSearchActions } from '../store/tagSearch';

const HeaderComponent = styled.header`
  border-bottom: 2px solid #d9d9d9;
  box-shadow: 2px 1px #d9d9d9;
  height: 47px;
  background: rgb(248, 249, 249);
  > div {
    display: flex;
    align-items: center;
    box-sizing: border-box;
    justify-content: space-around;
    margin: 0 auto;
    width: 1400px;

    & .header-container {
      width: 100%;
      height: 100%;
      display: flex;
      margin: 0 auto;
      align-items: center;
    }

    & .logo-anchor {
      padding: 0 8px;
      height: 100%;
      display: flex;
      align-items: center;
      background-color: transparent;
      cursor: pointer;
    }

    & .logo {
      background-image: url(${Logo});
      background-size: 100% 100%;
      width: 150px;
      height: 30px;
    }

    & .search-form {
      padding: 0 8px;
      display: flex;
      align-items: center;
      flex-shrink: 10000;
      flex-grow: 1;
    }

    & .search-group {
      position: relative;
      flex-grow: 1;
    }

    & .search-input {
      display: block;
      margin: 0;
      padding: 5px;
      padding-left: 32px;
      width: 100%;
    }

    & .search-icon {
      right: auto;
      left: 0.7em;
      position: absolute;
      top: 50%;
      margin-top: -6px;
    }
  }
`;

const Topbar = styled.div`
  padding: 1.5px;
  background: #f48225;
  margin-bottom: 3px;
`;

function Header() {
  const navigate = useNavigate();
  const [inputValue, setInputValue] = useState('');

  //dispatch 변수 할당, isLogin 상태 할당
  const dispatch = useDispatch();
  const isLogin = useSelector((state) => state.login.isLogin);

  //logoutHandler
  const logoutHandler = () => {
    //dispatch로 로그아웃 상태 redux에 저장
    dispatch(loginActions.logout());
    localStorage.removeItem('accessToken');
    localStorage.removeItem('refreshToken');
    localStorage.removeItem('memberId');
  };

  //searchInput에서 현재 값 받아오기
  const searchInputHandler = (e) => {
    setInputValue(e.target.value);
  };

  //검색 핸들러
  const searchHandler = (e) => {
    e.preventDefault();
    //redux에 searchInput 값 저장
    dispatch(searchActions.searchPost(inputValue));
    navigate('/');
    setInputValue('');
  };

  const logoClick = () => {
    setInputValue('');
    dispatch(searchActions.searchPost(inputValue));
    dispatch(tagSearchActions.searchPost(''));
    navigate('/');
  };

  return (
    <>
      <Topbar />
      <HeaderComponent>
        <div>
          <div className="header-container">
            <div className="logo-anchor" onClick={logoClick}>
              <span className="logo"></span>
            </div>

            <form className="search-form" onSubmit={searchHandler}>
              <div className="search-group">
                <GoSearch className="search-icon" />
                <input
                  className="search-input"
                  type="text"
                  placeholder="Search..."
                  onChange={searchInputHandler}
                  value={inputValue}
                />
              </div>
            </form>
            {isLogin ? (
              <>
                <Link to="/myProfile">
                  <UserCard />
                </Link>
                <Button onClick={logoutHandler}>Log out</Button>
              </>
            ) : (
              <>
                <Link to="/login">
                  <Button
                    data={{
                      background: '#e1ecf4',
                      color: '#315877',
                      hovercolor: '#B3D3EA',
                      activecolor: '#B3D3EA',
                    }}
                  >
                    Log in
                  </Button>
                </Link>

                <Link to="/sign">
                  <Button>Sign up</Button>
                </Link>
              </>
            )}
          </div>
        </div>
      </HeaderComponent>
    </>
  );
}

export default Header;
