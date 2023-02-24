export const loginSteps = ({ given, and, when, then }) => {
  given('user is on the login page', () => {
    assert.fail('Not implemented yet');
  });

  and(/^user enters existing email address (.*)$/, (arg0) => {
    assert.fail('Not implemented yet');
  });

  and(/^user enters correct password (.*)$/, (arg0) => {
    assert.fail('Not implemented yet');
  });

  when('user clicks login button', () => {
    assert.fail('Not implemented yet');
  });

  then('user is logged in to their account', () => {
    assert.fail('Not implemented yet');
  });

  and(/^user enters incorrect password (.*)$/, (arg0) => {
    assert.fail('Not implemented yet');
  });

  then(/^a "(.*)" error is issued$/, (arg0) => {
    assert.fail('Not implemented yet');
  });

  and(/^user enters a password (.*)$/, (arg0) => {
    assert.fail('Not implemented yet');
  });

  and('user enters non-existing email address', () => {
    assert.fail('Not implemented yet');
  });
};
