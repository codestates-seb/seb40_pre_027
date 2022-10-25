import React from 'react';
import styled from 'styled-components'
import Button from '../Button'

function TopMenu() {
    const TopMenuComponent = styled.div`
        border-left: 1px solid #d9d9d9;
        width: 100%;
        h1{
            font-size: 27px;
        }
        .askButton{
            margin-bottom: 12px;
            padding: 10.4px;
        }
        font-size: 13px;
        font-weight: bold;
         
        
        
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
            <Button>Ask Question</Button>
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