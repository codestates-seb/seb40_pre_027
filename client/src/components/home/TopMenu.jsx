import React from "react";
import styled from "styled-components";
import Button from "../Button";

const TopMenuComponent = styled.div`
    border-left: 1px solid #d9d9d9;
    width: 100%;
    h1 {
      font-size: 27px;
    }
    .askButton {
      margin-bottom: 12px;
      padding: 10.4px;
    }
    font-size: 13px;
    font-weight: bold;
  `;
  const H1Component = styled.div`
    display: flex;
    flex-direction: row;
    justify-content: space-between;
    margin-bottom: 12px;
    padding: 10.4px;
  `;

  const ButtonListsComponent = styled.div`
    display: flex;
    flex-direction: row-reverse;
    > button {
      width: 80px;
      height: 30px;
      font-size: 0.7rem;
      border: 0.8px solid black;
      background-color: white;
    }
    > :nth-child(5) {
      background-color: #e3e6e8;
      color: #a1a1a1;
    }
    > :nth-child(4) {
      width: 120px;
      height: 30px;
      display: flex;
      justify-content: center;
      padding-top: 4px;
      line-height: 1.4rem;
      > div {
        width: 30px;
        height: 20px;
        background-color: #1e98fc;
        color: white;
      }
    }
    > :nth-child(3) {
      width: 60px;
      color: #3e3e3e;
    }
    > :nth-child(2) {
      width: 70px;
      color: #3e3e3e;
    }
    > :nth-child(1) {
      width: 70px;
      color: #3e3e3e;
    }
  `;
  
function TopMenu() {
  
  return (
    <TopMenuComponent>
      <H1Component>
        <h1>Top Questions</h1>
        <Button>Ask Question</Button>
      </H1Component>
      <ButtonListsComponent>
        <button>Month</button>
        <button>Week</button>
        <button>Hot</button>
        <button>
          <div>297</div>
          &nbsp;bountied
        </button>
        <button>interesting</button>
      </ButtonListsComponent>
    </TopMenuComponent>
  );
}

export default TopMenu;
