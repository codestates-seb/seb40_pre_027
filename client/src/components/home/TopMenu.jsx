import React from 'react';
import styled from 'styled-components'

function TopMenu() {
    const TopMenuComponent = styled.div`
        h1{
            font-size: 27px;
        }
        .askButton{
            margin-bottom: 12px;
            padding: 10.4px;
        }
        font-size: 13px;
        font-weight: bold;
        width: 727px;
        
        
    `
    const H1Component =  styled.div`
        
        display: flex;
        flex-direction: row;
        justify-content: space-between;

        margin-bottom: 12px;
        padding: 10.4px;

    `

    const ButtonListsComponent = styled.div`
        display: flex;
        flex-direction: row-reverse;
    `
  return (
    <TopMenuComponent>
        <H1Component>
            <h1>Top Questions</h1>
            <button className='askButton'>Ask Question</button>
        </H1Component>
        <ButtonListsComponent>
            <button>Interesting</button>
            <button>Bountied</button>
            <button>Hot</button>
            <button>Week</button>
            <button>Month</button>
        </ButtonListsComponent>

    </TopMenuComponent>
  )
}

export default TopMenu