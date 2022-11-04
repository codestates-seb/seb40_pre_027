import styled from 'styled-components';
import LinkStyle from '../components/LinkStyle';
import Header from '../components/Header';
import Footer from '../components/Footer';
import Nav from '../components/home/Nav';
import MyProfile from '../components/profile/MyProfile';
import SmallLogo from '../img/smallLogo.png';
import { Link } from 'react-router-dom';
import React from 'react';
import { useState, useEffect } from 'react';
import AboutRender from '../components/profile/AboutRender';
import requestDataWithToken from '../components/util/requestNewAccessToken'
// import { useParams } from 'react-router-dom';
const ProfilePages = styled.main`
  width: 100vw;
  section {
    width: 100%;
    display: flex;
    justify-content: center;
    article {
      display: flex;
      flex-direction: column;
      border-left: 1px solid #d9d9d9;
      .top-menu {
        height: 110px;
      }
    }
  }
`;
const ProfileTab = styled.div`
  margin: 50px;
  & ul {
    width: 150px;
    display: flex;
    justify-content: space-between;

    .submenu {
      border: 1px solid #f28234;
    }
  }
`;
const ProfileIn = styled.div`
  width: 1000px;
  height: auto;
  margin-left: 40px;
  margin-bottom: 40px;
`;
const LeftContent = styled.div`
  float: left;
  width: 30%;
`;

const LeftBox = styled.div`
  width: 100%;
  & h2 {
    font-size: 1.8rem;
    font-weight: 400;
    margin-top: 15px;
  }
  & div {
    width: 100%;
    height: 20vh;
    border: 1px solid #c6c6c6;
    border-radius: 10px;
    margin-top: 15px;
    display: flex;
    .logo {
      background-image: url(${SmallLogo});
      background-size: 100% 100%;
      width: 30px;
      height: 30px;
      margin-left: 75px;
      margin-top: 20px;
    }
    & h3 {
      width: auto;
      height: 16px;
      margin-top: 27px;
      color: rgb(42, 116, 175);
    }
  }
  & span {
    display: flex;
    margin-top: 15px;
  }
  & h4 {
    font-size: 0.8rem;
    line-height: 2rem;
    margin-left: 80px;
    margin-top: 19px;
  }
  & :nth-child(3) {
    height: 8vh;
    border: 1px solid #c6c6c6;
    border-radius: 10px;
    margin-top: 15px;
  }
`;

const RightContent = styled.div`
  float: right;
  width: 68%;
`;
const About = styled.div`
  & h2 {
    font-size: 1.8rem;
    font-weight: 400;
    margin-top: 15px;
  }
  & div {
    width: 100%;
    height: auto;
    border: 1px solid #c6c6c6;
    border-radius: 10px;
    margin-top: 15px;
    position: relative;
    & div {
      border: none;
      height: auto;
    }
    & h4 {
      font-size: 0.9rem;
      color: #6096ff;
      text-align: center;
      line-height: 2rem;
      cursor: pointer;
    }
    & h3 {
      font-size: 0.9rem;
      text-align: center;
    }
    .about-txt {
      border: 1px solid red;
    }
  }
  & span {
    display: flex;
    margin-top: 15px;
  }
`;

function ProfilePage({ profiles }) {
  const [profile, setProfile] = useState([]);

  useEffect(() => {
    requestDataWithToken(setProfile)
  }, []);
   console.log(profile)

  return (
    <ProfilePages>
      <Header />
      <section>
        <Nav />
        <article>
          <MyProfile />
          <ProfileTab>
            <ul>
              <li>Profile</li>
              <li>
                <LinkStyle path="/myProfile/write" css={{ color: 'black' }}>
                  Write
                </LinkStyle>
              </li>
            </ul>
          </ProfileTab>
          <ProfileIn>
            <LeftContent>
              <LeftBox>
                <h2>Stats</h2>
                <div></div>
              </LeftBox>
              <LeftBox>
                <span>
                  <h2>Communities</h2>
                  <h4>Edit</h4>
                </span>
                <p></p>
                <div>
                  <span className="logo"></span>
                  <Link to="/" style={{ textDecoration: 'none' }}>
                    <h3>Stack Overflow</h3>
                  </Link>
                </div>
              </LeftBox>
            </LeftContent>
            <RightContent>
              <About>
                <h2>ABOUT</h2>
                <div>
                  <div>
                    <AboutRender className="ProfileList" profileRen={profile}>
                      {profiles &&
                        profiles.map((user) => {
                          return <div key={user.id}>{user.name}</div>;
                        })}
                    </AboutRender>
                    <h3>
                      Your about me section is currently blank. Would you like
                      to add one?
                    </h3>
                    <Link
                      to="/myProfile/edit"
                      style={{ textDecoration: 'none' }}
                    >
                      <h4>Edit profile</h4>
                    </Link>
                  </div>
                </div>
              </About>
            </RightContent>
          </ProfileIn>
        </article>
      </section>
      <Footer />
    </ProfilePages>
  );
}

export default ProfilePage;
