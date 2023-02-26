import TestRenderer from 'react-test-renderer';
import { expect } from 'vitest';
import { CreateUniversity } from '../../components/CreateUniversity';
import {get, post} from '../../utils/client';

export const addUniversitySteps = ({ given, when, then, and }) => {
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


  and(
    /^a university with name (.*) and city (.*) does not already exist in the system$/,
    (arg0, arg1) => {
      let universities = get('university');
      let university = universities.find((university) => {
        return university.name === arg0 && university.city === arg1;
      });

      expect(university).toBe(undefined);
    }
  );

  when(
    /^user attempts to create a university with name (.*), city (.*), and description (.*)$/,
    (arg0, arg1, arg2) => {
      let name = arg0;
      let city = arg1;
      let description = arg2;

      nameInput.props.onChange({ target: { value: name } });
      cityInput.props.onChange({ target: { value: city } });
      descriptionInput.props.onChange({ target: { value: description } });

      try {
        form.props.onSubmit({ preventDefault: () => {} });
      } catch (e) {
        error = e;
      }
    }
  );

  and(
    /^a university with name (.*) and city (.*) already exists in the system$/,
    (arg0, arg1) => {
      let name = arg0;
      let city = arg1;

      nameInput.props.onChange({ target: { value: name } });
      cityInput.props.onChange({ target: { value: city } });
      descriptionInput.props.onChange({ target: { value: "" } });

      
      form.props.onSubmit({ preventDefault: () => {} }));
    }
  );

  then(
    /^a new university with name (.*), city (.*), and description (.*) is added to the system$/,
    async (arg0, arg1, arg2) => {
      let universities = await get('university');
      let university = universities[universities.length - 1];
      expect(university.name).toBe(arg0);
      expect(university.city).toBe(arg1);
      expect(university.description).toBe(arg2);
    }
  );

  and(
    /^a new university with name (.*), city (.*), and description (.*) is not added to the system$/,
    async (arg0, arg1, arg2) => {
      let universities = await get('university');
      let university = universities[universities.length - 1];
      if (university == null) return;

      expect(university.name).not.toBe(arg0);
      expect(university.city).not.toBe(arg1);
      expect(university.description).not.toBe(arg2);
    }
  );
};
