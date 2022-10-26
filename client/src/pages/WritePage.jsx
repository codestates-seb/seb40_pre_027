import React, { useState } from 'react';
import styled from 'styled-components';
import Button from '../components/Button';
import Header from '../components/Header';
import InputBox from '../components/write/InputBox';
import Footer from '../components/Footer';
import InputGuide from '../components/write/InputGuide';
import backgroundsvg from '../img/background.svg';
import Modal from '../components/Modal';

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
    .input-area {
      display: flex;
      justify-content: space-between;
      > button {
        display: none;
      }
      &.step {
        button {
          display: block;
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
    .question > button {
      margin: 2rem 0;
    }
  }
`;

function WritePage() {
  const [step, setStep] = useState('Title');
  const stepHandler = (title) => setStep(title);

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
          <div className={step === 'Title' ? 'step input-area' : 'input-area'}>
            <InputBox
              title={'Title'}
              disc={`Be specific and imagine you're asking a question to another
              person.`}
              placeholder={
                'e.g Is there an R function for finding the index of an element in a vector?'
              }
              stepHandler={stepHandler}
            />
            <InputGuide
              title={'title'}
              disabled={step === 'Title' ? false : true}
            />
          </div>

          <div
            className={
              step === 'What are the details of your problem?'
                ? 'step input-area'
                : 'input-area'
            }
          >
            <InputBox
              title={'What are the details of your problem?'}
              disc={`Describe what you tried, what you expected to happen, and what
            actually resulted. Minimum 20 characters.`}
              editor={true}
              stepHandler={stepHandler}
            />
            <InputGuide
              title={'introduce'}
              disabled={
                step === 'What are the details of your problem?' ? false : true
              }
            />
          </div>
          <div
            className={
              step === 'What did you try and what were you expecting?'
                ? 'step input-area'
                : 'input-area'
            }
          >
            <InputBox
              title={'What did you try and what were you expecting?'}
              disc={`Introduce the prolem and expand on what you put in the title.
            Minimum 20 characters.`}
              editor={true}
              stepHandler={stepHandler}
            />
            <InputGuide
              title="expand"
              disabled={
                step === 'What did you try and what were you expecting?'
                  ? false
                  : true
              }
            />
          </div>
          <div className={step === 'Tags' ? 'step input-area' : 'input-area'}>
            <InputBox
              title={'Tags'}
              disc={`Add up to 5tags to describe what your question is about. Start
            typing to see suggestions.`}
              placeholder={
                'e.g Is there an R function for finding the index of an element in a vector?'
              }
              stepHandler={stepHandler}
            />
            <InputGuide
              title="tags"
              disabled={step === 'Tags' ? false : true}
            />
          </div>
          <Button>Review your question</Button>
        </div>
      </article>

      <Footer />
    </WritePageComponent>
  );
}

export default WritePage;
