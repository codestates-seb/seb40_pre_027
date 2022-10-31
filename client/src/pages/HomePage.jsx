import React, { useEffect, useState } from 'react';
import styled from 'styled-components';
import Header from '../components/Header';
import PostList from '../components/home/PostList';
import Nav from '../components/home/Nav';
import Footer from '../components/Footer';
import TagsBox from '../components/home/TagsBox';
import TopMenu from '../components/home/TopMenu';
import Pagination from '../components/home/Pagination';
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
function HomePage() {
  const [posts, setPosts] = useState([]);
  const [size, setSize] = useState(15);
  const [currentPage, setCurrentPage] = useState(1);

  const sizeHandler = (per) => setSize(per);
  const currentPageHandler = (p) => setCurrentPage(p);

  useEffect(() => {
    axios.get(`/question?&page=${currentPage}&size=${size}`).then((res) => {
      setPosts(res.data);
      console.log(res.data);
    });
  }, [size, currentPage]);
  return (
    <HomepageComponent>
      <Header />
      <section>
        <Nav />
        <article>
          <TopMenu />
          {posts.length ? <PostList posts={posts} /> : <div>...</div>}
          <Pagination
            size={size}
            sizeHandler={sizeHandler}
            currentPage={currentPage}
            currentPageHandler={currentPageHandler}
          />
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
