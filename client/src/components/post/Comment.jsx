import React, { useState } from 'react';
import styled from 'styled-components';
import { BiPencil, BiTrash } from 'react-icons/bi';
import Button from '../Button';

const CommentComponent = styled.div`
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
    margin-top: 0.5rem;
    button {
      margin: 0;
    }
  }

  > div {
    span {
      cursor: pointer;
    }
  }
`;

function Comment({ children, onPatch, onDelete, id, idx }) {
  const [commentIsPatch, setCommentIsPatch] = useState(false);
  const [newComment, setNewComment] = useState(children);
  const patchHandler = () => {
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
        <div>
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
