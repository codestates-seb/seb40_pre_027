import React, { useState, useEffect } from 'react';
import styled from 'styled-components';
import Button from '../Button';
import Tag from '../Tag';
import tagsData from '../tags';

const WatchedBox = styled.div`
  width: 300px;
  /* height: 120px; */
  border: 1px solid #f0f0f0;
  margin: 15px;
  box-shadow: 2px 3px 15px 5px #d8d8d8;
`;
const WatchedTxt = styled.div`
  width: 100%;
  height: 60px;
  font-size: 1rem;
  background-color: #f0f0f0;
  line-height: 3.8rem;
  /* padding-left: 10px; */
  display: flex;
  justify-content: space-between;
  padding: 0 0.35rem;
  .edit {
    cursor: pointer;
    color: #73717193;
  }
`;
const WatchBut = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  padding: 1rem 0;
  width: 100%;
  box-sizing: border-box;
  flex-wrap: wrap;
  .tag-search {
    display: flex;
    padding: 0 1rem;

    input {
      width: 100%;
    }
  }
  .select-tag {
    /* padding-left: 1rem; */
    margin: 0.3rem 5rem 0 1rem;
    border: 1px solid black;
  }
  .watch-tag-btn {
    width: 130px;
    height: 40px;
    border: 1px solid #81a6c7;
    background-color: #e2ecf4;
    color: #45719d;
  }
  .tags {
    display: flex;
    flex-wrap: wrap;
    padding: 0 0 0.5rem 1rem;
  }
`;

const Boxs = styled.div``;

function TagsBox({ setTags, tags, name }) {
  const [isEdit, setIsEdit] = useState(false);
  const [tag, setTag] = useState('');
  const [tagList, setTagList] = useState([]); // tags 데이터에서 tag와 일치하는 list
  useEffect(() => {
    const localTagsData = JSON.parse(localStorage.getItem(name));
    if (localTagsData) setTags(localTagsData);
  }, []);
  const onChange = (e) => {
    // tag관련 onChange
    setTag(e.target.value);
    const newTagList = tagsData.filter((v) => v.includes(e.target.value));
    setTagList(newTagList);
  };
  const autoComplete = (tag) => {
    // 추천 리스트에서 값을 누르면 자동완성
    setTag(tag);
    setTagList([]);
  };
  const tagAddHandler = (tag) => {
    // 나열된 taglist의 값에서 tag를 클릭할 경우 inputData의 tags 속성의 배열에 값을 추가
    const newTags = [...tags];
    if (!tags.includes(tag)) newTags.push(tag);
    setTags(newTags);
    localStorage.setItem(name, JSON.stringify(newTags));
    setTag('');
  };

  const tagDeleteHandler = (tag) => {
    // 태그 컴포넌트의 x를 누르면 해당 태그가 list에서 삭제
    const newtags = tags.filter((v) => tag !== v);
    setTags(newtags);
    localStorage.setItem(name, JSON.stringify(newtags));
  };
  const editHandler = () => {
    // edit 보여짐/ 숨김
    setIsEdit(!isEdit);
  };

  return (
    <Boxs>
      <WatchedBox>
        <WatchedTxt>
          <span>{name}</span>
          {!isEdit ? (
            <span className="edit" onClick={editHandler}>
              edit
            </span>
          ) : (
            <span className="edit" onClick={editHandler}>
              done
            </span>
          )}
        </WatchedTxt>
        <WatchBut>
          {isEdit ? (
            <div>
              <div className="tags">
                {tags &&
                  tags.map((v, i) => (
                    <Tag key={i} onDelete={() => tagDeleteHandler(v)}>
                      {v}
                    </Tag>
                  ))}
              </div>
              <div className="tag-search">
                <input type="text" onChange={onChange} value={tag} />
                <Button onClick={() => tagAddHandler(tag)}>Add</Button>
              </div>

              {tagList.length ? (
                <div className="select-tag">
                  {tagList.map((tag, i) => (
                    <div key={i} onClick={() => autoComplete(tag)}>
                      {tag}
                    </div>
                  ))}
                </div>
              ) : (
                <></>
              )}
            </div>
          ) : tags.length ? (
            tags.map((v, i) => <Tag key={i}>{v}</Tag>)
          ) : (
            <button onClick={() => setIsEdit(true)} className="watch-tag-btn">
              {name}
            </button>
          )}
        </WatchBut>
      </WatchedBox>
    </Boxs>
  );
}

export default TagsBox;
