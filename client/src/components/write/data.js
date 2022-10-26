export default {
  inputData: [
    {
      title: 'title',
      name: 'Title',
      disc: `Be specific and imagine you're asking a question to another
          person.`,
      placeholder:
        'e.g Is there an R function for finding the index of an element in a vector?',
    },
    {
      title: 'introduce',
      name: 'What are the details of your problem?',
      disc: `Describe what you tried, what you expected to happen, and what
            actually resulted. Minimum 20 characters.`,
      editor: true,
    },
    {
      title: 'expand',
      name: 'What did you try and what were you expecting?',
      disc: `Introduce the prolem and expand on what you put in the title.
            Minimum 20 characters.`,
      editor: true,
    },
    {
      title: 'tags',
      name: 'Tags',
      disc: `Add up to 5tags to describe what your question is about. Start
            typing to see suggestions.`,
      placeholder:
        'e.g Is there an R function for finding the index of an element in a vector?',
    },
  ],
  guideData: [
    {
      title: 'title',
      guideTitle: 'Writing a good title',
      disc: [
        'Your title should summarize the problem.',
        'You might find that you have a better idea of your title after writing out the rest of the question.',
      ],
    },
    {
      title: 'introduce',
      guideTitle: 'Introduce the problem',
      disc: [
        'Explain how you encountered the problem you’re trying to solve, and any difficulties that have prevented you from solving it yourself.',
      ],
    },
    {
      title: 'expand',
      guideTitle: 'Expand on the problem',
      disc: [
        `Show what you’ve tried, tell us what happened, and why it didn’t meet your needs.`,
        'Not all questions benefit from including code, but if your problem is better understood with code you’ve written, you should include a minimal, reproducible example.',
        'Please make sure to post code and errors as text directly to the question (and not as images), and format them appropriately.',
      ],
    },
    {
      title: 'tags',
      guideTitle: 'Adding tags',
      disc: [
        `Tags help ensure that your question will get attention from the right people.`,
        'Tag things in more than one way so people can find them more easily. Add tags for product lines, projects, teams, and the specific technologies or languages used.',
      ],
    },
  ],
};
