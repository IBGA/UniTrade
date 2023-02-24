export const addNewCourseSteps = ({ given, and, when, then }) => {
  and(/^user has filled in course name (.*)$/, (arg0) => {
    assert.fail('Not implemented yet');
  });

  and(/^user has filled in course code (.*)$/, (arg0) => {
    assert.fail('Not implemented yet');
  });

  and(/^user has filled in course description (.*)$/, (arg0) => {
    assert.fail('Not implemented yet');
  });

  and(/^user has filled in course university (.*)$/, (arg0) => {
    assert.fail('Not implemented yet');
  });

  and(/^user has filled in course faculty (.*)$/, (arg0) => {
    assert.fail('Not implemented yet');
  });

  and(/^user has filled in course department (.*)$/, (arg0) => {
    assert.fail('Not implemented yet');
  });

  then(
    /^the new course with name (.*), code (.*), description (.*), university (.*), faculty (.*), and department (.*) is added to the system$/,
    (arg0, arg1, arg2, arg3, arg4, arg5) => {
      assert.fail('Not implemented yet');
    }
  );
};
