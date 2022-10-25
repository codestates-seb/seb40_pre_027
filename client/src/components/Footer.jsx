import React from 'react'
import styled from 'styled-components'
/* import smallLogo from '../images/smallLogo.png'
 */
function Footer() {
  const FooterComponent = styled.div`
    .smallLogo{
      width: 50px;
      height: 50px;
    }
    h2{
      color: #babfc4;
      font-weight: bold;
    }
    height: 322px;
    padding-right: 11rem;
    padding-top: 2rem;
    line-height: 150%;
    color: #9fa9a1;
    background-color: #232629;
    display: flex;
    flex-direction: row;
    justify-content: space-around;
    
  `
  
  const StackComponent = styled.div`
    
  `
  const ProductsComponent = styled.div`
    
  `
  const CompanyComponent = styled.div`
    
  `
  const StackNetworkComponent = styled.div`
    
  `
  return (
    <FooterComponent>      
{/*       <img className='smallLogo' alt='stack-small-logo' src={smallLogo}></img>
 */}      <StackComponent>
      <h2>STACK OVERFLOW</h2>
      <div>Questions</div>
      <div>Help</div>
    </StackComponent>
    <ProductsComponent>
      <h2>Products</h2>
      <div>Teams</div>
      <div>Advertising</div>
      <div>Collectives</div>
      <div>Talent</div>
    </ProductsComponent>
    <CompanyComponent>
      <h2>COMPANY</h2>
      <div>About</div>
      <div>Press</div>
      <div>Work Here</div>
      <div>Legal</div>
      <div>Privacy Policy</div>
      <div>Terms of Service</div>
      <div>Contact Us</div>
      <div>Cookie Settings</div>
      <div>Cookie Policy</div>
    </CompanyComponent>
    <StackNetworkComponent>
      <h2>STACK EXCHANGE NETWORK</h2>
      <div>Technology</div>
      <div>Culture & recreation</div>
      <div>Life & arts</div>
      <div>Science</div>
      <div>Professional</div>
      <div>Business</div>
      <div></div>
      <div>API</div>
      <div>Data</div>
    </StackNetworkComponent>
    </FooterComponent>
  )
}

export default Footer