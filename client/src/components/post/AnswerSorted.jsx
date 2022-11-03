import React, { useState, useRef } from 'react';
import styled from 'styled-components';

const AnswerSortedComponent = styled.div`
  display: flex;
  justify-content: space-between;
  padding-left: 2rem;
  margin-top: 1rem;
  .answers-length {
    font-size: 1.25rem;
    line-height: 1rem;
  }
  .answers-sorted {
    display: flex;
    align-items: center;

    > div {
      display: flex;
      align-items: flex-end;
      flex-direction: column;
      span {
        padding: 0.1rem 0;
      }
    }
  }
  .reset {
    cursor: pointer;
    color: #3378c7;
  }
  select {
    padding: 0.5rem;
    background-color: #fff;
    box-shadow: 0;
    border: 1px solid #d9d9d9;
    border-radius: 5px;
    margin-left: 0.3rem;
  }
  .disabled {
    display: none;
  }
`;

function AnswerSorted({ answers }) {
  const descArr = [
    'Highest score(default)',
    'Trending (recent votes count more)',
    'Date modified (newest first)',
    'Date created (oldest first)',
  ];
  const selectRef = useRef();
  const [desc, setDesc] = useState(descArr[0]);
  const selectHandler = (value) => setDesc(value);
  const resetHandler = () => {
    // sorted select를 처음 상태로 돌림
    setDesc(descArr[0]);
    selectRef.current.value = descArr[0];
  };

  return (
    <AnswerSortedComponent>
      <div className="answers-length">{`${answers} Answer`}</div>
      <div className="answers-sorted">
        <div>
          <span>Sorted by:</span>
          {desc !== descArr[0] && (
            <span className="reset" onClick={resetHandler}>
              Reset to default
            </span>
          )}
        </div>
        <select onChange={(e) => selectHandler(e.target.value)} ref={selectRef}>
          {descArr.map((v, i) => (
            <option value={v} key={i}>
              {v}
            </option>
          ))}
        </select>
      </div>
    </AnswerSortedComponent>
  );
}

export default AnswerSorted;
