import React, { useState, useEffect } from 'react';
import styled from 'styled-components';
// import axios from 'axios';
import { Link } from 'react-router-dom';
import ImageUpload from './ImageUpload';
import requestDataWithToken from '../util/requestNewAccessToken';

const EditProfileComponent = styled.div`
  h1 {
    font-size: 27px;
    margin-bottom: 30px;
    font-weight: bold;
  }
  h2 {
    font-size: 20px;
  }
  .margin-word {
    font-size: 15px;
    font-weight: bold;
    margin-top: 30px;
    padding: 2px;
  }
  width: 1067px;
  margin: 30px;
`;
const ProfileEditInfoComponent = styled.div`
  .inputBox {
    width: 423px;
    height: 33px;
  }
  #inputBoxSize {
    width: 1000px;
    height: 200px;
  }
  border: 1px solid #6a737c;
  border-radius: 5px;
`;

const EditComponent = styled.div`
  margin: 30px;
`;

const ButtonComponent = styled.div`
  button {
    font-size: 15px;
    width: 115px;
    height: 45px;
    border-radius: 5px;
    outline: 0px;
    margin-top: 30px;
  }

  .saveButton {
    background-color: #0a95ff;
    color: #ffffff;
    border: none;
  }
  .cancelButton {
    background-color: #ffffff;
    color: #0a95ff;
    border: none;
  }
  .deleteButton {
    color: #c83434;
    background-color: white;
    border: 1px solid #c83434;
  }

  display: flex;
  flex-direction: row;
  justify-content: space-between;
`;

function EditProfile() {
  // data 한 객체로 만들기
  const [edit, setEdit] = useState({
    // image: '',
    name: '',
    location: '',
    title: '',
    introduction: '',
  });

  const onChangeEdit = (e) => {
    setEdit({
      ...edit,
      [e.target.name]: e.target.value,
    });
    // console.log(edit);
  };

  // get요청
  useEffect(() => {
    requestDataWithToken(setEdit, '/user/profile/edit', 'get');
  }, []);

  // put요청
  const putData = { name: '', location: '', title: '', introduction: '' };
  function putProfile() {
    requestDataWithToken(setEdit, '/user/profile/edit', 'put', putData);
  }

  return (
    <EditProfileComponent>
      <h1>Edit your Profile</h1>
      <h2>Public information</h2>
      <ProfileEditInfoComponent>
        <EditComponent>
          <ImageUpload />
          <div className="margin-word">Display name</div>
          <input
            className="inputBox"
            name="name"
            onChange={onChangeEdit}
            value={edit.name}
          ></input>
          <div className="margin-word">Location</div>
          <input
            className="inputBox"
            name="location"
            onChange={onChangeEdit}
            value={edit.location || ''}
          ></input>
          <div className="margin-word">Title</div>
          <input
            className="inputBox"
            name="title"
            onChange={onChangeEdit}
            value={edit.title || ''}
          ></input>
          <div className="margin-word">About Me</div>
          <input
            className="inputBox"
            id="inputBoxSize"
            name="introduction"
            onChange={onChangeEdit}
            value={edit.introduction || ''}
          ></input>
        </EditComponent>
      </ProfileEditInfoComponent>
      <ButtonComponent>
        <span>
          <Link to="/myProfile">
            <button className="saveButton" onClick={putProfile}>
              Save Profile
            </button>
          </Link>
          <Link to="/myProfile">
            <button className="cancelButton"> Cancel</button>
          </Link>
        </span>
        <Link to="/">
          <button
            className="deleteButton"
            onClick={() => alert('기능 추가 예정입니다.')}
          >
            Delete Account
          </button>
        </Link>
      </ButtonComponent>
    </EditProfileComponent>
  );
}

export default EditProfile;
