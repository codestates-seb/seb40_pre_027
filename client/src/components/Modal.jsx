import React, { useState } from 'react';
import styled from 'styled-components';

import Button from './Button';

const Backdrop = styled.div`
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100vh;
  z-index: 10;
  background: rgba(0, 0, 0, 0.242);
`;

const ModalComponent = styled.div`
  position: fixed;
  top: 20%;
  left: 70vh;
  z-index: 100;
  overflow: hidden;
  background: white;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.26);
  border-radius: 3px;

  & .header {
    padding: 1.5rem;
  }
  & .header h2 {
    margin: 0;
    color: black;
    font-size: 2rem;
    font-weight: bold;
  }

  & .content {
    padding: 1rem;
  }

  p {
    color: #5c6064;
    line-height: 1.25em;
    padding: 0.7em;
  }

  a {
    text-decoration: none;
  }

  & .numbering {
    color: #488dfd;
    font-weight: bold;
    text-shadow: 2px 2px 2px #dae8ff;
  }

  & .actions {
    padding: 1.5rem;
    padding-top: 0.5rem;
    display: flex;
    justify-content: flex-start;
  }
`;

function Modal(props) {
  const [isConfirm, setIsConfirm] = useState(false);

  const confirmHandler = () => {
    setIsConfirm(true);
  };

  return (
    <>
      {!isConfirm && (
        <Backdrop>
            <ModalComponent>
              <header className="header">
                <h2>Asking a good question</h2>
              </header>
              <div className="content">
                <p>
                  You're ready to ask your first programming-related<br></br>
                  question and the community is here to help! To get you
                  <br></br>
                  the best answers, we've provided some guidance:
                </p>
                <p>
                  Before you post, <a href="/questions">search the site</a> to
                  make sure your<br></br>question hasn't been answered
                </p>
                <p>
                  <span className="numbering">1.</span> Summarize the problem
                </p>
                <p>
                  <span className="numbering">2.</span> Describe what you've
                  tried
                </p>
                <p>
                  <span className="numbering">3.</span> When appropriate, show
                  some code
                </p>
              </div>
              <footer className="actions">
                <Button onClick={confirmHandler}>Start writing</Button>
              </footer>
            </ModalComponent>
        </Backdrop>
      )}
    </>
  );
}

export default Modal;
