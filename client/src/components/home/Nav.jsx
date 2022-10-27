import React from 'react';
import styled from 'styled-components';
import { FaGlobeAsia } from 'react-icons/fa';
import LinkStyle from '../LinkStyle';

const NavBar = styled.div`
  width: 200px;
  height: 50vh;
  margin-top: 2rem;
`;

const HomeButton = styled.div`
  width: 100%;
  height: 50px;
  font-size: 1rem;
  color: black;
  line-height: 2.9rem;
  cursor: pointer;
  &:hover {
    background-color: gray;
    color: white;
    border: 3px solid gray;
    border-color: gray #ff6a00 gray gray;
    transition: 0.4s;
  }
`;

const Public = styled.div`
  display: flex;
  width: 100%;
  height: 50px;
  font-size: 1rem;
  color: black;
  line-height: 3rem;
  cursor: pointer;
  &:hover {
    background-color: gray;
    color: white;
    border: 3px solid gray;
    border-color: gray #ff6a00 gray gray;
    transition: 0.4s;
  }
`;

const PublicContent = styled.ul`
  display: block;
  color: black;
  font-size: 0.85rem;
`;

const Questions = styled.li`
  cursor: pointer;
  margin: 10px 0px 10px 0;
  letter-spacing: 0.6px;
  height: 4.3vh;
  line-height: 2.5rem;
  &:hover {
    background-color: gray;
    color: white;
    border: 3px solid gray;
    border-color: gray #ff6a00 gray gray;
    transition: 0.3s;
  }
`;

const Tags = styled.li`
  cursor: pointer;
  margin: 10px 0 10px 39px;
  letter-spacing: 0.6px;
  height: 4.3vh;
  line-height: 2rem;
  &:hover {
    background-color: gray;
    color: white;
    border: 3px solid gray;
    border-color: gray #ff6a00 gray gray;
    transition: 0.4s;
  }
`;

const Users = styled.li`
  cursor: pointer;
  margin: 10px 0 10px 39px;
  letter-spacing: 0.6px;
  height: 4.3vh;
  line-height: 2rem;
  &:hover {
    background-color: gray;
    color: white;
    border: 3px solid gray;
    border-color: gray #ff6a00 gray gray;
    transition: 0.4s;
  }
`;

const Companies = styled.li`
  cursor: pointer;
  margin: 10px 0 10px 39px;
  letter-spacing: 0.6px;
  height: 4.3vh;
  line-height: 2rem;
  &:hover {
    background-color: gray;
    color: white;
    border: 3px solid gray;
    border-color: gray #ff6a00 gray gray;
    transition: 0.4s;
  }
`;

function Nav() {
  return (
    <NavBar>
      <LinkStyle path="/">
        <HomeButton>Home</HomeButton>
      </LinkStyle>

      <Public>Public</Public>
      <PublicContent>
        <Questions>
          <LinkStyle path="/questions" css={{ color: '#000', hover: true }}>
            <FaGlobeAsia size={30} />
            &nbsp;Questions
          </LinkStyle>
        </Questions>
        <Tags>Tags</Tags>
        <Users>Users</Users>
        <Companies>Companies</Companies>
      </PublicContent>
    </NavBar>
  );
}

export default Nav;
