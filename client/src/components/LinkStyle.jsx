import React from 'react';
import styled from 'styled-components';
import { Link } from 'react-router-dom';

const LinkStyledComponent = styled(Link)`
  color: ${(props) => (props.css?.color ? props.css.color : '#3378c7')};
  text-decoration: none;
  display: flex;
  align-items: center;
  :hover {
    color: ${(props) => (props.css?.hover ? '#fff' : '')};
  }
`;

function LinkStyle({ path, children, css }) {
  return (
    <LinkStyledComponent to={path} css={css}>
      {children}
    </LinkStyledComponent>
  );
}

export default LinkStyle;
