import React from 'react';
import styled from 'styled-components';

const Question = styled.div`
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

function QuestionRender({ title, content, questionId, viewCount }) {
  let DeleteTags = '';

  if (content) {
    DeleteTags = content.replace(/<[^>]*>?/g, '');
  }
  //vecel 재가입 가입했어fefefe
  return (
    <>
      <Question>
        <article className="title-art">
          <h2>Title : {title}</h2>
        </article>
        <article className="content-art">
          <h2>Content</h2>
          <h3>
            <code>{DeleteTags}</code>
          </h3>
        </article>
        <article className="id-art">
          <h4>Question Id : {questionId}</h4>
        </article>
        <article className="count-art">
          <h4>ViewCount : {viewCount}</h4>
        </article>
      </Question>
    </>
  );
}
export default QuestionRender;
