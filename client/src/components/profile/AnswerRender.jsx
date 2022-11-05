import React from 'react';
import styled from 'styled-components';

const Answer = styled.div`
  width: auto;
  border: 1px solid red;
  & article {
    border: 1px solid #c6c6c6;
    width: auto;
    height: auto;
    text-align: center;
    border-radius: 10px;
    margin: 15px;
  }
  & .title-art {
    padding-top: 20px;
    padding-bottom: 30px;
    background-color: rgb(242, 130, 52);
    color: white;
  }
  & h2 {
    font-size: 1.2rem;
  }
  & h3 {
    font-size: 0.7rem;
    margin: 10px;
  }
  & h4 {
    font-size: 0.5rem;
  }
`;

function AnswerRender({
  answerContent,
  answerCreatedAt,
  answerId,
  answerLikesCount,
}) {
  let DeleteTags = '';
  if (answerContent) {
    DeleteTags = answerContent.replace(/<[^>]*>?/g, '');
  }
  return (
    <>
      <Answer>
        <article className="title-art">
          <h2>Content : {DeleteTags}</h2>
        </article>
        <article>
          <h3>CreatedAt : {answerCreatedAt}</h3>
        </article>
        <article>
          <h4>Answer Id : {answerId}</h4>
        </article>
        <article>
          <h4>LikesCount : {answerLikesCount}</h4>
        </article>
      </Answer>
    </>
  );
}
export default AnswerRender;
