import React, { useState } from 'react';
import styled from 'styled-components';
import Button from '../components/Button';
import Header from '../components/Header';
import Footer from '../components/Footer';
import backgroundsvg from '../img/background.svg';
import Modal from '../components/Modal';
import InputArea from '../components/write/InputArea';
import data from '../components/write/data';

const WritePageComponent = styled.div`
  header {
    height: 50px;
    border-bottom: 1px solid #d9d9d9;
  }
  article {
    width: 1400px;
    margin: 0 auto;
    display: flex;
    .question {
      width: 100%;
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
      width: 1050px;
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
  const initialData = { title: '', introduce: '', expand: '', tags: [] };
  const [step, setStep] = useState('Title');
  const [inputData, setInputData] = useState(initialData);
  const stepHandler = (title) => {
    console.log(title);
    setStep(title);
  };
  return (
    <WritePageComponent>
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
              stepHandler={stepHandler}
              value={inputData}
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
