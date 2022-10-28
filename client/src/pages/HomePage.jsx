import React, { useEffect } from 'react';
import styled from 'styled-components';
import Header from '../components/Header';
import PostList from '../components/home/PostList';
import Nav from '../components/home/Nav';
import Footer from '../components/Footer';
import TagsBox from '../components/home/TagsBox';
import TopMenu from '../components/home/TopMenu';
import { useLocation } from 'react-router-dom';
import axios from 'axios';

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
      .top-menu {
        height: 110px;
      }
    }
  }
`;
async function getPost() {
  const data = await axios.get('?page=1&size=10');
  console.log(data);
}
function HomePage() {
  useEffect(() => {
    getPost();
  }, []);
  return (
    <HomepageComponent>
      <Header />
      <section>
        <Nav />
        <article>
          <TopMenu />
          <PostList />
        </article>
        <aside>
          <TagsBox />
        </aside>
      </section>
      <Footer />
    </HomepageComponent>
  );
}

export default HomePage;
