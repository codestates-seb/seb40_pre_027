import React from "react";
import styled from "styled-components";
import Button from "./Button";
import { GoSearch } from "react-icons/go";
import Logo from "../img/Logo.png";

const HeaderComponent = styled.div`
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
`;

const Topbar = styled.div`
  padding: 1.5px;
  background: #f48225;
  margin-bottom: 3px;
`;

function Header() {
  const searchHandler = () => {};
  const loginHandler = () => {};
  const signupHandler = () => {};
  return (
    <>
      <Topbar />
      <HeaderComponent>
        <div className="header-container">
          <a className="logo-anchor" href="url">
            <span className="logo"></span>
          </a>
          <form className="search-form" onSubmit={searchHandler}>
            <div className="search-group">
              <GoSearch className="search-icon" />
              <input
                className="search-input"
                type="text"
                placeholder="Search..."
              />
            </div>
          </form>
          <Button
            data={{
              background: "#e1ecf4",
              color: "#315877",
              hovercolor: "#B3D3EA",
              activecolor: "#B3D3EA",
            }}
            onClick={loginHandler}
          >
            Log in
          </Button>
          <Button onClick={signupHandler}>Sign up</Button>
        </div>
      </HeaderComponent>
    </>
  );
}

export default Header;
