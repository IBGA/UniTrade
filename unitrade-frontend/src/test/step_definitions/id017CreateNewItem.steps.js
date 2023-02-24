export const createNewItemSteps = ({ given, and, when, then }) => {
  and('user is on the create new item posting page', () => {
    assert.fail('Not implemented yet');
  });

  when(
    /^user has not found the university (.*) or course (.*) from the dropdown menu and clicks "(.*)"$/,
    (arg0, arg1, arg2) => {
      assert.fail('Not implemented yet');
    }
  );

  then(
    /^user is prompted to add a new university or course to the system with the fields prefilled with (.*) or (.*)$/,
    (arg0, arg1) => {
      assert.fail('Not implemented yet');
    }
  );

  and(
    /^user has found and selected the university (.*) from the dropdown menu$/,
    (arg0) => {
      assert.fail('Not implemented yet');
    }
  );

  and(
    /^user has found and selected the course (.*) from the dropdown menu$/,
    (arg0) => {
      assert.fail('Not implemented yet');
    }
  );

  and(/^user has filled in the item category (.*)$/, (arg0) => {
    assert.fail('Not implemented yet');
  });

  and(/^user has filled in the item name (.*)$/, (arg0) => {
    assert.fail('Not implemented yet');
  });

  and(/^user has filled in the item description (.*)$/, (arg0) => {
    assert.fail('Not implemented yet');
  });

  and(/^user has appended the item image (.*)$/, (arg0) => {
    assert.fail('Not implemented yet');
  });

  and(/^user has filled in the item condition (.*)$/, (arg0) => {
    assert.fail('Not implemented yet');
  });

  and(/^user has filled in the item price (.*)$/, (arg0) => {
    assert.fail('Not implemented yet');
  });

  and(/^user has filled in the item quantity (.*)$/, (arg0) => {
    assert.fail('Not implemented yet');
  });

  when('user clicks the create posting button', () => {
    assert.fail('Not implemented yet');
  });

  then(
    /^the item posting of item with name (.*), item description (.*), item image (.*), item condition (.*), item price (.*), item quantity (.*), item category (.*), is created and tagged with university (.*) and course (.*)$/,
    (arg0, arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8) => {
      assert.fail('Not implemented yet');
    }
  );
  and(/^user has filled in at least one field (.*) incorrectly$/, (arg0) => {
    assert.fail('Not implemented yet');
  });
};
