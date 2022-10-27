import React, { useState } from 'react';
import styled from 'styled-components';

import Tag from '../Tag';

const PostBodyComponent = styled.div`
  margin: 10% auto;
  display: flex;
  flex-direction: column;
  width: 65%;

  a {
    text-decoration: none;
    color: #0074cc;
    :hover {
      color: #53a8fc;
    }
    :active {
      color: #53a8fc;
    }
  }

  .tags {
    display: flex;
    margin-top: 25px;
    margin-bottom: 18px;
  }

  .body-footer-wrapper {
    display: flex;
    justify-content: space-between;
    font-size: 13px;
    margin-top: 16px;

    .menu-wrapper {
      display: flex;
      .menu-item {
        margin: 5px;
        color: #51565d;
        :hover {
          color: #828c98;
        }
        :active {
          color: #828c98;
        }
        .share-dropdown {
          z-index: 1000;
          padding: 12px;
          inset: 10px;
          border: 1px solid red;
        }
      }
    }

    .edited-date {
      font-size: 12px;
    }

    .post-owner-wrapper {
      width: 200px;
      background: #d9eaf7;
      padding: 5px 6px 7px 7px;
      border-radius: 3px;

      .created-date {
        color: #6e737c;
        font-size: 12px;
        margin-top: 1px;
        margin-bottom: 10px;
      }
      .user-info {
        display: flex;
        .user-img {
          height: 32px;
          width: 32px;
        }
        .user-details {
          margin-left: 8px;
          width: calc(100% - 40px);

          .reputation-score {
            color: #6a737c;
            margin-right: 2px;
            font-size: 12px;
          }
        }
      }
    }
  }

  .add-comment {
    font-size: 13px;
    color: #a6b4c2;
    :hover {
      color: #d3e7ff;
    }
    :active {
      color: #d3e7ff;
    }
  }
`;

function PostBody(props) {
  const [shareClicked, setShareClicked] = useState(false);

  const shareHandler = () => {
    setShareClicked(!shareClicked);
  };

  console.log(shareClicked);

  const tags = ['azure', 'hive', 'azure-hdinsight'];
  const userimg =
    'https://www.gravatar.com/avatar/088029d211d686a016bcfdc326523d62?s=256&d=identicon&r=PG';

  return (
    <PostBodyComponent>
      <section className="main-content">{props.content}글 본문 입니다.</section>
      <section className="tags">
        {tags.map((v, i) => (
          <Tag key={i}>{v}</Tag>
        ))}
      </section>
      <section className="body-footer">
        <div className="body-footer-wrapper">
          <div className="menu-wrapper">
            <div className="menu-item" title="Short permalink to this question">
              <div role="button" onClick={shareHandler}>
                Share
              </div>
              {shareClicked && (
                <div className="share-dropdown">
                  <label>Share a link to this question</label>
                  <input type="text" value={'/post'}/>
                </div>
              )}
            </div>
            <div className="menu-item" title="Revise and improve this post">
              Edit
            </div>
            <div
              className="menu-item"
              title="Follow this question to receive notification"
            >
              Folow
            </div>
          </div>
          <div className="edited-date-wrapper">
            <div className="edited-date" title="Show all edits to this post">
              {props.editedAt}edited Feb 9 at 14:36
            </div>
          </div>
          <div className="post-owner-wrapper">
            <div className="created-date">
              asked Feb 8 at 14:36{props.createdAt}
            </div>
            <div className="user-info">
              <div className="user-avatar">
                <a href="/user">
                  <img className="user-img" src={userimg} alt="user-img" />
                </a>
              </div>
              <div className="user-details">
                <a href="/user">Happygoluck{props.user}</a>
                <div className="flair">
                  <span className="reputation-score">51{props.reputation}</span>
                </div>
              </div>
            </div>
          </div>
        </div>
      </section>
      <section className="add-comment">Add a comment</section>
    </PostBodyComponent>
  );
}

export default PostBody;
