import React from 'react';
import styled from 'styled-components';
import LinkStyle from '../LinkStyle';

const ProfilePages = styled.main`
  width: 1200px;
  margin: 0 auto;
`;
const ProfileTab = styled.div`
  margin: 50px;
  & ul {
    width: 150px;
    display: flex;
    justify-content: space-between;

    .submenu {
      border: 1px solid #f28234;
    }
  }
`;
const WriteIn = styled.div`
  width: 1000px;
  height: auto;
  border: 1px solid #c6c6c6;
  margin: 50px;
`;
const LeftContent = styled.div`
  float: left;
  width: 50%;

  & h2 {
    font-size: 1.8rem;
    font-weight: 400;
    margin-top: 15px;
  }
  & div {
    width: 100%;
    height: 65vh;
    border: 1px solid #c6c6c6;
    border-radius: 10px;
    margin-top: 15px;
    position: relative;

    & div {
      border: 1px solid white;
      height: 15vh;
      position: absolute;
      top: 40%;
    }
    & h4 {
      font-size: 0.9rem;
      color: #6096ff;
      text-align: center;
      line-height: 3rem;
      cursor: pointer;
    }
    & h3 {
      font-size: 0.9rem;
      text-align: center;
    }
  }
`;
const RightContent = styled.div`
  float: right;
  width: 45%;
`;
const RightBox = styled.div`
  & h2 {
    font-size: 1.8rem;
    font-weight: 400;
    margin-top: 15px;
  }
  & div {
    width: 100%;
    height: 29.5vh;
    border: 1px solid #c6c6c6;
    border-radius: 10px;
    margin-top: 15px;
    position: relative;
    & span {
      border: 1px solid white;
      height: 15vh;
      position: absolute;
      top: 40%;
      left: 3%;
    }
    & h4 {
      font-size: 0.9rem;
      color: #6096ff;
      text-align: center;
      line-height: 3rem;
      cursor: pointer;
    }
    & h3 {
      font-size: 0.9rem;
      text-align: center;
    }
  }
`;

function profilewrite() {
  return (
    <>
      <ProfilePages>
        <ProfileTab>
          <ul>
            <LinkStyle path="/Profile" css={{ color: '#000' }}>
              Profile
            </LinkStyle>
            <li>Write</li>
          </ul>
        </ProfileTab>
        <WriteIn>
          <LeftContent>
            <h2>View Comments</h2>
            <div>
              <div>
                <h3>
                  Your about me section is currently blank. Would you like to
                  add one?
                </h3>
                <h4>Edit profile</h4>
              </div>
            </div>
          </LeftContent>
          <RightContent>
            <RightBox>
              <h2>Questions</h2>
              <div>
                <span>
                  <h3>
                    Your about me section is currently blank. Would you like to
                    add one?
                  </h3>
                  <h4>write question</h4>
                </span>
              </div>
            </RightBox>
            <RightBox>
              <h2>Answer</h2>
              <div>
                <span>
                  <h3>
                    Your about me section is currently blank. Would you like to
                    add one?
                  </h3>
                  <h4>write answer</h4>
                </span>
              </div>
            </RightBox>
          </RightContent>
        </WriteIn>
      </ProfilePages>
    </>
  );
}

export default profilewrite;
