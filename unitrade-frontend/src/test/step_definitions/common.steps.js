export const commonSteps = ({ given, and, when, then }) => {
  given('user is logged in', () => {
    assert.fail('Not implemented yet');
  });

  and(/^user is on the "(.*)" page$/, (arg0) => {
    assert.fail('Not implemented yet');
  });

  when(/^user clicks "(.*)" button$/, (arg0) => {
    assert.fail('Not implemented yet');
  });

  then(
    /^a "(.*)" error message is issued and the fields (.*) are highlighted in red$/,
    (arg0, arg1) => {
      assert.fail('Not implemented yet');
    }
  );

  then(
    /^a "(.*)" error message is issued and the (.*) field\(s\) is or are highlighted$/,
    (arg0, arg1) => {
      assert.fail('Not implemented yet');
    }
  );
};
