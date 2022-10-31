import React, { useState } from 'react';
import styled from 'styled-components';
import Post from './Post';

const PostListComponent = styled.div`
  width: 926px;
  border-left: 1px solid #d9d9d9;
`;

function PostList({ posts, sizeHandler }) {
  const [data, setData] = useState([]);
  const [currentPer, setCurrentPer] = useState(15);

  return (
    <PostListComponent>
      {/* {Array(15)
        .fill()
        .map((v, i) => (
          <Post key={`idx${i}`} />
        ))} */}
      {posts.map((v) => (
        <Post key={v.questionId} post={v} />
      ))}
    </PostListComponent>
  );
}

export default PostList;
