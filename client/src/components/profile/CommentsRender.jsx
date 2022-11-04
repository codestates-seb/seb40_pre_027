import React from 'react';
import styled from 'styled-components';

const WriteCall = styled.article`
  border: 1px solid red;
`;

const Comments = styled.h3`
  display: flex;
  justify-content: center;
  height: auto;
`;
function CommentsRender(profileRen) {
  const { replies } = profileRen;
  return (
    <>
      <WriteCall>
        <Comments>{replies}안녕하세요 댓글 조회칸입니다</Comments>
      </WriteCall>
    </>
  );
}

export default CommentsRender;
