import React from 'react';
import styled from 'styled-components';
import { FcGoogle } from 'react-icons/fc';
import { FaGithub } from 'react-icons/fa';
import logo from '../img/smallLogo.png';
import LinkStyle from './LinkStyle';

const SocialLoginComponent = styled.div`
  width: 310px;
  height: 38px;
  margin-bottom: 12.5px;
  border-radius: 5px;
  text-align: center;
  line-height: 2.3rem;
  border: 1px solid #d9d9d9;
  background-color: ${(props) =>
    props.social === 'github' ? '#2f3337' : '#fff'};
  color: ${(props) => (props.social === 'github' ? '#fff' : '#000')};
  font-size: 0.8rem;
  display: flex;
  justify-content: center;
  align-items: center;
  cursor: pointer;
  img {
    width: 25px;
    height: 25px;
  }
  span {
    margin-left: 1rem;
  }
`;

function SocialLogin({ social, children }) {
  return (
    <SocialLoginComponent social={social}>
      {social === 'google' ? (
        <FcGoogle size={28} />
      ) : social === 'github' ? (
        <FaGithub size={28} />
      ) : (
        <img src={logo} alt="logo" />
      )}
      {social === 'stack' ? (
        <span>
          <LinkStyle path="/login" css={{ color: '#484747' }}>
            {children}
          </LinkStyle>
        </span>
      ) : (
        <span>{children}</span>
      )}
    </SocialLoginComponent>
  );
}

export default SocialLogin;
