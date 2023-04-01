import { loadFeature, defineFeature } from 'jest-cucumber';
import { beforeEach } from 'vitest';
import TestRenderer from 'react-test-renderer';
import { CreateUniversity } from '../../components/CreateUniversity';
import { GET, LOGIN, POST, DELETE } from '../../utils/client';
import accessBackend from '../../utils/testUtils';
import ErrorToast from '../../components/toasts/ErrorToast';
import { expect } from 'vitest';

const feature = loadFeature('../features/ID024_Add_new_university.feature');

let testRenderer;
let testInstance;

let form;

let allInputs;
let nameInput;
let cityInput;
let descriptionInput;
let name = '';
let city = '';
let description = '';

let error = ''
let universityCount = 0;

let defaultUser = {
  email: 'default@user.com',
  username: 'default@user.com',
  firstName: 'Default',
  lastName: 'User',
  password: 'DefaultUser'
}

defineFeature(feature, (test) => {
  beforeEach(() => {
    error = '';
    testRenderer = TestRenderer.create(<CreateUniversity />);
    testInstance = testRenderer.root;

    form = testInstance.findByType('form');

    allInputs = testInstance.findByType('form').findAllByType('input');
    nameInput = allInputs[0];
    cityInput = allInputs[1];
    descriptionInput = allInputs[2];
  });

  afterEach( async() => {
    await accessBackend(defaultUser, async () => {
        let universities = await GET('university', true);
        // Remove last university created
        if (universities.length > 0){
            await DELETE(`university/${universities[universities.length-1].id}`, true);
        }
    });
  });

  test('Fields are filled in correctly (Normal Flow)', ({
    given,
    and,
    when,
    then,
  }) => {
    given('user is logged in', async () => {
      await POST('person', defaultUser, false);
      await LOGIN(defaultUser.email, defaultUser.password);
    });

    and(
      /^a university with name (.*) and city (.*) does not already exist in the system$/,
      async (arg0, arg1) => {
        await accessBackend(defaultUser, async () => {
          let universities = await GET('university', true);
          arg0 = arg0.replace(/["]+/g, '');
          arg1 = arg1.replace(/["]+/g, '');
          console.log('universities is:')
          console.log(universities);
          universities.forEach((university) => {
            if (university.name === arg0 && university.city === arg1) {
              error = 'University already exists';
              assert.fail(error);
            }
          });
        })}
    );

    when(
      /^user attempts to create a university with name (.*), city (.*), and description (.*)$/,
      async (arg0, arg1, arg2) => {
        name = arg0;
        city = arg1;
        description = arg2;

        nameInput.props.onChange({ target: { value: name } });
        cityInput.props.onChange({ target: { value: city } });
        descriptionInput.props.onChange({ target: { value: description } });

        await form.props.onSubmit({ preventDefault: () => {} });
      }
    );

    then(
      /^a new university with name (.*), city (.*), and description (.*) is added to the system$/,
      async (arg0, arg1, arg2) => {
          await accessBackend(defaultUser, async () => {
          let universities = await GET('university', true);
          let found = false
          console.log('universities 2 is:')
          console.log(universities);
          universities.forEach((university) => {
            if (university.name === arg0 && university.city === arg1 && university.description === arg2) {
                found = true
            }
          })
          await expect(found).toBe(true);
      })}
    );
  });

  test('University already exists  (Error Flow)', ({
    given,
    and,
    when,
    then,
  }) => {
    given('user is logged in', async () => {
      await POST('person', defaultUser, false);
      await LOGIN(defaultUser.email, defaultUser.password);
    });

    and(
      /^a university with name (.*) and city (.*) already exists in the system$/,
      async (arg0, arg1) => {
        await accessBackend(defaultUser, async () => {
          await POST('university', {
            name: arg0,
            city: arg1,
            description: 'Test University',
          });
          let universities = await GET('university', true);
          universityCount = universities.length;
        });
      }
    );

    when(
      /^user attempts to create a university with name (.*), city (.*), and description (.*)$/,
      async (arg0, arg1, arg2) => {
        try {
          name = arg0;
          city = arg1;
          description = arg2;
  
          nameInput.props.onChange({ target: { value: name } });
          cityInput.props.onChange({ target: { value: city } });
          descriptionInput.props.onChange({ target: { value: description } });
  
          await form.props.onSubmit({ preventDefault: () => {} });
        }
        catch (err) {
          error = err;
        }
      }
    );

    then('an error is thrown', async () => {
      await setTimeout(() => {
        expect(testInstance.findByType(ErrorToast).props.show).toBe(true);
    }, 1000);
    });

    and(
      /^a new university with name (.*), city (.*), and description (.*) is not added to the system$/,
      async (arg0, arg1, arg2) => {
        await accessBackend(defaultUser, async () => {
          let universities = await GET('university', true);
          arg0 = arg0.replace(/["]+/g, '');
          arg1 = arg1.replace(/["]+/g, '');
          arg2 = arg2.replace(/["]+/g, '');
          let found = false
          universities.forEach((university) => {
            if (university.name === arg0 && university.city === arg1 && university.description === arg2) {
                found = true
            }
          })
          expect(found).toBe(false);
        })}
    );
  });
});
