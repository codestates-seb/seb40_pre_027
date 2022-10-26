import React from 'react';
import styled from 'styled-components';
import { BsPencil } from 'react-icons/bs';
import data from './data';

const InputGuideComponent = styled.aside`
  border: 1px solid #d9d9d9;
  width: 330px;
  height: 100%;
  margin: 2rem 0;
  border-radius: 5px;
  display: ${(props) => (props.disabled ? 'none' : 'block')};

  h2 {
    padding: 1rem;
    border-bottom: 1px solid #d9d9d9d9;
  }
  > div {
    display: flex;
    margin: 16px;
    .icon {
      width: 48px;
      font-size: 3rem;
      display: flex;
      justify-content: center;
      align-items: center;
      margin-right: 1rem;
    }
    .disc {
      font-size: 0.8rem;
      div {
        padding: 0.3rem 0;
        line-height: 1.15rem;
      }
    }
  }
`;

function InputGuide({ title, disabled }) {
  const viewData = data.guideData.filter((v) => v.title === title)[0];
  return (
    <InputGuideComponent disabled={disabled}>
      <h2>{viewData.guideTitle}</h2>
      <div>
        <div className="icon">
          <BsPencil />
        </div>
        <div className="disc">
          {viewData.disc.map((v, i) => (
            <div key={i}>{v}</div>
          ))}
        </div>
      </div>
    </InputGuideComponent>
  );
}

export default InputGuide;
