import axios from 'axios';
import React, { useState, useRef, useEffect } from 'react';
import styled from 'styled-components';
import { useNavigate } from 'react-router-dom';
import { Editor } from '@toast-ui/react-editor';
import Button from '../Button';

import Tag from '../Tag';
import Recommend from './Recommend';

const PostBodyComponent = styled.div`
  display: flex;
  margin-top: 2rem;
  padding-left: 2rem;
  line-height: 1.25rem;
  button {
    margin: 1rem 0 0 0;
  }
  code {
    /* border: 1px solid black; */
    padding: 0.3rem;
    border-radius: 5px;
    background: #c9d4df;
  }
  .post-body-container {
    width: 100%;
    display: flex;
    flex-direction: column;
    margin-top: 1.25rem;
    margin-left: 1.25rem;
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
      width: 100%;
      display: flex;
      justify-content: space-between;
      font-size: 13px;
      margin-top: 16px;
      .share {
        position: relative;
      }
      .menu-wrapper {
        display: flex;
        .menu-item {
          margin: 5px;
          color: #51565d;
          cursor: pointer;
          :hover {
            color: #828c98;
          }
          :active {
            color: #828c98;
          }
          .share-dropdown {
            position: absolute;
            top: 0;
            z-index: 1000;
            inset: 10px;
            width: 200px;
            height: 50px;
            margin: 0.7rem -0.7rem;
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
      padding: 1rem 0;
      color: #a6b4c2;
      :hover {
        color: #d3e7ff;
      }
      :active {
        color: #d3e7ff;
      }
    }
  }
  .comment-input {
    width: 100%;
    display: flex;
    input {
      width: 100%;
      margin-right: 1rem;
      height: 2.5rem;
    }
  }
`;

function PostBody(props) {
  const navigate = useNavigate();
  const [shareClicked, setShareClicked] = useState(false);
  const [newAnswer, setNewAnswer] = useState('');
  const [comment, setComment] = useState('');
  const [isPatch, setIsPatch] = useState(false);

  const shareHandler = () => setShareClicked(!shareClicked);
  const contentRef = useRef();
  const editorRef = useRef();

  const createdAt = new Date(
    props.answer ? props.answer.answerCreatedAt : props.createdAt
  );
  const modifiedAt = new Date(
    props.answer ? props.answer.answerModifiedAt : props.modifiedAt
  );
  useEffect(() => {
    if (isPatch) {
      editorRef.current?.getInstance().setHTML(props.answer.answerContent);
    }
  }, [isPatch]);
  const patchHandler = () => {
    if (props.answer.answerId) {
      setIsPatch(true);
    } else {
      const contentArr = props.content.split(' <br calssName="boundary"/> ');
      const introduce = contentArr[0];
      const expand = contentArr[1];
      const data = {
        id: props.questionId,
        title: props.title,
        introduce,
        expand,
        tags: props.tags,
      };
      navigate(`/write`, { state: data });
    }
  };
  const onChange = () => {
    setNewAnswer(editorRef.current.getInstance().getHTML());
  };
  const deleteHandler = () => {
    if (props.answer.answerId) {
      axios
        .delete(`/answer/${props.answer.answerId}`)
        .then(() => {
          const newAnswersArray = props.answersArray.filter(
            (v) => v.answerId !== props.answer.answerId
          );
          props.setAnswersArray(newAnswersArray);
        })
        .catch(() => alert('답변 삭제 실패'));
    } else {
      axios
        .delete(`/question/${props.questionId}`)
        .then(() => navigate('/'))
        .catch(() => alert('실패'));
    }
  };
  const answerPatchHandler = () => {
    axios
      .patch(`/answer/${props.answer.answerId}`, {
        answerContent: newAnswer,
      })
      .then(() => {
        setIsPatch(false);
        const newAnswersArray = [...props.answersArray];
        newAnswersArray[props.idx].answerContent = newAnswer;
        props.setAnswersArray(newAnswersArray);
      });
  };
  const userimg =
    'https://www.gravatar.com/avatar/088029d211d686a016bcfdc326523d62?s=256&d=identicon&r=PG';

  return (
    <PostBodyComponent>
      <Recommend />
      <div className="post-body-container">
        <section className="main-content" ref={contentRef}>
          {isPatch ? (
            <div>
              <Editor
                initialValue={props.answer.answerContent}
                height="300px"
                initialEditType="markdown"
                onChange={(e) => onChange(e, true)}
                useCommandShortcut={true}
                ref={editorRef}
                autofocus={true}
              />
              <Button onClick={answerPatchHandler}>수정</Button>
            </div>
          ) : (
            <div
              dangerouslySetInnerHTML={{
                __html: props.answer
                  ? props.answer.answerContent
                  : props.content,
              }}
            ></div>
          )}

          {/* {contentRef.innerHTML(props.content)} */}
        </section>
        {!props.answer && (
          <section className="tags">
            {props.tags !== null &&
              props.tags.map((v, i) => <Tag key={i}>{v}</Tag>)}
          </section>
        )}
        <section className="body-footer">
          <div className="body-footer-wrapper">
            <div className="menu-wrapper">
              <div
                className="menu-item"
                title="Short permalink to this question"
              >
                <div role="button" onClick={shareHandler} className="share">
                  Share
                  {shareClicked && (
                    <div className="share-dropdown">
                      <label>Share a link to this question</label>
                      <input type="text" value={'/post'} />
                    </div>
                  )}
                </div>
              </div>
              <div
                className="menu-item"
                title="Revise and improve this post"
                onClick={patchHandler}
              >
                Edit
              </div>
              <div
                className="menu-item"
                title="Follow this question to receive notification"
              >
                Follow
              </div>
              <div
                className="menu-item"
                title="Post delete"
                onClick={deleteHandler}
              >
                Delete
              </div>
            </div>
            <div className="edited-date-wrapper">
              <div className="edited-date" title="Show all edits to this post">
                edited
                {createdAt.toLocaleString()}
              </div>
            </div>
            <div className="post-owner-wrapper">
              <div className="created-date">{createdAt.toLocaleString()}</div>
              <div className="user-info">
                <div className="user-avatar">
                  <a href="/user">
                    <img className="user-img" src={userimg} alt="user-img" />
                  </a>
                </div>
                <div className="user-details">
                  <a href="/user">Happygoluck{props.user}</a>
                  <div className="flair">
                    <span className="reputation-score">
                      51{props.reputation}
                    </span>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </section>
        <section className="add-comment">Add a comment</section>

        <div>
          {props.commentsArray.map((comment) => (
            <div>{comment.content}</div>
          ))}
        </div>
        <div className="comment-input">
          <input type="text" onChange={(e) => setComment(e.target.value)} />
          <Button>submit</Button>
        </div>
      </div>
    </PostBodyComponent>
  );
}

export default PostBody;
