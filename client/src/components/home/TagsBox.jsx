import React from "react";
import styled from "styled-components";

const CustomBox = styled.div`
  width: 250px;
  height: 120px;
  border: 1px solid #f0f0f0;
  margin: 15px;
  box-shadow: 2px 7px 15px 7px #d8d8d8;
`;
const CB = styled.h1`
  font-size: 0.8rem;
  text-align: center;
  line-height: 4rem;
  color: #2b93e9;
  cursor: pointer;
`;
const CustomTxt = styled.div`
  width: 250px;
  height: 60px;
  font-size: 1rem;
  background-color: #f0f0f0;
  line-height: 3.8rem;
  padding-left: 10px;
`;
const WatchedBox = styled.div`
  width: 250px;
  height: 120px;
  border: 1px solid #f0f0f0;
  margin: 15px;
  box-shadow: 2px 7px 15px 7px #d8d8d8;
`;
const WatchedTxt = styled.div`
  width: 100%;
  height: 60px;
  font-size: 1rem;
  background-color: #f0f0f0;
  line-height: 3.8rem;
  padding-left: 10px;
`;
const WatchBut = styled.button`
  width: 130px;
  height: 40px;
  border: 1px solid #81a6c7;
  background-color: #e2ecf4;
  color: #45719d;
  margin: 10px 30px 50px 70px;
`;
const IgnoredBox = styled.div`
  width: 250px;
  height: 120px;
  border: 1px solid #f0f0f0;
  margin: 15px;
  box-shadow: 2px 7px 15px 7px #d8d8d8;
`;
const IgnoredTxt = styled.div`
  width: 250px;
  height: 60px;
  font-size: 1rem;
  background-color: #f0f0f0;
  line-height: 3.8rem;
  padding-left: 10px;
`;
const IgnoreBut = styled.button`
  width: 130px;
  height: 40px;
  border: 1px solid #81a6c7;
  background-color: #e2ecf4;
  color: #45719d;
  margin: 10px 30px 50px 70px;
`;
const Boxs = styled.div``;

function TagsBox() {
  return (
    <Boxs>
      <CustomBox>
        <CustomTxt>Custom Filters</CustomTxt>
        <CB>Create a custom filters</CB>
      </CustomBox>
      <WatchedBox>
        <WatchedTxt>Watched Tags</WatchedTxt>
        <WatchBut>Watch a tag</WatchBut>
      </WatchedBox>
      <IgnoredBox>
        <IgnoredTxt>Ignored Tags</IgnoredTxt>
        <IgnoreBut>Watch a tag</IgnoreBut>
      </IgnoredBox>
    </Boxs>
  );
}

export default TagsBox;
