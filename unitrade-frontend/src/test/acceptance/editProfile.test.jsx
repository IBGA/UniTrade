import { loadFeature, defineFeature } from 'jest-cucumber';
import { beforeEach } from 'vitest';
import TestRenderer from 'react-test-renderer';
import { CreateUniversity } from '../../components/CreateUniversity';
import { GET, LOGIN, POST } from '../../utils/client';
import accessBackend from '../../utils/testUtils';
import ErrorToast from '../../components/toasts/ErrorToast';
import { EditProfilePage } from '../../pages/EditProfilePage';
import { AuthProvider } from '../../components/AuthProvider';
import { expect } from 'vitest';

const feature = loadFeature('../features/ID126_Edit_profile.feature');

let testRenderer;
let testInstance;

let form;

let allInputs;
let profilePictureInput;
let firstNameInput;
let lastNameInput;
let universitySelect;
let courseInputs;

let error = '';
let universityCount = 0;

let defaultUser = {
  email: 'default@user.com',
  username: 'default@user.com',
  firstName: 'Default',
  lastName: 'User',
  password: 'DefaultUser',
};

defineFeature(feature, (test) => {
  beforeEach(async () => {
    error = '';

    await POST('person', defaultUser, false);

    testRenderer = await TestRenderer.create(
      <AuthProvider>
        <EditProfilePage />
      </AuthProvider>
    );

    while (form == null) {
      try {
        testInstance = testRenderer.root;
        await LOGIN(defaultUser.email, defaultUser.password);
        form = testInstance.findByType('form');

        allInputs = testInstance.findByType('form').findAllByType('input');
        profilePictureInput = allInputs[0];
        firstNameInput = allInputs[1];
        lastNameInput = allInputs[2];
        universitySelect = form.findByType('select');
        courseInputs = form.findByProps({ className: 'mb-3 checkboxes' });
      } catch (e) {
        continue;
      }
    }
  });

  test('User edits profile information successfully (Normal Flow)', ({
    given,
    and,
    then,
  }) => {
    given('user is logged in', () => {});

    and(
      /^user edits profile information with profile picture (.*), university with name (.*) and city (.*), and enrolled course with codename (.*)$/,
      async (arg1, arg2, arg3, arg4) => {
        let arg0 = 'editProfile' + arg3;

        let course;
        let uni;

        await accessBackend(defaultUser, async () => {
          uni = await POST('university', {
            name: arg2,
            city: arg3,
            description: 'Test University',
          });

          let universities = await GET('university');

          course = await POST('course', {
            universityId: uni.id,
            codename: arg4,
            title: 'Test Course',
            description: 'Test Course',
          });

          await POST(
            'person',
            {
              email: arg0 + '@email.com',
              username: arg0,
              firstName: 'Default',
              lastName: 'User',
              password: '1234',
            },
            false
          );
        });

        LOGIN(arg0 + '@email.com', '1234');

        profilePictureInput.props.onChange({ target: { value: arg1 } });
        firstNameInput.props.onChange({ target: { value: arg0 } });
        lastNameInput.props.onChange({ target: { value: arg0 } });
        universitySelect.props.onChange({ target: { value: uni.id } });

        courseInputs.props.onChange({ target: { value: [course.id] } });

        await form.props.onSubmit({ preventDefault: () => {} });
      }
    );

    then(
      /^user's profile information is updated with profile picture (.*), university with name (.*) and city (.*), and enrolled course with codename (.*)$/,
      async (arg1, arg2, arg3, arg4) => {
        let arg0 = 'editProfile' + arg3;

        let person;

        await accessBackend(defaultUser, async () => {
          person = await GET('person/self');
        });

        expect(person.username).toBe(arg0);
        expect(person.profilePicture).toBe(arg1);
        expect(person.university.name).toBe(arg2);
        expect(person.university.city).toBe(arg3);
        expect(person.enrolledCourses[0].codename).toBe(arg4);
      }
    );
  });
});
