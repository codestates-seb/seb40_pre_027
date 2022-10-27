import React, { useState } from 'react';
import styled from 'styled-components';
import Post from './Post';

const PostListComponent = styled.div`
  width: 926px;
  border-left: 1px solid #d9d9d9;
  .pagination {
    width: 100%;
    margin-top: 2rem;
    display: flex;
    justify-content: space-between;
    padding: 1.8rem;
    .page {
      display: flex;
      .dot {
        padding: 0.5rem 0.8rem;
      }
      button {
        box-shadow: 0;
        border: 1px solid #d9d9d9;
        background: #fff;
        border-radius: 5px;
        :hover {
          background-color: #d9d9d9;
        }
      }
      .page-items {
        border: 1px solid #d9d9d9;
        width: 23px;
        height: 25px;
        display: flex;
        justify-content: center;
        align-items: center;
        border-radius: 5px;
        margin: 3px;
        :not(&.check) {
          cursor: pointer;
        }

        :hover:not(&.check) {
          background-color: #d9d9d9;
        }
      }
    }

    .per {
      display: flex;
      align-items: center;
      .per-items {
        width: 30px;
        height: 25px;
        display: flex;
        justify-content: center;
        align-items: center;
        border-radius: 5px;
        margin-right: 5px;
        border: 1px solid #d9d9d9;
        :not(&.check) {
          cursor: pointer;
        }

        :hover:not(&.check) {
          background-color: #d9d9d9;
        }
      }
    }
    .check {
      background: rgb(244, 130, 37);
      color: #fff;
    }
  }
`;

function PostList() {
  const [data, setData] = useState([]);
  const [currentPage, setCurrentPage] = useState(1);
  const [currentPer, setCurrentPer] = useState(15);
  const pagination = [1, 2, 3];
  const per = [15, 30, 50];
  return (
    <PostListComponent>
      {Array(15)
        .fill()
        .map((v, i) => (
          <Post key={`idx${i}`} />
        ))}
      <div className="pagination">
        <div className="page">
          {pagination.map((v, i) => (
            <div
              className={currentPage === v ? 'page-items check' : 'page-items'}
              key={i}
            >
              {v}
            </div>
          ))}
          <div className="dot">...</div>
          <button>next</button>
        </div>
        <div className="per">
          {per.map((v, i) => (
            <div
              className={currentPer === v ? 'per-items check' : 'per-items'}
              key={i}
            >
              {v}
            </div>
          ))}
          <span>per page</span>
        </div>
      </div>
    </PostListComponent>
  );
}

export default PostList;
