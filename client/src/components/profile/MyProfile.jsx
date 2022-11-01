import React from 'react'
import styled from 'styled-components';
import ProfilePicture from '../../img/ProfilePicture.png'
import { MdCake } from 'react-icons/md'
import { BiTimeFive } from 'react-icons/bi'
import { FaRegCalendarAlt } from 'react-icons/fa'
import { FaPen } from 'react-icons/fa'

const MyProfileComponent = styled.div`
    .profilePicture{
        width: 160px;
        height: 160px;
        border-radius: 5px;
    }
    .editButton{
        color: #6a737c;
        font-size: 12px;
        width: 115px;
        height: 45px;
        background-color: #fff;
        border-radius: 5px;
        border: 1px solid #6a737c;
        outline: 0px;
    }
    
    width: 1067px;
    height: 144px;
    margin: 30px;
    display: flex;
    flex-direction: row;
    justify-content: space-between;
`

const ProfileInfoComponent = styled.div`
    .myName{
    display: flex;
    flex-direction: column;
    font-size: 34px;
    margin: 4px 4px 12px;
    }
    .myProfile{
    display: flex;
    flex-direction: row;
    }
    ul{
        color: #6a737c;
    }
    li{
        margin-right: 10px;
        margin-left: 4px;

    }
    display: flex;
    flex-direction: column;
    justify-content: center;
    padding-right: 200px;
`


function MyProfile() {
  return (
    <MyProfileComponent>
    <img
        className="profilePicture"
        alt="ProfilePicture"
        src={ProfilePicture}>
    </img>
    <ProfileInfoComponent>
        <div className='myName'>Name</div>
        <ul className='myProfile'>
            <MdCake />
            <li>Member for 9 days</li>
            <BiTimeFive />
            <li>Last seen this week</li>
            <FaRegCalendarAlt />
            <li>Visited 6 days, 2 consecutive</li>
        </ul>
    </ProfileInfoComponent>
    <button className='editButton'><FaPen />Edit Profile</button>
    </MyProfileComponent>
  )
}

export default MyProfile