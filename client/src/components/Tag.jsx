import React from 'react';
import styled from 'styled-components';
import { MdClose } from 'react-icons/md';
import { BsEye, BsEyeSlash } from 'react-icons/bs';
import { useNavigate } from 'react-router-dom';
import { tagSearchActions } from '../store/tagSearch';
import { useDispatch } from 'react-redux';

const TagComponent = styled.div`
  cursor: pointer;
  padding: 9px;
  background-color: ${(props) => (props.status === 2 ? '#b9bdbd' : '#e3ecf3')};

  margin-right: 12px;
  display: flex;
  align-items: center;
  height: 28px;
  border-radius: 8px;
  color: ${(props) => (props.status === 2 ? '#d9d9d9' : 'rgb(57, 115, 157)')};
  position: relative;

  .closed {
    cursor: pointer;
    margin-left: 0.5rem;
    font-size: 1rem;
  }
`;
function Tag({ children, onDelete, status, searchEvent }) {
  const navigate = useNavigate();
  const dispatch = useDispatch();
  const tagSearchHandler = () => {
    if (searchEvent) {
      dispatch(tagSearchActions.searchPost(children));
      navigate('/');
    }
  };
  return (
    <TagComponent status={status} onClick={tagSearchHandler}>
      {status === 1 ? <BsEye /> : status === 2 ? <BsEyeSlash /> : <></>}
      {children}
      {onDelete && (
        <div className="closed" onClick={onDelete}>
          <MdClose />
        </div>
      )}
    </TagComponent>
  );
}

export default Tag;
