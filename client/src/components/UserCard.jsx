import React from 'react';
import styled from 'styled-components';
import axios from 'axios';
const UserCardComponent = styled.div`
  width: auto;
  height: auto;
  padding: 10px;

  .user-card-wrapper {
    display: flex;
    align-items: center;
  }

  img {
    width: 24px;
    height: 24px;
    border-radius: 4px;
  }

  .reputation {
    color: #525960;
    margin-left: 5px;
    margin-bottom: 2px;
    font-size: 12px;
  }
`;

function UserCard({ onGetUserProfile, reputation }) {
  //userprofile에 대한 axios 요청
  const accessToken = localStorage.getItem('accessToken');
  async function getUserProfile() {
    try {
      const res = await axios.get(`/user/profile`, {
        headers: { access: accessToken },
      });
      onGetUserProfile(res.data);
      console.log(res.data, '123');
    } catch (error) {
      console.log(error);
    }
  }
  const imgurl = 'https://avatars.githubusercontent.com/u/107591946?s=400&v=4';
  return (
    <UserCardComponent onClick={getUserProfile}>
      <div className="user-card-wrapper">
        <div className="user-avatar">
          <img src={imgurl} alt="user-avatar"></img>
        </div>
        <div className="reputation">17{reputation}</div>
      </div>
    </UserCardComponent>
  );
}

export default UserCard;
