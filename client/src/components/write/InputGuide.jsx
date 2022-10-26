import React from "react";
import styled from "styled-components";
import { BsPencil } from "react-icons/bs";

const InputGuideComponent = styled.aside`
  border: 1px solid #d9d9d9;
  width: 330px;
  height: 100%;
  margin: 2rem 0;
  border-radius: 5px;
  display: ${(props) => (props.disabled ? "none" : "block")};

  h2 {
    padding: 1rem;
    border-bottom: 1px solid #d9d9d9d9;
  }
  > div {
    display: flex;
    margin: 16px;
    .icon {
      width: 48px;
      font-size: 3rem;
      display: flex;
      justify-content: center;
      align-items: center;
      margin-right: 1rem;
    }
    .disc {
      font-size: 0.8rem;
      div {
        padding: 0.3rem 0;
        line-height: 1.15rem;
      }
    }
  }
`;

function InputGuide({ title, disabled }) {
  const guide = [
    {
      title: "title",
      guideTitle: "Writing a good title",
      disc: [
        "Your title should summarize the problem.",
        "You might find that you have a better idea of your title after writing out the rest of the question.",
      ],
    },
    {
      title: "introduce",
      guideTitle: "Introduce the problem",
      disc: [
        "Explain how you encountered the problem you’re trying to solve, and any difficulties that have prevented you from solving it yourself.",
      ],
    },
    {
      title: "expand",
      guideTitle: "Expand on the problem",
      disc: [
        `Show what you’ve tried, tell us what happened, and why it didn’t meet your needs.`,
        "Not all questions benefit from including code, but if your problem is better understood with code you’ve written, you should include a minimal, reproducible example.",
        "Please make sure to post code and errors as text directly to the question (and not as images), and format them appropriately.",
      ],
    },
    {
      title: "tags",
      guideTitle: "Adding tags",
      disc: [
        `Tags help ensure that your question will get attention from the right people.`,
        "Tag things in more than one way so people can find them more easily. Add tags for product lines, projects, teams, and the specific technologies or languages used.",
      ],
    },
  ];
  const viewData = guide.filter((v) => v.title === title)[0];
  return (
    <InputGuideComponent disabled={disabled}>
      <h2>{viewData.guideTitle}</h2>
      <div>
        <div className="icon">
          <BsPencil />
        </div>
        <div className="disc">
          {viewData.disc.map((v) => (
            <div>{v}</div>
          ))}
        </div>
      </div>
    </InputGuideComponent>
  );
}

export default InputGuide;
