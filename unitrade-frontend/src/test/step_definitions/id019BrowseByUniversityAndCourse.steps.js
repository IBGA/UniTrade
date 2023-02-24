export const browseByUniversityAndCourseSteps = ({
  given,
  and,
  when,
  then,
}) => {
  and('user is on the browse page', () => {
    assert.fail('Not implemented yet');
  });

  and(
    /^user finds and selects both a university (.*) and course (.*) from the dropdown menu$/,
    (arg0, arg1) => {
      assert.fail('Not implemented yet');
    }
  );

  when('user clicks the update search button', () => {
    assert.fail('Not implemented yet');
  });

  and(
    /^user does not find the university (.*) or course (.*) they are looking for$/,
    (arg0, arg1) => {
      assert.fail('Not implemented yet');
    }
  );

  then(
    /^a University (.*) {or} course (.*) not found message is displayed$/,
    (arg0, arg1) => {
      assert.fail('Not implemented yet');
    }
  );

  then(
    /^the browse page is updated with items tagged with university (.*) and course (.*)$/,
    (arg0, arg1) => {
      assert.fail('Not implemented yet');
    }
  );
};
