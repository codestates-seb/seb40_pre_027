import React from "react";
import styled from "styled-components";
import {
  FcGoogle,
  FcQuestions,
  FcInternal,
  FcExpand,
  FcLightAtTheEndOfTunnel,
} from "react-icons/fc";
import { FaGithub } from "react-icons/fa";
import Header from "../components/Header";

const Signpage = styled.main`
  width: 100%;
  height: 100vh;
  display: flex;
  align-items: center;
  flex-direction: column;
  background-color: rgb(241, 242, 243);
  .divcontent {
    display: flex;
    align-items: center;
    height: 100%;
  }
`;
const Sign = styled.div`
  width: 1000px;
  height: 100%;
  margin: auto;
  display: flex;
  align-items: center;
  justify-content: center;
`;
const SignTxt = styled.div`
  display: flex;
  flex-direction: column;
  margin-right: 100px;
  & :nth-child(1) {
    font-size: 2rem;
    margin-bottom: 10px;
  }
  & h2 {
    font-size: 1rem;
    margin: 40px 18px 19px 18px;
    font-weight: 400;
  }
  & :nth-child(6) {
    font-size: 0.7rem;
    color: #acacac;
    margin: 25px 15px 0px 18px;
  }
  & :nth-child(7) {
    font-size: 0.7rem;
    color: #8ab9de;
    margin: 5px 5px 0px 18px;
  }
`;
const SignIn = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: flex-end;
  align-items: center;
`;
const SignGoogle = styled.div`
  width: 310px;
  height: 38px;
  margin: 15px;
  border-radius: 5px;
  text-align: center;
  line-height: 2.3rem;
  border: 1px solid white;
  background-color: white;
  font-size: 0.9rem;
  cursor: pointer;
`;
const SignGithub = styled.div`
  width: 310px;
  height: 38px;
  margin-bottom: 25px;
  border-radius: 5px;
  text-align: center;
  line-height: 2.3rem;
  border: 1px solid #2f3337;
  background-color: #2f3337;
  color: white;
  font-size: 0.9rem;
  cursor: pointer;
`;
const SignMe = styled.div`
  width: 310px;
  height: 430px;
  border: 1px solid white;
  background-color: white;
  box-shadow: 0px 6px 15px 0px #a8a8a8;
  border-radius: 5px;
`;
const Display = styled.div`
  display: block;
  margin: 30px 10px 25px 18px;
  font-size: 1.2rem;
  font-weight: 600;
  & input {
    width: 270px;
    height: 35px;
    border: 1px solid #9c9c9c;
    border-radius: 5px;
  }
  & h2 {
    margin-bottom: 10px;
  }
`;
const Email = styled.div`
  display: block;
  margin: 10px 10px 25px 18px;
  font-size: 1.2rem;
  font-weight: 600;
  & input {
    width: 270px;
    height: 35px;
    border: 1px solid #9c9c9c;
    border-radius: 5px;
  }
  & h2 {
    margin-bottom: 10px;
  }
`;
const Password = styled.div`
  display: block;
  margin: 10px 10px 25px 18px;
  font-size: 1.2rem;
  font-weight: 600;
  & input {
    width: 270px;
    height: 35px;
    border: 1px solid #9c9c9c;
    border-radius: 5px;
  }
  & h2 {
    margin-bottom: 10px;
  }
`;
const Signwarn = styled.h2`
  color: #6b6b6b;
  font-size: 0.8rem;
  margin: 20px;
`;
const SignUp = styled.button`
  background: #1e98fc;
  border: 1px solid white;
  color: white;
  border-radius: 5px;
  width: 270px;
  height: 35px;
  margin: 20px;
`;
const SignHelp = styled.h3`
  margin: 20px;
  display: flex;
  font-size: 0.8rem;
  letter-spacing: 0.4px;
  & :nth-child(1) {
    color: #5c6cc6;
    cursor: pointer;
  }
`;
function SigninPage() {
  return (
    <Signpage>
      <Header></Header>
      <div className="divcontent">
        <Sign>
          <SignTxt>
            <h1>Join the Stack Overflow community</h1>
            <h2>
              <FcQuestions size={35} /> Get unstuck -ask a question
            </h2>
            <h2>
              <FcInternal size={35} />
              Unlock new privileges like voting and commenting
            </h2>
            <h2>
              <FcExpand size={35} />
              Save your favorite tags, filters, and jobs
            </h2>
            <h2>
              <FcLightAtTheEndOfTunnel size={35} />
              Earn reputation and badges
            </h2>
            <h4>
              Collaborate and share knowledge with a private group for FREE.
            </h4>
            <h4>Get Stack Overflow for Teams free for up to 50 users.</h4>
          </SignTxt>
          <SignIn>
            <SignGoogle>
              <FcGoogle size={30} />
              &nbsp;&nbsp;Sign up with Google
            </SignGoogle>
            <SignGithub>
              <FaGithub size={30} />
              &nbsp;&nbsp;Sign up with Github
            </SignGithub>
            <SignMe>
              {/*회원가입 창*/}
              <Display>
                <h2>Display name</h2>
                <input></input>
              </Display>
              <Email>
                <h2>Email</h2>
                <input></input>
              </Email>
              <Password>
                <h2>Password</h2>
                <input></input>
              </Password>
              <Signwarn>
                Passwords must contain at least eight characters, including at
                least and 1 number
              </Signwarn>
              <SignUp>Sign up</SignUp>
            </SignMe>
            <SignHelp>
              Already have an account?&nbsp;&nbsp;&nbsp;&nbsp;<h3>Log in</h3>
            </SignHelp>
          </SignIn>
        </Sign>
      </div>
    </Signpage>
  );
}

export default SigninPage;
