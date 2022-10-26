import React from "react";
import styled from "styled-components";
import Tag from "../Tag";

const PostComponent = styled.div`
  width: 100%;
  height: 125px;
  border-top: 1px solid #d9d9d9;
  display: flex;
  align-items: center;
  .info-post {
    display: flex;
    flex-direction: column;
    align-items: flex-end;
    flex: 1.5;
    .vote {
      font-weight: 700;
    }
    div {
      padding: 5px;
    }
  }
  .article {
    flex: 8;
    margin-left: 10px;
    .info-side {
      display: flex;
      justify-content: space-between;
    }
    > div {
      padding: 5px;
    }
    > h3 {
      padding: 5px;
    }
    .title {
      font-size: 20px;
      color: #3378c7;
    }
    .tags {
      display: flex;
    }
    .info-user {
      display: flex;
      align-items: center;
      padding: 5px;
      img {
        width: 18px;
        height: 18px;
        margin-right: 5px;
      }
      span {
        padding: 0.15rem;
      }
      .user-name {
        color: #3378c7;
      }
      .user-answers {
        font-weight: 700;
      }
      margin-right: 10px;
    }
  }
`;

function Post() {
  const tags = ["excel", "encoding", "power-automate"];
  return (
    <PostComponent>
      <div className="info-post">
        <div className="vote">{`0 votes`}</div>
        <div className="answers">{`0 answers`}</div>
        <div className="views">{`0 views`}</div>
      </div>
      <div className="article">
        <h3 className="title article-status">{`tring to use a loop`}</h3>
        <div className="article-content article-status">
          i am using arcpy to work within geo
        </div>
        <div className="info-side article-status">
          <div className="tags">
            {tags.map((v, i) => (
              <Tag key={i}>{v}</Tag>
            ))}
          </div>
          <div className="info-user">
            <img src="https://blog.kakaocdn.net/dn/tEMUl/btrDc6957nj/NwJoDw0EOapJNDSNRNZK8K/img.jpg" />
            <span className="user-name">{`PieCharmer`}</span>
            <span className="user-answers">{15}</span>
            <span>{`asked 58 sec ago`}</span>
          </div>
        </div>
      </div>
    </PostComponent>
  );
}

export default Post;
