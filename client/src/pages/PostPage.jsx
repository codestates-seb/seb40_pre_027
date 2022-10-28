import React from 'react';
import styled from 'styled-components';
import AnswerPost from '../components/post/AnswerPost';
import AnswerSorted from '../components/post/AnswerSorted';
import Header from '../components/Header';
import Nav from '../components/home/Nav';
import Footer from '../components/Footer';
import PostBody from '../components/post/PostBody';
import Title from '../components/post/Title';

const PostPageComponent = styled.div`
  width: 100vw;
  .section {
    width: 100%;
    display: flex;
    justify-content: center;

    article {
      display: flex;
      flex-direction: column;
      border-left: 1px solid #d9d9d9;
      /* padding-left: 2rem; */
      .top-menu {
        height: 110px;
      }
    }
    .post-aside {
      display: flex;
    }
    aside {
      width: 245px;
      /* border: 1px solid red; */
    }
  }
`;

function PostPage() {
  return (
    <PostPageComponent>
      <Header />

      <div className="section">
        <Nav />
        <article>
          <Title />
          <div className="post-aside">
            <div>
              <PostBody answer={false} />
              <AnswerSorted />
              <PostBody answer={true} />
              <AnswerPost />
            </div>
            <aside></aside>
          </div>
        </article>
      </div>

      <Footer />
    </PostPageComponent>
  );
}

export default PostPage;
