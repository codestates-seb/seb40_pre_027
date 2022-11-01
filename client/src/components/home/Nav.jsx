import React from 'react';
import styled from 'styled-components';
import { FaGlobeAsia } from 'react-icons/fa';
import LinkStyle from '../LinkStyle';
import { useLocation } from 'react-router-dom';

const NavBar = styled.div`
  font-size: 1rem;
  width: 180px;
  height: 100px;
  margin-top: 2rem;
  color: #5c5959;
  .nav-item {
    display: flex;
    align-items: center;
    height: 35px;
    &:hover {
      color: #000;
    }
  }
  .sub-item {
    margin-left: 2rem;
  }
  .select {
    background-color: #f0e3e3;
    color: white;
    border: 3px solid #f0e3e3;
    border-color: #f0e3e3 #ff6a00 #f0e3e3 #f0e3e3;
  }
  #questions {
    span {
      margin-left: 0.3rem;
    }
  }
`;

function Nav() {
  const { pathname } = useLocation();
  console.log('nav');
  return (
    <NavBar>
      <ul>
        <li className={pathname === '/' ? 'nav-item select' : 'nav-item'}>
          <LinkStyle path="/" css={{ color: '#000' }}>
            Home
          </LinkStyle>
        </li>
        <li className="nav-item">Public</li>
        <li
          className={pathname === '/questions' ? 'nav-item select' : 'nav-item'}
          id="questions"
        >
          <LinkStyle path="/questions" css={{ color: '#000' }}>
            <FaGlobeAsia size={15} />
            <span>Questions</span>
          </LinkStyle>
        </li>
        <li className="nav-item sub-item">Tags</li>
        <li className="nav-item sub-item">Users</li>
        <li className="nav-item sub-item">Companies</li>
      </ul>
    </NavBar>
  );
}

export default Nav;
