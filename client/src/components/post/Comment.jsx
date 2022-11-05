import React, { useState } from 'react';
import styled from 'styled-components';
import { BiPencil, BiTrash } from 'react-icons/bi';
import Button from '../Button';

const CommentComponent = styled.div`
  width: 100%;
  display: flex;
  justify-content: space-between;
  input {
    width: 100%;
    height: 2.5rem;
    padding: 1rem;
    border: 1px solid #d9d9d9;
    border-radius: 5px;
    margin-right: 1rem;
  }
  .comment-patch {
    display: flex;
    width: 100%;
    button {
      margin: 0;
    }
  }

  .hanlder-btn {
    display: flex;
    align-items: flex-end;
    span {
      cursor: pointer;
      margin: 0 0.3rem;
    }
  }
`;

function Comment({ children, onPatch, onDelete, id, idx }) {
  const [commentIsPatch, setCommentIsPatch] = useState(false);
  const [newComment, setNewComment] = useState(children);
  const patchHandler = () => {
    // comment 수정 함수
    onPatch(id, newComment, idx);
    setCommentIsPatch(false);
  };
  return (
    <CommentComponent>
      {commentIsPatch ? (
        <div className="comment-patch">
          <input
            value={newComment}
            onChange={(e) => setNewComment(e.target.value)}
          />
          <Button onClick={patchHandler}>submit</Button>
        </div>
      ) : (
        children
      )}
      {commentIsPatch ? (
        <></>
      ) : (
        <div className="hanlder-btn">
          <span>
            <BiPencil onClick={() => setCommentIsPatch(true)} />
          </span>
          <span>
            <BiTrash onClick={() => onDelete(id)} />
          </span>
        </div>
      )}
    </CommentComponent>
  );
}

export default Comment;
