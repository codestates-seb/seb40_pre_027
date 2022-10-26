import React from 'react';
import styled from 'styled-components';
import InputBox from './InputBox';
import InputGuide from './InputGuide';

const InputAreaComponent = styled.div``;

function InputArea({ data, step, stepHandler, value, setValue }) {
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
          value={value}
          setValue={setValue}
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
