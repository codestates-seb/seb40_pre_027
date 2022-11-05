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
  const descArr = [
    'Highest score(default)',
    'Date modified (newest first)',
    'Date created (oldest first)',
  ];
  const [desc, setDesc] = useState(descArr[0]);
  const arraySortHandler = (a, b) => {
    // 답변 정렬 함수
    if (desc) {
      if (desc === 'Highest score(default)') {
        return b.answerLikesCount - a.answerLikesCount;
      } else if (desc === 'Date modified (newest first)') {
        const dateA = new Date(a.answerCreatedAt);
        const dateB = new Date(b.answerCreatedAt);
        return dateB - dateA;
      } else if (desc === 'Date created (oldest first)') {
        const dateA = new Date(a.answerModifiedAt);
        const dateB = new Date(b.answerModifiedAt);
        return dateA - dateB;
      }
    }
  };
  useEffect(() => {
    axios.get(`/question/${id}`).then((res) => {
      setPost(res.data);
      setAnswersArray(res.data.answers.sort(arraySortHandler));
      setCommentsArray(res.data.comments);
    });
  }, []);
  useEffect(() => {
    setAnswersArray([...answersArray].sort(arraySortHandler));
  }, [desc]);

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
                <AnswerSorted
                  answers={answersArray.length}
                  desc={desc}
                  setDesc={setDesc}
                  descArr={descArr}
                />
                {answersArray.length ? (
                  answersArray.map((answer, i) => (
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
