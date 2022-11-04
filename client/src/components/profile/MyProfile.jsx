import React, { useState, useEffect } from 'react';
import styled from 'styled-components';
import ProfilePicture from '../../img/ProfilePicture.png';
import { MdCake } from 'react-icons/md';
import { BiTimeFive } from 'react-icons/bi';
import { FaRegCalendarAlt } from 'react-icons/fa';
import { FaPen } from 'react-icons/fa';
import { Link } from 'react-router-dom';
import requestDataWithToken from '../util/requestNewAccessToken'
// import axios from 'axios';


const MyProfileComponent = styled.div`
  .profilePicture {
    width: 160px;
    height: 160px;
    border-radius: 5px;
  }
  .editButton {
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
`;

const ProfileInfoComponent = styled.div`
  .myName {
    display: flex;
    flex-direction: column;
    font-size: 34px;
    margin: 4px 4px 12px;
  }
  .myProfile {
    display: flex;
    flex-direction: row;
    color: #6a737c;
  }
  .lists {
    margin-right: 10px;
    margin-left: 4px;
    font-size: 13px;
  }
  display: flex;
  flex-direction: column;
  justify-content: center;
  padding-right: 200px;
`;

function MyProfile() {
  const [profileList, setProfileList] = useState({
    name: '',
    createdDate: '',
    loginDate: '',
  });

  // 날짜 바꾸기
  function leftPad(value) {
    if (value >= 10) {
        return value;
    }
    return `0${value}`;
  }
  
  function toStringByFormatting(source, delimiter = '-') {
    const year = source.getFullYear();
    const month = leftPad(source.getMonth() + 1);
    const day = leftPad(source.getDate());

    return [year, month, day].join(delimiter);
}

  useEffect(() => {
    requestDataWithToken(setProfileList, '/user/profile', 'get')
  }, [])

  return (
    <MyProfileComponent>
      <img
        className="profilePicture"
        alt="ProfilePicture"
        src={ProfilePicture}
      ></img>
      <ProfileInfoComponent>
          <div className="myName">{profileList.name}</div>
        <div className="myProfile">
          <MdCake />
          <div 
            className='lists'>Created Date {toStringByFormatting(new Date(profileList.createdDate))}</div>
          <BiTimeFive />
          <div className='lists'>Login Date {toStringByFormatting(new Date(profileList.loginDate))}</div>
          <FaRegCalendarAlt />
          <div className='lists'>Visited 6 days, 2 consecutive</div>
        </div>
      </ProfileInfoComponent>
      <Link to="/myProfile/edit">
        <button className="editButton">
          <FaPen />
          Edit Profile
        </button>
      </Link>
    </MyProfileComponent>
  );
}

export default MyProfile;