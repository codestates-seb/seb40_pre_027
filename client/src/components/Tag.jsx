import React from 'react';
import styled from 'styled-components';
import { MdClose } from 'react-icons/md';
const TagComponent = styled.div`
  padding: 9px;
  background-color: #e3ecf3;
  margin-right: 12px;
  display: flex;
  align-items: center;
  height: 28px;
  border-radius: 8px;
  color: rgb(57, 115, 157);
  position: relative;
  .closed {
    cursor: pointer;
    margin-left: 0.5rem;
    font-size: 1rem;
  }
`;
function Tag({ children, onClick }) {
  return (
    <TagComponent>
      {children}
      {onClick && (
        <div className="closed" onClick={onClick}>
          <MdClose />
        </div>
      )}
    </TagComponent>
  );
}

export default Tag;
