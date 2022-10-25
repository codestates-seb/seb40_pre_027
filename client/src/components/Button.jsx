import styled from 'styled-components';
import React from 'react';

const ButtonComponent = styled.button`
  background-color: ${(props) => (props.data ? props.data.background : '#0088ef')};
  color: ${(props) => (props.data ? props.data.color : 'white')};
  border-radius: 5px;
  border: none;
  padding: 10px;
  text-align: center;
  border: 0.5px solid #0074cc;
  margin: 0 5px;

  &:hover {
    background: ${(props) => (props.data ? props.data.hovercolor : '#0074cc')};
  }
  &:active {
    background: ${(props) => (props.data ? props.data.activecolor : '#0074cc')};
  }
`;

function Button(props) {
  return <ButtonComponent data={props.data}>{props.children}</ButtonComponent>;
}

export default Button;
