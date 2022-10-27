import React from 'react';
import styled from 'styled-components';
import AnswerPost from '../components/post/AnswerPost';
import AnswerSorted from '../components/post/AnswerSorted';
import Header from '../components/Header';
import Nav from '../components/home/Nav';
import Footer from '../components/Footer';

const PostPageComponent = styled.div`
  width: 100vw;
  section {
    width: 100%;
    display: flex;
    justify-content: center;

    article {
      display: flex;
      flex-direction: column;
      border-left: 1px solid #d9d9d9;
      padding-left: 2rem;
      .top-menu {
        height: 110px;
      }
    }
    aside {
      width: 244px;
    }
  }
`;

function PostPage() {
  return (
    <PostPageComponent>
      <Header />
      <section>
        <Nav />
        <article>
          {/* postViewer */}
          <AnswerSorted />
          {/* answerViewer */}
          <AnswerPost />
        </article>
        <aside></aside>
      </section>
      <Footer />
    </PostPageComponent>
  );
}

export default PostPage;
