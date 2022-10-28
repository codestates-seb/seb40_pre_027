import React, { useState, useEffect } from 'react';
import styled from 'styled-components';
import Button from '../components/Button';
import Header from '../components/Header';
import Footer from '../components/Footer';
import backgroundsvg from '../img/background.svg';
import Modal from '../components/Modal';
import InputArea from '../components/write/InputArea';
import data from '../components/write/data';

const WritePageComponent = styled.div`
  background: rgba(240, 240, 240, 0.6);
  article {
    width: 1400px;
    margin: 0 auto;
    display: flex;
    .question {
      width: 100%;
      > button {
        :nth-last-child(1) {
          margin: 0 0 2rem 0;
          cursor: ${(props) =>
            props.stepBtn >= 3 ? 'pointer' : 'not-allowed'};
          pointer-events: ${(props) => (props.stepBtn <= 3 ? 'none' : '')};
          background: ${(props) =>
            props.stepBtn <= 3 ? '#348ed37f' : '#0088ef'};
        }
      }
    }
    h1 {
      padding: 3rem 0;
      font-weight: 700;
      font-size: 1.75rem;
      background-image: ${`url(${backgroundsvg})`};
      background-repeat: no-repeat;
      background-position: right;
    }
    .guide {
      width: 1000px;
      border: 1px solid rgba(166, 206, 237);
      background-color: rgba(235, 244, 251);
      border-radius: 5px;
      padding: 2rem;
      > div {
        padding: 0.15rem 0;
      }
      h2 {
        padding: 0 0 1rem 0;
        font-weight: 500;
        font-size: 1.35rem;
      }
      h3 {
        padding: 1rem 0;
        font-weight: 700;
      }
      ul {
        margin-left: 2.5rem;
        li {
          list-style: disc;
          padding: 0.15rem 0;
        }
      }
    }
  }
`;

function WritePage() {
  const initialData = { title: '', introduce: '', expand: '', tags: [] }; // 글쓰기 기본 양식
  const [step, setStep] = useState('Title'); // 현재 글쓰기 step
  const [stepBtn, setStepBtn] = useState(0); // 각 step의 next 버튼을 눌렀을 때 다음 step으로 이동하기 위한
  const [inputData, setInputData] = useState(initialData); // 글쓰기 상태
  const stepHandler = (title) => setStep(title);
  const stepBtnHandler = () => setStepBtn(stepBtn + 1);

  return (
    <WritePageComponent stepBtn={stepBtn}>
      <Modal />
      <header>
        <Header />
      </header>
      <article>
        <div className="question">
          <h1>Ask a public question</h1>
          <div className="guide">
            <h2>Writing a good question</h2>
            <div>
              You're ready to ask a programming-related question and this form
              will help guide you through the process.
            </div>
            <div>
              Looking to ask a non-programming question? See the topics here to
              find a relevant site.
            </div>

            <h3>Steps</h3>
            <ul>
              <li>Symmarize your problem in a one-line title.</li>
              <li>Describe your problem in more detail.</li>
              <li>Describe what you tried and what you expected to happen.</li>
              <li>
                Add "tags" which help surface your question to members of the
                community.
              </li>
              <li>Review your question and post it to the site.</li>
            </ul>
          </div>
          {data.inputData.map((v, i) => (
            <InputArea
              step={step}
              data={v}
              key={i}
              stepBtn={stepBtn}
              idx={i}
              value={inputData}
              stepHandler={stepHandler}
              stepBtnHandler={stepBtnHandler}
              setValue={setInputData}
            />
          ))}

          <Button>Review your question</Button>
        </div>
      </article>

      <Footer />
    </WritePageComponent>
  );
}

export default WritePage;
