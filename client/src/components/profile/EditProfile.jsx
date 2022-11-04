import React, { useState, useRef,useEffect } from 'react';
import styled from 'styled-components';
// import ProfilePicture from '../../img/ProfilePicture.png';
import { Editor } from '@toast-ui/react-editor';
import axios from 'axios';
import { Link } from 'react-router-dom';
import ImageUpload from './ImageUpload';

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

  // toast ui
  const editRef = useRef();
  
  const onChangeEdit = (e, editor) => {
    if (editor) {
      setEdit({
        ...edit,
        introduction: editRef.current.getInstance().getHTML(),
      });
    } else {
      setEdit({
        ...edit,
        [e.target.name]: e.target.value,
      });
    }
    console.log(edit);
    
  }; 
  // get요청
  useEffect(() => {
    const access = localStorage.getItem('accessToken')
      if(access) {
        axios.get('/user/profile/edit', {headers : {access}})
      .then((res) => {
        console.log(res)
        setEdit({
          // image: res.data.image
          name: res.data.name,
          location: res.data.location,
          title: res.data.title,
          introduction: "aaaa"
        }) 
      })
      }
  }, [])

  // put요청
  async function putProfile() {
    const access = localStorage.getItem('accessToken')
    try {
    await axios.put('/user/profile/edit', edit,{headers : {access}})
      .then((res) => {
        console.log(res)
      })
      } catch(error){
        console.error(error)
      }
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
          <Editor
            initialValue={edit.introduction}
            height="300px"
            initialEditType="markdown"
            useCommandShortcut={true}
            name="introduction"
            onChange={(e) => onChangeEdit(e, true)}
            // onChange={(e) => console.log}
            value={edit.introduction || ''}
            ref={editRef}
          ></Editor>
        </EditComponent>
      </ProfileEditInfoComponent>
      <ButtonComponent>
        <span>
          <button className="saveButton" onClick={putProfile}>
            Save Profile
          </button>
          <Link to= '/myProfile'>
          <button className="cancelButton"> Cancel</button>
          </Link>
        </span>
        <button className="deleteButton">Delete Account</button>
      </ButtonComponent>
    </EditProfileComponent>
  );
}

export default EditProfile;
