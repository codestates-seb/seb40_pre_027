import React from 'react';
import styled from 'styled-components';
import Button from '../Button';

const Titlemain = styled.main`
  /* width: 1300px; */
  width: 1200px;

  height: 100%;
  margin: 20px auto;
  padding-left: 2rem;
  padding-bottom: 2rem;
  border-bottom: 1px solid #d9d9d9;
`;
const TitleTxt = styled.div`
  display: inline-block;
  width: 100%;
  button {
    font-size: 0.75rem;
    width: 120px;
    height: 36px;
    padding: 0.1rem;
  }
`;
const MainTxt = styled.div`
  display: flex;
  & h1 {
    font-size: 1.5rem;
    line-height: 2.4rem;
  }

  & :nth-child(2) {
    margin-left: 100px;
  }
  width: 100%;
  padding: 1rem 0;
`;

const SubTxt = styled.div`
  display: flex;

  & h3 {
    color: #858585;
    margin-right: 10px;
  }
`;
function Title() {
  return (
    <Titlemain>
      <TitleTxt>
        <MainTxt>
          <h1>
            Is there a way to access internal metastore of Azure HDInsight to
            fire queries on Hive metastore tables?
          </h1>
          <Button>Ask Question</Button>
        </MainTxt>
        <SubTxt>
          <h3>Asked</h3>
          <h4>8 months ago</h4>&nbsp;&nbsp;
          <h3>Modified</h3>
          <h4>today</h4>&nbsp;&nbsp;
          <h3>Viewed</h3>
          <h4>68 times</h4>
        </SubTxt>
      </TitleTxt>
    </Titlemain>
  );
}

export default Title;
