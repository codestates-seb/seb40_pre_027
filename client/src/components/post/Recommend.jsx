import React, { useState, useRef } from 'react';
import styled from 'styled-components';
import { AiOutlineCaretUp } from 'react-icons/ai';
import { AiOutlineCaretDown } from 'react-icons/ai';
import { GiBackwardTime } from 'react-icons/gi';
import { BsBookmark } from 'react-icons/bs';

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

function Recommend() {
  const [like, setLike] = useState(0);

  const likeHandlerPlus = () => {
    setLike(like + 1)
    // alert("이미 추천하셨습니다.")
  }

  const likeHandlerMinus = () => {
    setLike(like - 1)
    // alert("이미 비추천하셨습니다.")
  }

  return (
    <Container>
      <RecommendComponent>
        <AiOutlineCaretUp
          size={36}
          onClick={ likeHandlerPlus }
        />
        <span className="likeNumbers">{like}</span>
        <AiOutlineCaretDown 
          size={36} 
          onClick={ likeHandlerMinus } />
        <BsBookmark size={15} />
        <GiBackwardTime size={20} />
      </RecommendComponent>
    </Container>
  );
}

export default Recommend;
