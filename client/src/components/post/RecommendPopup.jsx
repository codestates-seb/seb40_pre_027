import React from 'react';
import styled from 'styled-components';
import { AiOutlineClose } from 'react-icons/ai';

const RecommendPopupComponent = styled.div`
  width: 250px;
  height: 50px;
  padding: 16px 16px;
  font-size: 13px;
  color: #3b4045;
  border: 1px solid #0074cc;
  border-radius: 5px;
  background-color: #ebf4fb;
`;
function RecommendPopup(props) {
  const { onClose } = props;

  return (
    <RecommendPopupComponent
      onClick={() => {
        onClose(false);
      }}
    >
      {' '}
      팝업입니다.
      <AiOutlineClose />
    </RecommendPopupComponent>
  );
}

export default RecommendPopup;
