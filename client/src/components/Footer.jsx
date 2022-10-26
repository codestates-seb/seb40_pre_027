import React from 'react'
import styled from 'styled-components'
import smallLogo from '../img/smallLogo.png'


const FooterComponent = styled.div`
  .smallLogo{
    width: 50px;
    height: 50px;
    padding-top: 10px;
  }
  .footerList{
    font-size: 11px;
    display: flex;
    flex-direction: column;
    justify-content: space-between;
  }
  
  h2{
    color: #babfc4;
    font-weight: bold;
  }

  width: 100%;
  height: 322px;
  background-color: #232629;
  color: #9fa9a1;
  font-size: 13px;
  padding: 40px 100px;
  line-height: 180%;

  display: flex;
  flex-direction: row;
  justify-content: space-around;

`

  
function Footer() {
  
  return (
    <FooterComponent>      
      <img className='smallLogo' alt='stack-small-logo' src={smallLogo}></img>
      <div>
        <h2>STACK OVERFLOW</h2>
        <p>Questions</p>
        <p>Help</p>
      </div>
      <div>
        <h2>Products</h2>
        <p>Teams</p>
        <p>Advertising</p>
        <p>Collectives</p>
        <p>Talent</p>
      </div>
      <div>
        <h2>COMPANY</h2>
        <p>About</p>
        <p>Press</p>
        <p>Work Here</p>
        <p>Legal</p>
        <p>Privacy Policy</p>
        <p>Terms of Service</p>
        <p>Contact Us</p>
        <p>Cookie Settings</p>
        <p>Cookie Policy</p>
      </div>
      <div>
        <h2>STACK EXCHANGE NETWORK</h2>
        <p>Technology</p>
        <p>Culture & recreation</p>
        <p>Life & arts</p>
        <p>Science</p>
        <p>Professional</p>
        <p>Business</p>
        <br></br>
        <p>API</p>
        <p>Data</p>
      </div>
      <div className='footerList'>
        <p>Blog Facebook Twitter LinkedIn Instagram</p>
        <p>Site design / logo © 2022 Stack Exchange Inc; user 
          <br /> contributions licensed under CC BY-SA. rev 
          <br /> 2022.10.26.42986
        </p>
      </div>
    </FooterComponent>
  )
}

export default Footer