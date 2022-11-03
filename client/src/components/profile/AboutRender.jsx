import React from 'react';
import styled from 'styled-components';

const Prof = styled.article`
  width: 100%;
  height: 100%;
  display: flex;
  position: relative;
  & input {
    margin: 50px;
    width: 30%;
    height: 15%;
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
    top: 20%;
    left: 35px;
  }
  .title {
    margin: 15px;
    position: absolute;
    top: 40%;
    left: 35px;
  }
  .introduction {
    margin: 15px;
    position: absolute;
    top: 60%;
    left: 35px;
  }
  .intro-input {
    width: 60%;
    height: 35%;
  }
`;

function AboutRender() {
  return (
    <Prof>
      <div className="display-name">Display-name</div>
      <input type="text" />
      <div className="location">Location</div>
      <input type="text" />
      <div className="title">Title</div>
      <input type="text" />
      <div className="introduction">Introduction</div>
      <input type="text" className="intro-input" />
    </Prof>
  );
}

export default AboutRender;
