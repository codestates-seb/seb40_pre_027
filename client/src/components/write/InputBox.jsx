import React, { useState, useRef, useEffect } from 'react';
import styled from 'styled-components';
import Button from '../Button';
import { Editor } from '@toast-ui/react-editor';
import data from './data';
import tags from '../tags';
import Tag from '../Tag';

const InputBoxComponent = styled.div`
  width: 1000px;
  margin: 2rem 0;
  padding: 2rem;
  border: 1px solid #d9d9d9;
  background: #fff;
  border-radius: 5px;
  margin-right: 2rem;
  position: relative;

  .wall {
    position: absolute;
    top: 0;
    left: 0;
    width: 1000px;
    height: 100%;
    background: rgba(240, 240, 240, 0.5);
    z-index: 1;
    cursor: not-allowed;
  }
  h3 {
    font-weight: 700;
    margin-bottom: 1rem;
    margin-left: 5px;
  }
  .disc {
    margin: 1rem 0.2rem;
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
  form {
    width: 100%;
  }
  .input-title-tag {
    border: 1px solid #d9d9d9;
    position: relative;

    padding: 0.5rem;
    width: 100%;
    display: flex;
    input {
      width: 100%;
      border: none;
    }
    .tag-list {
      display: flex;
    }
    .select-tag {
      border: 2px solid #4683c0;
      border-radius: 5px;
      background: #fff;
      position: absolute;
      top: 40px;
      left: 0;
      padding: 0.5rem;
      z-index: 2;
    }
  }
`;

function InputBox({
  title,
  name,
  disc,
  placeholder,
  editor,
  step,
  stepHandler,
  setValue,
  value,
  stepBtnHandler,
  stepBtn,
  idx,
  postData,
}) {
  const [tag, setTag] = useState(''); // tag를 작성하기 위한
  const [tagList, setTagList] = useState([]); // tags 데이터에서 tag와 일치하는 list
  const editorRef = useRef(); // toast ui editor selector
  const onChange = (e, editor) => {
    // 글쓰기 각 해당 step에서 글을 작성헀을 때 WritePage의 inputData를 변경
    if (editor) {
      //toast ui editor
      const newValue = {
        ...value,
        [title]: editorRef.current.getInstance().getHTML(),
      };
      setValue(newValue);
    } else if (title === 'tags') {
      // title이 'tags'일 때 taglist에 일치한 데이터를 저장
      setTag(e.target.value);
      const newTagList = tags.filter((v) => v.includes(e.target.value));
      setTagList(newTagList);
    } else {
      // inputData에 각 타이틀과 일치한 속성에 값을 대입
      const newValue = { ...value, [title]: e.target.value };
      setValue(newValue);
    }
  };

  const inputTitleChange = (name) => {
    // 각 step의 input창을 클릭 했을 때 해당 step으로 이동하는 함수
    if (stepBtn >= idx) stepHandler(name);
  };

  const tagDeleteHandler = (data) => {
    // 나열된 value.tags값에서 tag값을 클릭할 경우 inputData의 tags 속성의 배열에 data값을 제거
    const newValue = { ...value };
    newValue.tags = newValue.tags.filter((tag) => tag !== data);
    setValue(newValue);
  };

  const tagAddHandler = (data) => {
    // 나열된 taglist의 값에서 tag를 클릭할 경우 inputData의 tags 속성의 배열에 값을 추가

    const newValue = { ...value };
    newValue.tags.push(data);
    setValue(newValue);
    setTag('');
    setTagList([]);
  };

  const nxtBtnHandler = () => {
    // 각 글쓰기 step의 next 버튼 event
    stepBtnHandler();
    if (step === data.inputData[0].name) {
      stepHandler(data.inputData[1].name);
    } else if (step === data.inputData[1].name) {
      stepHandler(data.inputData[2].name);
    } else if (step === data.inputData[2].name) {
      stepHandler(data.inputData[3].name);
    }
  };
  useEffect(() => {
    if (postData && Object.keys(postData).length) {
      if (idx === 1) {
        editorRef.current?.getInstance().setHTML(postData.introduce);
      } else if (idx === 2) {
        editorRef.current?.getInstance().setHTML(postData.expand);
      }
    }
  }, []);
  const tagInputSubmit = (e) => {
    e.preventDefault();
    if (title === 'tags') {
      const newValue = { ...value };
      newValue.tags.push(tag);
      setValue(newValue);
      setTag('');
    }
  };
  return (
    <InputBoxComponent input={value[title]}>
      <div className={stepBtn >= idx ? '' : 'wall'}></div>
      <h3>{name}</h3>
      <div className="disc">{disc}</div>
      {value && editor ? (
        <div
          onClick={() => inputTitleChange(name)}
          className={value[title].length <= 20 ? 'disabled' : ''}
        >
          <Editor
            initialValue=" "
            height="300px"
            initialEditType="markdown"
            onChange={(e) => onChange(e, true)}
            useCommandShortcut={true}
            ref={editorRef}
            autofocus={false}
          />
        </div>
      ) : (
        <div
          className={
            title === 'title'
              ? value.title.length <= 5
                ? 'disabled input-title-tag'
                : 'input-title-tag'
              : value.tags !== null && value.tags.length <= 0
              ? 'disabled input-title-tag'
              : 'input-title-tag'
          }
        >
          {name === 'Tags' && (
            <div className="tag-list">
              {value.tags !== null &&
                value.tags.map((tag, i) => (
                  <Tag key={i} onDelete={() => tagDeleteHandler(tag)}>
                    {tag}
                  </Tag>
                ))}
            </div>
          )}
          <form onSubmit={tagInputSubmit}>
            <input
              type="text"
              placeholder={placeholder}
              className={value.title.length <= 5 ? 'disabled' : ''}
              onClick={() => inputTitleChange(name)}
              onChange={(e) => onChange(e)}
              value={name === 'Tags' ? tag : value.title}
            />
          </form>
          {name === 'Tags' && tagList.length ? (
            <div className="select-tag">
              {tagList.map((tag, i) => (
                <div key={i} onClick={() => tagAddHandler(tag)}>
                  {tag}
                </div>
              ))}
            </div>
          ) : (
            ''
          )}
        </div>
      )}

      <Button onClick={nxtBtnHandler}>Next</Button>
    </InputBoxComponent>
  );
}

export default InputBox;
