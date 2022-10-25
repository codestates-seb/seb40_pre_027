import React from 'react';
import styled from 'styled-components';
import Header from '../components/Header';
import PostList from '../components/home/PostList';
import Nav from '../components/home/Nav';
import Footer from '../components/Footer';
import TagsBox from '../components/home/TagsBox';

const HompageComponent = styled.div`
  width: 100vw;
  header {
    height: 47px;
    border-bottom: 1px solid black;
  }
  section {
    width: 100%;
    display: flex;
    justify-content: center;
    nav {
      display: flex;
      justify-content: flex-end;
      flex: 1;
      border-right: 1px solid black;
    }
    article {
      display: flex;
      flex-direction: column;

      flex: 1.4;
      .top-menu {
        height: 110px;
      }
    }
    aside {
      border-left: 1px solid black;
      flex: 1;
    }
  }
`;

function HomePage() {
  return (
    <HompageComponent>
      <Header></Header>
      <section>
        <Nav />
        <article>
          <div className="top-menu">top-menu</div>
          <PostList />
        </article>
        <aside>
          <TagsBox />
        </aside>
      </section>
      <Footer />
    </HompageComponent>
  );
}

export default HomePage;
