import React, { useState } from 'react';
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

  return (
    <Container>
      <RecommendComponent>
        <AiOutlineCaretUp
          size={36}
          onClick={() => {
            setLike(like + 1);
          }}
        />
        <span className="likeNumbers">{like}</span>
        <AiOutlineCaretDown size={36} onClick={setLike} />
        <BsBookmark size={15} />
        <GiBackwardTime size={20} />
      </RecommendComponent>
    </Container>
  );
}

export default Recommend;
