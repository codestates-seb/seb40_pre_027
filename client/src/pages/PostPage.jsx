import React, { useEffect, useState } from 'react';
import styled from 'styled-components';
import AnswerPost from '../components/post/AnswerPost';
import AnswerSorted from '../components/post/AnswerSorted';
import Header from '../components/Header';
import Nav from '../components/home/Nav';
import Footer from '../components/Footer';
import PostBody from '../components/post/PostBody';
import Title from '../components/post/Title';
import axios from 'axios';
import { useParams } from 'react-router-dom';

const PostPageComponent = styled.div`
  width: 100vw;
  .section {
    width: 100%;
    display: flex;
    justify-content: center;

    article {
      display: flex;
      flex-direction: column;
      border-left: 1px solid #d9d9d9;
      /* padding-left: 2rem; */
      .top-menu {
        height: 110px;
      }
    }
    .post-aside {
      display: flex;
    }
    aside {
      width: 245px;
      /* border: 1px solid red; */
    }
  }
`;

function PostPage() {
  const [post, setPost] = useState([]);
  const [answersArray, setAnswersArray] = useState([]);
  const [commentsArray, setCommentsArray] = useState([]);
  const { id } = useParams();
  useEffect(() => {
    axios.get(`/question/${id}`).then((res) => {
      setPost(res.data);
      setAnswersArray(res.data.answers);
      setCommentsArray(res.data.comments);
    });
  }, []);
  return (
    <PostPageComponent>
      <Header />
      {Object.keys(post).length ? (
        <div className="section">
          <Nav />
          <article>
            <Title post={post} />
            <div className="post-aside">
              <div>
                <PostBody
                  answer={false}
                  post={post}
                  setCommentsArray={setCommentsArray}
                  commentsArray={commentsArray}
                />
                <AnswerSorted answers={answersArray.length} />
                {answersArray.length ? (
                  answersArray
                    .sort((a, b) => b.answerLikesCount - a.answerLikesCount)
                    .map((answer, i) => (
                      <PostBody
                        post={post}
                        answer={answer}
                        idx={i}
                        key={answer.answerId}
                        setAnswersArray={setAnswersArray}
                        answersArray={answersArray}
                      />
                    ))
                ) : (
                  <></>
                )}

                <AnswerPost
                  setAnswersArray={setAnswersArray}
                  answersArray={answersArray}
                />
              </div>
              <aside></aside>
            </div>
          </article>
        </div>
      ) : (
        <div>...</div>
      )}

      <Footer />
    </PostPageComponent>
  );
}

export default PostPage;
