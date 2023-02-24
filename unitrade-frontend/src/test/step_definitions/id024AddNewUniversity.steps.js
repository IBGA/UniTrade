export const addNewUniversitySteps = ({ given, and, when, then }) => {
  and(/^user has filled in university name (.*)$/, (arg0) => {
    assert.fail('Not implemented yet');
  });

  and(/^user has filled in university city (.*)$/, (arg0) => {
    assert.fail('Not implemented yet');
  });

  and(/^user has filled in university address (.*)$/, (arg0) => {
    assert.fail('Not implemented yet');
  });

  and(/^user has filled in university postcode (.*)$/, (arg0) => {
    assert.fail('Not implemented yet');
  });

  and(/^user has filled in university country (.*)$/, (arg0) => {
    assert.fail('Not implemented yet');
  });

  and(/^user has filled in university website (.*)$/, (arg0) => {
    assert.fail('Not implemented yet');
  });

  then(
    /^the new university with name (.*), city (.*), address (.*), postcode (.*), country (.*) and website (.*) is added to the system$/,
    (arg0, arg1, arg2, arg3, arg4, arg5) => {
      assert.fail('Not implemented yet');
    }
  );
};
