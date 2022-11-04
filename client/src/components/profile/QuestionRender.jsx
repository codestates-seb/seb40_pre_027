import React from 'react';
import styled from 'styled-components';

const Question = styled.div``;
function QuestionRender(write) {
  const { questions } = write;
  console.log(questions);
  return (
    <>
      <Question></Question>
    </>
  );
}
export default QuestionRender;
