import React from 'react';
import { useState, useEffect } from 'react';
import styled from 'styled-components';
import LinkStyle from '../LinkStyle';
import Header from '../Header';
import Footer from '../Footer';
import Nav from '../home/Nav';
import MyProfile from './MyProfile';
import { Link } from 'react-router-dom';
import QuestionRender from './QuestionRender';
import AnswerRender from './AnswerRender';
import requestNewAccessToken from '../util/requestNewAccessToken';
const ProfilePages = styled.main`
  width: 100%;
  section {
    width: 100%;
    display: flex;
    justify-content: center;
    article {
      display: flex;
      flex-direction: column;
      border-left: 1px solid #d9d9d9;
      .top-menu {
        height: 110px;
      }
    }
  }
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
  width: 1100px;
  height: auto;
  margin-left: 40px;
  margin: 45px;
`;
const LeftContent = styled.div`
  float: left;
  width: 50%;
`;
const LeftBox = styled.article`
  & h2 {
    font-size: 1.8rem;
    font-weight: 400;
    margin-top: 15px;
  }
  & div {
    width: 100%;
    height: auto;
    border: 1px solid #c6c6c6;
    border-radius: 10px;
    margin-top: 15px;
    position: relative;
    & div {
      border: none;
      height: auto;
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
    height: auto;
    border: 1px solid #c6c6c6;
    border-radius: 10px;
    margin-top: 15px;
    position: relative;
    & div {
      border: none;
      height: auto;
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

function Profilewrite() {
  const [write, setWrite] = useState([]);

  useEffect(() => {
    requestNewAccessToken(setWrite, `/user/profile/write`, 'get');
  }, []);
  const questions = write.questions;
  const answers = write.answers;
  console.log(questions);
  return (
    <>
      <ProfilePages>
        <Header />
        <section>
          <Nav />
          <article>
            <MyProfile />
            <ProfileTab>
              <ul>
                <LinkStyle path="/myProfile" css={{ color: '#000' }}>
                  Profile
                </LinkStyle>
                <li>Write</li>
              </ul>
            </ProfileTab>
            <WriteIn>
              <LeftContent>
                <LeftBox>
                  <h2>Questions</h2>
                  <div>
                    <div>
                      {questions &&
                        questions.map((el) => {
                          return (
                            <QuestionRender
                              title={el.title}
                              content={el.content}
                              viewCount={el.viewCount}
                              questionId={el.questionId}
                            ></QuestionRender>
                          );
                        })}

                      <QuestionRender
                        className="writeList"
                        write={write}
                      ></QuestionRender>
                      <h3>
                        Your about me section is currently blank. Would you like
                        to add one?
                      </h3>
                      <Link to="/write" style={{ textDecoration: 'none' }}>
                        <h4>write question</h4>
                      </Link>
                    </div>
                  </div>
                </LeftBox>
              </LeftContent>
              <RightContent>
                <RightBox>
                  <h2>Answer</h2>
                  <div>
                    <div>
                      {answers &&
                        answers.map((el) => {
                          return (
                            <AnswerRender
                              answerContent={el.answerContent}
                              answerCreatedAt={el.answerCreatedAt}
                              answerId={el.answerId}
                              answerLikesCount={el.answerLikesCount}
                            />
                          );
                        })}
                      <AnswerRender
                        className="writeList"
                        WriteRen={write}
                      ></AnswerRender>
                      <h3>
                        Your about me section is currently blank. Would you like
                        to add one?
                      </h3>
                      <Link to="/" style={{ textDecoration: 'none' }}>
                        <h4>write answer</h4>
                      </Link>
                    </div>
                  </div>
                </RightBox>
              </RightContent>
            </WriteIn>
          </article>
        </section>
        <Footer />
      </ProfilePages>
    </>
  );
}

export default Profilewrite;
