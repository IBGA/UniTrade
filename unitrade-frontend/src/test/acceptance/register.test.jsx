import { loadFeature, defineFeature } from 'jest-cucumber';
import { beforeAll, beforeEach, assert, expect } from 'vitest';
import TestRenderer from 'react-test-renderer';
import { GET, POST, LOGIN, LOGOUT, getLoginStatus, DELETE } from '../../utils/client';
import { SignupPage } from '../../pages/SignupPage';
import accessBackend from '../../utils/testUtils';
import ErrorToast from '../../components/toasts/ErrorToast';

const feature = loadFeature('../features/ID015_Register_user_account.feature');
let testRenderer = TestRenderer.create(<SignupPage />);
let testInstance = testRenderer.root;
let form;
let firstNameInput;
let lastNameInput;
let emailInput;
let usernameInput;
let passwordInput;
let error;

let defaultUser = {
  email: 'default@user.com',
  username: 'default@user.com',
  firstName: 'Default',
  lastName: 'User',
  password: 'DefaultUser',
};

defineFeature(feature, (test) => {
  beforeAll(async () => {
    // Create a default user
    await POST('person', defaultUser, false);
  });

  beforeEach(async () => {
    // Rerender everything
    testRenderer = TestRenderer.create(<SignupPage />);
    testInstance = testRenderer.root;

    form = testInstance.findByType('form');
    let allInputs = testInstance.findByType('form').findAllByType('input');

    firstNameInput = allInputs[0];
    lastNameInput = allInputs[1];
    usernameInput = allInputs[2];
    emailInput = allInputs[3];
    passwordInput = allInputs[4];
  });

  test('User email and username does not already exist in system. (Normal Flow)', ({
    given,
    and,
    when,
    then,
  }) => {
    given('user is not logged in', () => {
      LOGOUT();
    });

    and(
      /^a user with email (.*) or username (.*) does not already exist in the system$/,
      async (arg0, arg1) => {
        await accessBackend(defaultUser, async () => {
          let res = await GET(`person/exists/${arg0}`, false)
          if (res)
            assert.fail('User with email already exists');
        });
      }
    );

    when(
      /^user registers with email (.*), username (.*), first name (.*), last name (.*) and password (.*)$/,
      async (arg0, arg1, arg2, arg3, arg4) => {
        emailInput.props.onChange({ target: { value: arg0 } });
        usernameInput.props.onChange({ target: { value: arg1 } });
        firstNameInput.props.onChange({ target: { value: arg2 } });
        lastNameInput.props.onChange({ target: { value: arg3 } });
        passwordInput.props.onChange({ target: { value: arg4 } }); //

        await form.props.onSubmit({ preventDefault: () => {} });
      }
    );

    then(
      /^a new user account with email (.*), username (.*), first name (.*), last name (.*) and password (.*) is created$/,
      async (arg0, arg1, arg2, arg3, arg4) => {
        await accessBackend(defaultUser, async () => {
          let person = await GET(`person/username/${arg1}`, false);
          expect(person.email).toBe(arg0);
          expect(person.username).toBe(arg1);
          expect(person.firstName).toBe(arg2);
          expect(person.lastName).toBe(arg3);

        });

        // Need to get session token of user that was created
        let userToDelete = {
            email: arg0,
            password: arg4
        }
        await accessBackend(userToDelete, async() => {
          // Cleanup
          await DELETE(`person`, true);
        })
      }
    );
  });

  test('User email or username already exists in system. (Error Flow)', ({
    given,
    and,
    when,
    then,
  }) => {
    given('user is not logged in', () => {
      LOGOUT();
    });

    and(
      /^a user with email (.*) or username (.*) already exists in the system$/,
      async (arg0, arg1) => {
        await accessBackend(defaultUser, async () => {
          let person = await GET(`person/username/${arg0}`);
          if (person == null) {
            POST('person', {
              email: arg0,
              username: arg1,
              firstName: 'Test',
              lastName: 'Test',
              password: 'Test',
            });
          }
        });
      }
    );

    when(
      /^user registers with email (.*), username (.*), first name (.*), last name (.*) and password (.*)$/,
      async (arg0, arg1, arg2, arg3, arg4) => {
        emailInput.props.onChange({ target: { value: arg0 } });
        usernameInput.props.onChange({ target: { value: arg1 } });
        firstNameInput.props.onChange({ target: { value: arg2 } });
        lastNameInput.props.onChange({ target: { value: arg3 } });
        passwordInput.props.onChange({ target: { value: arg4 } });

        await form.props.onSubmit({ preventDefault: () => {} });
      }
    );

    then(
      /^an error is thrown to create a new user account with email (.*), username (.*), first name (.*), last name (.*) and password (.*)$/, 
      async (arg0, arg1, arg2, arg3, arg4) => {
        // wait some time for the toast to appear
        await setTimeout(() => {
            expect(testInstance.findByType(ErrorToast).props.show).toBe(true);
        }, 10000);

        // Need to get session token of user that was created
        let userToDelete = {
          email: arg0,
          password: arg4
        }
        await accessBackend(userToDelete, async() => {
          // Cleanup
          await DELETE(`person`, true);
        })
    });
  });
});
