import React from "react";
import styled from "styled-components";
const TagComponent = styled.div`
  padding: 9px;
  background-color: #e3ecf3;
  margin-right: 12px;
  display: flex;
  align-items: center;
  height: 28px;
  border-radius: 8px;
  color: rgb(57, 115, 157);
`;
function Tag({ children }) {
  return <TagComponent>{children}</TagComponent>;
}

export default Tag;
