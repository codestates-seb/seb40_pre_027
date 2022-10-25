import React from "react";
import styled from "styled-components";
import Header from "../conponents/Header";
import PostList from "../conponents/home/PostList";

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
        <nav>nav</nav>
        <article>
          <div className="top-menu">top-menu</div>
          <PostList />
        </article>
        <aside>aside</aside>
      </section>

      <footer>footer</footer>
    </HompageComponent>
  );
}

export default HomePage;
