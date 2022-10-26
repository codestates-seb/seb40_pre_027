import React, { useState, useRef } from 'react';
import styled from 'styled-components';
import Button from '../Button';
import { Editor } from '@toast-ui/react-editor';

const InputBoxComponent = styled.div`
  width: 1050px;
  margin: 2rem 0;
  padding: 2rem;
  border: 1px solid #d9d9d9;
  border-radius: 5px;
  h3 {
    font-weight: 700;
    margin-bottom: 1rem;
    margin-left: 5px;
  }
  .disc {
    margin: 1rem 0.2rem;
  }
  > input {
    width: 100%;
    margin: 0.5rem 5px;
    padding: 0.5rem;
    border: 1px solid #d9d9d9;
    border-radius: 5px;
  }
  .disabled {
    ~ button {
      opacity: 0.6;
      pointer-events: none;
    }
  }
  > button {
    margin: 1rem 0 0 0;
    display: none;
  }
`;

function InputBox({
  title,
  name,
  disc,
  placeholder,
  editor,
  stepHandler,
  setValue,
  value,
}) {
  const editorRef = useRef();
  const onChange = (changeData) => {
    console.log(value, title);
    setValue({ ...value, title: changeData });
  };
  const editorOnChange = () => {
    console.log(value, title);
    // const newValue = (value[title] = editorRef.current.getInstance().getHTML());
    // setValue(newValue);
  };
  return (
    <InputBoxComponent input={value[title]}>
      <h3>{name}</h3>
      <div className="disc">{disc}</div>
      {editor ? (
        <div
          onClick={() => stepHandler(name)}
          className={value[title].length <= 5 ? 'disabled' : ''}
        >
          <Editor
            initialValue=" "
            previewStyle="vertical"
            height="300px"
            initialEditType="markdown"
            onChange={editorOnChange}
            useCommandShortcut={true}
            className={value[title].length <= 20 ? 'disabled' : ''}
            ref={editorRef}
          />
        </div>
      ) : (
        <input
          type="text"
          placeholder={placeholder}
          className={value[title].length <= 5 ? 'disabled' : ''}
          onClick={() => stepHandler(name)}
          onChange={(e) => onChange(e.target.value)}
        />
      )}
      <Button>Next</Button>
    </InputBoxComponent>
  );
}

export default InputBox;
