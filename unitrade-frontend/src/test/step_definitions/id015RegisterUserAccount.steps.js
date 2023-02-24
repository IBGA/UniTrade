export const userAccountSteps = ({ given, and, when, then }) => {
  given('user is on the register account page', () => {
    assert.fail('Not implemented yet');
  });

  and(/^user enters a non-existing email address (.*)$/, (arg0) => {
    assert.fail('Not implemented yet');
  });

  and(/^user enters a valid password (.*)$/, (arg0) => {
    assert.fail('Not implemented yet');
  });

  when('user clicks on the register button', () => {
    assert.fail('Not implemented yet');
  });

  then(
    /^user with email (.*) and password (.*) is registered and redirected to the login page$/,
    (arg0, arg1) => {
      assert.fail('Not implemented yet');
    }
  );

  and(
    /^user enters an existing or non existing email address (.*)$/,
    (arg0) => {
      assert.fail('Not implemented yet');
    }
  );

  and(/^user enters an existing email address (.*)$/, (arg0) => {
    assert.fail('Not implemented yet');
  });

  and(/^user enters an invalid password (.*)$/, (arg0) => {
    assert.fail('Not implemented yet');
  });

  then(/^a "(.*)" error message is issued$/, (arg0) => {
    assert.fail('Not implemented yet');
  });
};
