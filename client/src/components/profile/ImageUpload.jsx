import React, { useState, useRef } from 'react'
import styled from 'styled-components';
import ProfilePicture from '../../img/ProfilePicture.png';


const ImageUploadComponent = styled.div`
    .profilePicture{
        width: 160px;
        height: 160px;
        border-radius: 5px;
    }
`

function ImageUpload() {
    const [Image, setImage] = useState(ProfilePicture)
    const fileInput = useRef(null)
    
    const onChange = (e) => {
        if(e.target.files[0]){
                setImage(e.target.files[0])
            }else{ //업로드 취소할 시
                setImage(ProfilePicture)
                return
            }
        //화면에 프로필 사진 표시
            const reader = new FileReader();
            reader.onload = () => {
                if(reader.readyState === 2){
                    setImage(reader.result)
                }
            }
            reader.readAsDataURL(e.target.files[0])
        }
  return (
    <ImageUploadComponent>
        <img
            className="profilePicture"
            alt="ProfilePicture"
            src={Image}
            onClick={()=>{fileInput.current.click()}}
          ></img>
        
        <input 
 	        type='file' 
    	    style={{display:'none'}}
            accept='image/*' 
            name='profile_img'
            onChange={onChange}
            ref={fileInput}/>
    </ImageUploadComponent>
  )
}

export default ImageUpload