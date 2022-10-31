import React from 'react';
import styled from 'styled-components';

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

function UserCard({ reputation }) {
  const imgurl =
    'https://lh3.googleusercontent.com/a/ALm5wu0zIighZk4XrejD9MrEuxmgoXAnmWU4RCpp1cOF=k-s48';
  return (
    <UserCardComponent>
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
