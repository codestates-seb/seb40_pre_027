import React from 'react';
import styled from 'styled-components';
import InputBox from './InputBox';
import InputGuide from './InputGuide';

const InputAreaComponent = styled.div`
  .input-area {
    display: flex;
    margin-right: 2rem;
    > button {
      display: none;
    }
    &.step {
      button {
        display: block;
      }
    }
  }
  .question > button {
    margin: 2rem 0;
  }
`;

function InputArea({
  data,
  step,
  stepHandler,
  value,
  setValue,
  stepBtnHandler,
  stepBtn,
  idx,
}) {
  return (
    <InputAreaComponent>
      <div className={step === data.name ? 'step input-area' : 'input-area'}>
        <InputBox
          title={data.title}
          name={data.name}
          disc={data.disc}
          placeholder={data.placeholder}
          editor={data.editor}
          stepHandler={stepHandler}
          stepBtnHandler={stepBtnHandler}
          value={value}
          setValue={setValue}
          stepBtn={stepBtn}
          idx={idx}
          step={step}
        />
        <InputGuide
          title={data.title}
          disabled={step === data.name ? false : true}
        />
      </div>
    </InputAreaComponent>
  );
}

export default InputArea;
