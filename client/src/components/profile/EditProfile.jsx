import React from 'react'
import styled from 'styled-components';
import ProfilePicture from '../../img/ProfilePicture.png'
import WriteAboutMe from './WriteAboutMe'

const EditProfileComponent = styled.div`
    h1{
        font-size: 27px;
        margin-bottom: 30px;
        font-weight: bold;
    }
    h2{
        font-size: 20px;
    }
    div{
        font-size: 15px;
        font-weight: bold;
        margin-top: 30px;
        padding: 2px;
    }
    width: 1067px;
    margin: 30px;

`
const ProfileEditInfoComponent = styled.div`
    .profilePicture{
        width: 160px;
        height: 160px;
        border-radius: 5px;
    }
    .inputBox{
        width: 423px;
        height: 33px;
    }
    border: 1px solid #6a737c;
    border-radius: 5px;
`

const EditComponent = styled.div`
    margin: 30px;
    
`

const ButtonComponent = styled.div`
    button{
        font-size: 15px;
        width: 115px;
        height: 45px;
        border-radius: 5px;
        outline: 0px;
    }

    .saveButton{
        background-color: #0a95ff;
        color: #ffffff;
        border: none;
    }
    .cancelButton{
        background-color: #ffffff;
        color: #0a95ff;
        border: none;

    }
    .deleteButton{
        color: #C83434;
        background-color: white;
        border: 1px solid #C83434;
    }

    display: flex;
    flex-direction: row;
    justify-content: space-between;
`

function EditProfile() {
  return (
    <EditProfileComponent>
        <h1>Edit your Profile</h1>
        <h2>Public information</h2>
        <ProfileEditInfoComponent>
            <EditComponent>
            <img
            className="profilePicture"
            alt="ProfilePicture"
            src={ProfilePicture}>
            </img>
            <div>Display name</div>
            <input
                className='inputBox'
            ></input>
            <div>Location</div>
            <input
                className='inputBox'
            ></input>
            <div>Tittle</div>
            <input
                className='inputBox'
            ></input>
            <div>About Me</div>
            <WriteAboutMe />
            </EditComponent>
        </ProfileEditInfoComponent>
        <ButtonComponent>
            <span>
                <button className='saveButton'>Save Profile</button>
                <button className='cancelButton'>Cancel</button>
            </span>
            <button className='deleteButton'>Delete Account</button>
        </ButtonComponent>
    </EditProfileComponent>
  )
}

export default EditProfile