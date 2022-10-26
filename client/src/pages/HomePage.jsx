import React from "react";
import styled from "styled-components";
import Header from "../components/Header";
import PostList from "../components/home/PostList";
import Nav from "../components/home/Nav";
import Footer from "../components/Footer";
import TagsBox from "../components/home/TagsBox";
import TopMenu from '../components/home/TopMenu';


const HomepageComponent = styled.div`
  width: 100vw;
  header {
    height: 50px;
    border-bottom: 1px solid #d9d9d9;
  }
  section {
    width: 100%;
    display: flex;
    justify-content: center;

    nav {
      display: flex;
      justify-content: flex-end;
      border-right: 1px solid black;
    }
    article {
      display: flex;
      flex-direction: column;
      .top-menu {
        height: 110px;
      }
    }
  }
`;

function HomePage() {
  return (
    <HomepageComponent>
      <header>
        <Header />
      </header>
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
