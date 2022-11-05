import React from 'react';
import MyProfile from '../components/profile/MyProfile';
import EditProfile from '../components/profile/EditProfile';
import styled from 'styled-components';
import Header from '../components/Header';
import Nav from '../components/home/Nav';
import Footer from '../components/Footer';

const HomepageComponent = styled.div`
  width: 100vw;
  section {
    width: 100%;
    display: flex;
    justify-content: center;
    article {
      display: flex;
      flex-direction: column;
      border-left: 1px solid #d9d9d9;
    }
  }
`;
function ProfilePage() {
  return (
    <HomepageComponent>
      <Header />
      <section>
        <Nav />
        <article>
          <MyProfile />
          <EditProfile />
        </article>
        <aside></aside>
      </section>
      <Footer />
    </HomepageComponent>
  );
}

export default ProfilePage;
