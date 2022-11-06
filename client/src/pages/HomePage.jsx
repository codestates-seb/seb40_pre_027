import React, { useEffect, useState } from 'react';
import { useSelector } from 'react-redux';
import styled from 'styled-components';
import Header from '../components/Header';
import PostList from '../components/home/PostList';
import Nav from '../components/home/Nav';
import Footer from '../components/Footer';
import TagsBox from '../components/home/TagsBox';
import TopMenu from '../components/home/TopMenu';
import Pagination from '../components/home/Pagination';
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
  const [paginationLength, setPaginationLength] = useState(1);

  const [watchedTags, setWatchedTags] = useState([]);
  const [ignoredTags, setIgnoredTags] = useState([]);

  //redux에서 searchInput 값 받아오기
  const searchInput = useSelector((state) => state.search.searchInput);
  const tagSearch = useSelector((state) => state.tagSearch.tagSearch);

  const sizeHandler = (per) => setSize(per);
  const currentPageHandler = (p) => setCurrentPage(p);
  useEffect(() => {
    //searchInput값이 존재하지 않으면 모든 게시글 가져오기
    const api = process.env.REACT_APP_API_URL;
    console.log(`${api}/question?&page=${currentPage}&size=${size}`);
    axios.defaults.withCredentials = true;
    if (!searchInput && !tagSearch) {
      axios
        .get(`${api}/question?&page=${currentPage}&size=${size}`)
        .then((res) => {
          console.log(res);
          setPosts(res.data.data);
          setPaginationLength(res.data.totalCount);
        });
    }
    //searchInput값이 존재하면 해당 값으로 검색
    if (searchInput) {
      axios
        .get(
          `${api}/question/search?q=${searchInput}&page=${currentPage}&size=${size}`
        )
        .then((res) => {
          setPosts(res.data.data);
        });
    } else if (tagSearch) {
      axios
        .get(
          `${api}/question/tagged/${tagSearch}?tab=Newest&page=${currentPage}&size=${size}`
        )
        .then((res) => {
          setPosts(res.data.data);
        });
    }
  }, [size, currentPage, searchInput, tagSearch]);
  return (
    <HomepageComponent>
      <Header />
      <section>
        <Nav />
        <article>
          <TopMenu />
          {posts && posts.length ? (
            <PostList
              posts={posts}
              watchedTags={watchedTags}
              ignoredTags={ignoredTags}
            />
          ) : (
            <div>...</div>
          )}
          <Pagination
            size={size}
            sizeHandler={sizeHandler}
            currentPage={currentPage}
            currentPageHandler={currentPageHandler}
            paginationLength={paginationLength ? paginationLength : 1}
          />
        </article>
        <aside>
          <TagsBox
            setTags={setWatchedTags}
            tags={watchedTags}
            name={'Watched Tags'}
          />
          <TagsBox
            setTags={setIgnoredTags}
            tags={ignoredTags}
            name={'Ignored Tags'}
          />
        </aside>
      </section>
      <Footer />
    </HomepageComponent>
  );
}

export default HomePage;
