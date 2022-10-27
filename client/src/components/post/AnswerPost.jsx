import React, { useState } from 'react';
import styled from 'styled-components';
import { Editor } from '@toast-ui/react-editor';
import Button from '../Button';
import { MdClose } from 'react-icons/md';
import LinkStyle from '../LinkStyle';
import SocialLogin from '../SocialLogin';

const AnswerPostComponent = styled.div`
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
    > span {
      margin-right: 0.3rem;
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

function AnswerPost({ isLogined }) {
  const [guide, setGuide] = useState(false);
  const [guideview, setGuideview] = useState(false);
  const guideHandler = () => {
    if (!guide && !guideview) {
      setGuide(true);
      setGuideview(true);
    }
  };
  const guideCloseHandler = () => setGuideview(false);
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

      <div className="not-logined">
        <span>Sign up or</span>
        <LinkStyle path="/login">log in</LinkStyle>
        <div className="social">
          <SocialLogin social="google">Sign up using Google</SocialLogin>
          <SocialLogin social="stack">
            Sign up using Email and Password
          </SocialLogin>
        </div>
      </div>
      <Button>Post Your Answer</Button>
    </AnswerPostComponent>
  );
}

export default AnswerPost;
