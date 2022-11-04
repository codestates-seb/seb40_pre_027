import React from 'react';
import styled from 'styled-components';

const AnswerCall = styled.div``;

const AnswerTo = styled.h3``;
function AnswerRender(profileRen) {
  const { answer } = profileRen;
  return (
    <>
      <AnswerCall>
        <AnswerTo>{answer}안녕하세요 답변조회칸입니다</AnswerTo>
      </AnswerCall>
    </>
  );
}
export default AnswerRender;
