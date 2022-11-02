import React, { useState } from 'react';
import styled from 'styled-components';
import Button from '../Button';
import { Link, useLocation } from 'react-router-dom';

const TopMenuComponent = styled.div`
  h1 {
    font-size: 27px;
    margin-top: 20px;
  }
  .askButton {
    margin-bottom: 12px;
    padding: 10.4px;
  }
  width: 926px;
  height: 100%;
  font-size: 13px;
  font-weight: bold;
`;
const TopMenuBoxComponent = styled.div`
  margin-bottom: 12px;
  padding: 10.4px;

  display: flex;
  flex-direction: row;
  justify-content: space-between;
`;

const ButtonListsComponent = styled.div`
  > div {
    width: 80px;
    height: 30px;
    font-size: 0.8rem;
    border: 0.8px solid black;
    background-color: #fff;
    cursor: pointer;
    display: flex;
    justify-content: center;
    align-items: center;
  }
  .select {
    background: #b6b4b4;
  }
  margin-bottom: 10px;
  display: flex;
  justify-content: flex-end;
`;

function TopMenu() {
  const [sorted, setSorted] = useState('interesting');
  const sortedArr = ['interesting', 'Hot', 'Week', 'Month'];
  const sortBtnHandler = (t) => setSorted(t);
  return (
    <TopMenuComponent>
      <TopMenuBoxComponent>
        <h1>{'All Questions'}</h1>
        <Link to="/write">
          <Button>Ask Question</Button>
        </Link>
      </TopMenuBoxComponent>

      <ButtonListsComponent sort={sorted}>
        {sortedArr.map((v) => (
          <div
            className={sorted === v ? 'select' : ''}
            onClick={() => sortBtnHandler(v)}
          >
            {v}
          </div>
        ))}
      </ButtonListsComponent>
      {/* <ButtonListsComponent>
        <button>Month</button>
        <button>Week</button>
        <button>Hot</button>
        <button>
          <div>297</div>
          &nbsp;bountied
        </button>
        <button>interesting</button>
      </ButtonListsComponent> */}
    </TopMenuComponent>
  );
}

export default TopMenu;
