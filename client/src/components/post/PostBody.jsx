import axios from 'axios';
import React, { useState, useRef, useEffect } from 'react';
import styled from 'styled-components';
import { useNavigate } from 'react-router-dom';
import { Editor } from '@toast-ui/react-editor';
import Button from '../Button';

import Tag from '../Tag';
import Recommend from './Recommend';
import Comment from './Comment';

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
  .comments {
    border-top: 1px solid #d9d9d9;
    margin: 1.25rem 0;
    padding: 0.8rem 0;
  }
  .comment-input {
    width: 100%;
    display: flex;
    height: 2.5rem;
    margin-bottom: 1rem;
    input {
      width: 100%;
      margin-right: 1rem;
      border-radius: 5px;
      box-shadow: 0;
      border: 1px solid #d9d9d9;
      padding: 1rem;
    }
    button {
      height: 100%;
      margin: 0;
    }
  }
`;

function PostBody(props) {
  const navigate = useNavigate();
  const [shareClicked, setShareClicked] = useState(false);
  const [newAnswer, setNewAnswer] = useState('');
  const [comment, setComment] = useState('');
  const [answerIsPatch, setAnswerIsPatch] = useState(false);
  const [replyArray, setReplyArray] = useState([]);

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
    if (answerIsPatch) {
      editorRef.current?.getInstance().setHTML(props.answer.answerContent);
    } else if (props.answer.answerId) {
      setReplyArray(props.answer.replies);
    }
  }, [answerIsPatch]);
  const patchHandler = () => {
    if (props.answer.answerId) {
      setAnswerIsPatch(true);
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
        setAnswerIsPatch(false);
        const newAnswersArray = [...props.answersArray];
        newAnswersArray[props.idx].answerContent = newAnswer;
        props.setAnswersArray(newAnswersArray);
      });
  };
  const commentSubmitHandler = async () => {
    try {
      const access = localStorage.getItem('accessToken');
      if (props.answer.answerId) {
        await axios.post(
          `/reply/${props.answer.answerId}`,
          {
            replyContent: comment,
          },
          { headers: { access } }
        );
        // const newPostData = await axios.get(`/question/${props.questionId}`);
        // console.log(newPostData);
        // await setReplyArray(newPostData.data.answer.replies);
      } else {
        await axios.post(
          `/comment/${props.questionId}`,
          { content: comment },
          { headers: { access } }
        );
        const newPostData = await axios.get(`/question/${props.questionId}`);
        await props.setCommentsArray(newPostData.data.comments);
      }
      setComment('');
    } catch (err) {
      console.log(err);
    }
  };
  const commentOnDeleteHandler = async (id) => {
    try {
      if (props.answer.answerId) {
        await axios.delete(`/reply/${id}`);
        const newReplysArray = [...replyArray].filter((v) => v.replyId !== id);
        setReplyArray(newReplysArray);
      } else {
        await axios.delete(`/comment/${id}`);
        const newCommentsArray = props.commentsArray.filter(
          (v) => v.commentId !== id
        );
        props.setCommentsArray(newCommentsArray);
      }
    } catch (err) {
      alert('삭제 실패');
      console.log(err);
    }
  };
  const commentOnPatchHandler = async (id, content, idx) => {
    try {
      if (props.answer.answerId) {
        await axios.patch(`/reply/${id}`, { replyContent: content });
        const newReplysArray = [...replyArray];
        newReplysArray[idx].replyContent = content;
        setReplyArray(newReplysArray);
      } else {
        await axios.patch(`/comment/${id}`, { content });
        const newCommentsArray = [...props.commentsArray];
        newCommentsArray[idx].content = content;
        props.setCommentsArray(newCommentsArray);
      }
    } catch (err) {
      alert('코멘트 수정 실패');
      console.log(err);
    }
  };
  const userimg =
    'https://www.gravatar.com/avatar/088029d211d686a016bcfdc326523d62?s=256&d=identicon&r=PG';

  return (
    <PostBodyComponent>
      <Recommend questionId={props.questionId} />
      <div className="post-body-container">
        <section className="main-content" ref={contentRef}>
          {answerIsPatch ? (
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

        <div className="comments">
          {props.commentsArray && props.commentsArray.length ? (
            props.commentsArray.map((comment, i) => (
              <Comment
                key={comment.commentId}
                onDelete={commentOnDeleteHandler}
                onPatch={commentOnPatchHandler}
                id={comment.commentId}
                idx={i}
              >
                {comment.content}
              </Comment>
            ))
          ) : replyArray.length ? (
            replyArray.map((v, i) => (
              <Comment
                key={v.replyId}
                onDelete={commentOnDeleteHandler}
                onPatch={commentOnPatchHandler}
                id={v.replyId}
                idx={i}
              >
                {v.replyContent}
              </Comment>
            ))
          ) : (
            <></>
          )}
        </div>
        <section className="add-comment">Add a comment</section>
        <div className="comment-input">
          <input
            type="text"
            value={comment}
            onChange={(e) => setComment(e.target.value)}
          />
          <Button onClick={commentSubmitHandler}>submit</Button>
        </div>
      </div>
    </PostBodyComponent>
  );
}

export default PostBody;
