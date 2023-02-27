import { loadFeature, defineFeature } from 'jest-cucumber';
import { beforeEach } from 'vitest';
import TestRenderer from 'react-test-renderer';
import { CreateUniversity } from '../../components/CreateUniversity';
import { get, post } from '../../utils/client';

const feature = loadFeature('../features/ID024_Add_new_university.feature');

let testRenderer = TestRenderer.create(<CreateUniversity />);
let testInstance = testRenderer.root;

let form = testInstance.findByType('form');

let allInputs = testInstance.findByType('form').findAllByType('input');
let nameInput = allInputs[0];
let cityInput = allInputs[1];
let descriptionInput = allInputs[2];

let name = '';
let city = '';
let description = '';

let error = ''

defineFeature(feature, (test) => {
  beforeEach(() => {


  });

  test('Fields are filled in correctly (Normal Flow)', ({
    given,
    and,
    when,
    then,
  }) => {
    given('user is logged in', () => {});

    and(
      /^a university with name (.*) and city (.*) does not already exist in the system$/,
      async (arg0, arg1) => {
        let universities = await get('university');
        universities.forEach((university) => {
          if (university.name === arg0 && university.city === arg1) {
            error = 'University already exists';
            assert.fail(error);
          }
        });
      }
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
        let universities = await get('university');
        console.log(universities)
        let university = universities[universities.length - 1];
        expect(university.name).toBe(arg0);
        expect(university.city).toBe(arg1);
        expect(university.description).toBe(arg2);
      }
    );
  });

  test('University already exists  (Error Flow)', ({
    given,
    and,
    when,
    then,
  }) => {
    given('user is logged in', () => {});

    and(
      /^a university with name (.*) and city (.*) already exists in the system$/,
      (arg0, arg1) => {}
    );

    when(
      /^user attempts to create a university with name (.*), city (.*), and description (.*)$/,
      (arg0, arg1, arg2) => {}
    );

    then('an error is thrown', () => {});

    and(
      /^a new university with name (.*), city (.*), and description (.*) is not added to the system$/,
      (arg0, arg1, arg2) => {}
    );
  });
});
