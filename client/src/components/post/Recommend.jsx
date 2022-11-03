import React, { useState, useEffect } from 'react';
import styled from 'styled-components';
import { AiOutlineCaretUp } from 'react-icons/ai';
import { AiOutlineCaretDown } from 'react-icons/ai';
import { GiBackwardTime } from 'react-icons/gi';
import { BsBookmark } from 'react-icons/bs';
import axios from 'axios';

const RecommendComponent = styled.div`
  .likeNumbers {
    font-size: 21px;
  }
  color: #babfc4;
  width: 40px;
  height: 171px;
  margin: 2px;
  padding: 4px;

  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: space-around;
`;
const Container = styled.div`
  width: 40px;
`;

function Recommend({ questionId, questionLikeCount, answerId }) {
  //질문의 추천 비추천 상태
  const [questionLike, setQuestionLike] = useState(0);
  //답변의 추천 비추천 상태
  const [answerLike, setAnswerLike] = useState(0);

  //첫 post화면 렌더링 시, 글이 기존에 갖고 있는 추천 수 반영
  useEffect(() => {
    setQuestionLike(questionLikeCount);
  }, [questionLikeCount]);

  //추천비추천 axios 요청 endpoint를 위한 변수 val
  let val;

  //추천비추천 axios 요청
  //질문의 추천비추천 버튼이 눌리는지, 답변의 추천비추천 버튼이 눌리는지 여부는
  //props로 내려오는 questionId와 anwerId의 유무로 판단
  async function likeDislikeRequest() {
    if (questionId) {
      try {
        const res = await axios.post(
          `/question/${questionId}/vote?val=${val}`,
          {},
          {
            headers: { access: localStorage.getItem('accessToken') },
          }
        );
        setQuestionLike(res.data);
        console.log(res);
      } catch (error) {
        console.log(error);
        if (error.response.status === 408) {
          alert(error.response.data.message);
        } else {
          alert(error);
        }
      }
    }
  }

  //추천버튼 handler
  const likeHandler = () => {
    val = 1;
    likeDislikeRequest();
  };

  //비추천버튼 handler
  const dislikeHandler = () => {
    val = -1;
    likeDislikeRequest();
  };

  return (
    <Container>
      <RecommendComponent>
        <AiOutlineCaretUp size={36} onClick={likeHandler} />
        {questionId ? (
          <span className="likeNumbers">{questionLike}</span>
        ) : answerId ? (
          <span className="likeNumbers">{answerLike}</span>
        ) : (
          <></>
        )}

        <AiOutlineCaretDown size={36} onClick={dislikeHandler} />
        <BsBookmark size={15} />
        <GiBackwardTime size={20} />
      </RecommendComponent>
    </Container>
  );
}

export default Recommend;
