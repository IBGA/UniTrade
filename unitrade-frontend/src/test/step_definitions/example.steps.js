export const exampleSteps = ({ given, when, then }) => {
  given('the sky is blue', () => {});

  given('the grass is green', () => {});

  given(/^the following people are available (.*)$/, (people) => {});

  when(/^we go to the place (.*)$/, (place) => {});

  then('we have fun', () => {});

  given('Steve is available', () => {});

  when('we go to the mines', () => {});

  then('we do not have fun', () => {});

  when(/^we eat (.*) in (.*)$/, (food, place) => {});
};

/*
If scenarios: Just have it be a string
If scenario outlines: It needs to be regex like above
*/
