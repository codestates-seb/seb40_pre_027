import React from 'react';
import styled from 'styled-components';

const Prof = styled.article`
  width: 100%;
  height: 100%;
  display: flex;
  position: relative;
  .box {
    margin: 50px;
    width: 40%;
    height: 60px;
    border-radius: 0%;
    border: 1px solid black;

    & h2 {
      font-size: 1.2rem;
      font-weight: 300;
      margin: 22px;
    }
  }
  & :nth-child(2) {
    width: 40%;
    min-height: 60px;
    border: 1px solid black;
  }
  .name-input {
    width: 40%;
    height: 30px;
    border: 1px solid red;
  }
  .display-name {
    margin: 15px;
    position: absolute;
    top: 5px;
    left: 35px;
  }
  .location {
    margin: 15px;
    position: absolute;
    top: 21%;
    left: 35px;
  }
  .title {
    margin: 15px;
    position: absolute;
    top: 43%;
    left: 35px;
  }
  .introduction {
    margin: 15px;
    position: absolute;
    top: 66%;
    left: 35px;
  }
  .intro-input {
    width: 70%;
    height: 140px;
    border: 1px solid black;
    margin: 50px;
  }
`;

function AboutRender({ profileRen }) {
  const { name, location, title, introduction } = profileRen;
  return (
    <Prof>
      <div className="display-name">Display-name</div>
      <div className="box">
        <h2>{name}</h2>
      </div>
      <div className="location">Location</div>
      <div className="box">
        <h2>{location}</h2>
      </div>
      <div className="title">Title</div>
      <div className="box">{title}</div>
      <div className="introduction">Introduction</div>
      <div className="intro-input">{introduction}</div>
    </Prof>
  );
}

export default AboutRender;
