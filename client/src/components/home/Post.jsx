import React from 'react';
import styled from 'styled-components';
import Tag from '../Tag';
import LinkStyle from '../LinkStyle';
import { useNavigate } from 'react-router-dom';

const PostComponent = styled.div`
  background: ${(props) => (props.watched ? 'rgb(253,247,226)' : '#fff')};
  color: ${(props) => (props.ignored ? '#9d9e9e' : '#000')};
  width: 100%;
  height: 125px;
  border-top: 1px solid #d9d9d9;
  display: flex;
  align-items: center;
  padding: 4.5rem 0;
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
      cursor: pointer;
      color: ${(props) => (props.ignored ? '#9d9e9e' : '#3378c7')};
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
        color: ${(props) => (props.ignored ? '#9d9e9e' : '#3378c7')};
      }
      .user-answers {
        font-weight: 700;
      }
      margin-right: 10px;
    }
  }
`;

function Post({ post, watchedTags, ignoredTags }) {
  const navigate = useNavigate();
  const { questionId, title, content, tags, viewCount, likeCount, answers } =
    post;
  const imgurl =
    'https://blog.kakaocdn.net/dn/tEMUl/btrDc6957nj/NwJoDw0EOapJNDSNRNZK8K/img.jpg';
  const deleteTagContent = content
    .replace(/<[^>]*>?/g, '')
    .replace(/&lt;/g, '')
    .replace(/&gt;/g, '');
  const tagsEvaluate = (arr) => {
    // watchedtags, ignoredtags에 tag가 포함되어 있는지 검사하는 함수
    let result = false;
    if (tags !== null) {
      for (let i = 0; i < tags.length; i++) {
        if (arr.includes(tags[i])) {
          result = true;
          break;
        }
      }
    }
    return result;
  };
  const watched = tagsEvaluate(watchedTags);
  const ignored = tagsEvaluate(ignoredTags);
  return (
    <PostComponent watched={watched} ignored={ignored}>
      <div className="info-post">
        <div className="vote">{`${likeCount} votes`}</div>
        <div className="answers">{`${answers.length} answers`}</div>
        <div className="views">{`${viewCount} views`}</div>
      </div>
      <div className="article">
        <h3
          className="title article-status"
          onClick={() => navigate(`/post/${questionId}`)}
        >
          {title}
        </h3>

        <div className="article-content article-status">
          {deleteTagContent.length <= 160
            ? deleteTagContent
            : `${deleteTagContent.substring(0, 160)}...`}
        </div>
        <div className="info-side article-status">
          <div className="tags">
            {tags !== null ? (
              tags.map((v, i) => (
                <Tag
                  key={i}
                  status={
                    watchedTags.includes(v) ? (
                      1
                    ) : ignoredTags.includes(v) ? (
                      2
                    ) : (
                      <></>
                    )
                  }
                >
                  {v}
                </Tag>
              ))
            ) : (
              <div></div>
            )}
          </div>
          <div className="info-user">
            <img src={imgurl} alt="user-img" />
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
