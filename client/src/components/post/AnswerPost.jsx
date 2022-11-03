import React, { useState, useRef } from 'react';
import styled from 'styled-components';
import { Editor } from '@toast-ui/react-editor';
import Button from '../Button';
import { MdClose } from 'react-icons/md';
import LinkStyle from '../LinkStyle';
import SocialLogin from '../SocialLogin';
import { useSelector } from 'react-redux';
import axios from 'axios';
import { useParams, useNavigate } from 'react-router-dom';
const AnswerPostComponent = styled.div`
  padding-left: 2rem;
  h2 {
    padding: 1.75rem 0;
    font-weight: 600;
    font-size: 1.25rem;
  }
  .guide {
    padding: 1rem 1.5rem;
    border: 1px solid rgb(230, 207, 121);
    background: rgb(253, 247, 226);
    margin-top: 1rem;
    border-radius: 5px;
    font-size: 0.8rem;
    > div {
      padding: 0.8rem 0;
      display: flex;
      justify-content: space-between;
      > div {
        :nth-child(2) {
          font-size: 1.25rem;
          font-weight: 700;
          cursor: pointer;
        }
      }
    }
    ul {
      li {
        list-style: disc;
        margin-left: 2rem;
        line-height: 1rem;
        padding: 0.15rem;
      }
    }
  }
  .not-logined {
    margin: 1rem 0 0 0;
    font-size: 1.25rem;
    font-weight: 500;
    .not-logined-status {
      display: flex;
      > span {
        margin-right: 0.3rem;
      }
    }

    .social {
      margin: 1rem 0;
    }
  }
  button {
    :nth-last-child(1) {
      margin: 1.5rem 0;
    }
  }
`;

function AnswerPost({ answersArray, setAnswersArray }) {
  const [guide, setGuide] = useState(false);
  const [answer, setAnswer] = useState('');
  const [guideview, setGuideview] = useState(false);
  const navigate = useNavigate();
  const isLogin = useSelector((state) => state.login.isLogin);
  const editorRef = useRef();
  const { id } = useParams();
  const guideHandler = () => {
    if (!guide && !guideview) {
      setGuide(true);
      setGuideview(true);
    }
  };
  const guideCloseHandler = () => setGuideview(false);
  const onChange = () => {
    //toast ui editor
    setAnswer(editorRef.current.getInstance().getHTML());
  };
  const answerSubmitHandler = async () => {
    try {
      if (isLogin) {
        const access = localStorage.getItem('accessToken');
        await axios.post(
          `/answer/${id}`,
          { answerContent: answer },
          { headers: { access } }
        );
        const newPostData = await axios.get(`/question/${id}`);
        await setAnswersArray(newPostData.data.answers);
        await editorRef.current.getInstance().setHTML(' ');
      } else alert('you need login');
    } catch (err) {
      alert('답변 생성 실패');
      console.log(err);
    }
  };
  return (
    <AnswerPostComponent>
      <h2>Your Answer</h2>
      <div onClick={() => guideHandler()}>
        <Editor
          initialValue=" "
          previewStyle="vertical"
          height="300px"
          initialEditType="markdown"
          useCommandShortcut={true}
          ref={editorRef}
          onChange={onChange}
          autofocus={false}
        />
      </div>

      {guideview && (
        <div className="guide">
          <div>
            <div>Thanks for contributing an answer to Stack Overflow!</div>
            <div onClick={() => guideCloseHandler()}>
              <MdClose />
            </div>
          </div>
          <ul>
            <li>
              Please be sure to answer the question. Provide details and share
              your research!
            </li>
          </ul>
          <div>But avoid …</div>
          <ul>
            <li>
              Asking for help, clarification, or responding to other answers.
            </li>
            <li>
              Making statements based on opinion; back them up with references
              or personal experience.
            </li>
          </ul>
          <div>To learn more, see our tips on writing great answers.</div>
        </div>
      )}

      {isLogin ? (
        <></>
      ) : (
        <div className="not-logined">
          <div className="not-logined-status">
            <span>Sign up or</span>
            <LinkStyle path="/login">log in</LinkStyle>
          </div>
          <div className="social">
            <SocialLogin social="google">Sign up using Google</SocialLogin>
            <SocialLogin social="stack">
              Sign up using Email and Password
            </SocialLogin>
          </div>
        </div>
      )}
      <Button onClick={answerSubmitHandler}>Post Your Answer</Button>
    </AnswerPostComponent>
  );
}

export default AnswerPost;
