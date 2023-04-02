import { loadFeature, defineFeature } from 'jest-cucumber';
import { beforeEach } from 'vitest';
import TestRenderer from 'react-test-renderer';
import { CreateCourse } from '../../components/CreateCourse';
import { GET, LOGIN, POST } from '../../utils/client';
import accessBackend from '../../utils/testUtils';
import ErrorToast from '../../components/toasts/ErrorToast';
import { expect } from 'vitest';

const feature = loadFeature('../features/ID125_Create_course.feature');

let testRenderer;
let testInstance;

let form;

let allInputs;
let universityNameInput;
let universityCityInput;
let courseCodeNameInput;
let courseTitleInput;
let courseDescriptionInput;
let universityName = '';
let universityCity = '';
let courseCodeName = '';
let courseTitle = '';
let courseDescription = '';

let error = ''
let courseCount = 0;

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
    testRenderer = TestRenderer.create(<CreateCourse />);
    testInstance = testRenderer.root;

    form = testInstance.findByType('form');

    allInputs = testInstance.findByType('form').findAllByType('input');
    universityNameInput = allInputs[0]; //fix
    universityCityInput = allInputs[1];
    courseCodeNameInput = allInputs[2];
    courseTitleInput = allInputs[3];
    courseDescriptionInput = allInputs[4];

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

    and (
      /^a university with name (.*) and city (.*) exists in the system$/,
      async (arg0, arg1) => {
        universityName = arg0;
        universityCity = arg1;
        await accessBackend(defaultUser, async () => {
          let universities = await GET('university');
          universities.forEach((university) => {
            if (universities.includes(arg0, arg1) == false) {
              error = 'Univerisity does not exist';
              assert.fail(error);
            }
          })
        })
      }
      );

    and (
      /^user is a moderator for university with name (.*) and city (.*)$/,
    ); // not sure how to implement this yet

    and (
      /^a course for university (.*) with codename (.*) does not already exist in the system$/,
      async (arg0, arg1) => {
        await accessBackend(defaultUser, async () => {
          let universities = await GET('university');
          universities.forEach(async (university) => {
            if (university.name = arg0){
              let courses = await GET('course');
              courses.forEach((course) => {
                if (course.courseCodeName == arg1){
                  error = 'Course already exists';
                  assert.fail(error);
                }
              })
            }
          })
        })

      }
    );

    when (
      /^user attempts to create a course for university (.*) with title (.*), codename (.*), and description (.*)$/,
      async(arg0, arg1, arg2, arg3) => {
        universityName = arg0;
        courseTitle = arg1;
        courseCodeName = arg2;
        courseDescription = arg3;

        universityNameInput.props.onChange({target: {value: universityName}});
        courseTitleInput.props.onChange({target: {value: courseTitle}});
        courseCodeNameInput.props.onChange({target: {value: courseCodeName}});
        courseDescriptionInput.props.onChange({target: {value: courseDescription}});

        await form.props.onSubmit({ preventDefault: () => {}});
      }
      );

      then (
        /^a new course for university (.*) with title (.*), codename (.*), and description (.*) is added to the system$/,
        async (arg0, arg1, arg2, arg3) => {
          await accessBackend(defaultUser, async () => {
            let courses = await GET('course');
            let course = courses[course.length-1];
            await expect(course.universityName).toBe(arg0);
            await expect(course.courseTitle).toBe(arg1);
            await expect(course.courseCodeName).toBe(arg2);
            await expect(course.courseDescription).toBe(arg3)
          });
        }
        );


  });

  test('Course already exists', ({
    given,
    and,
    when,
    then,
  }) => {
    given('user is logged in', async() => {
      await POST('person', defaultUser);
      await LOGIN(defaultUser.email, deafultUser.password);

    });

    and (
      /^a university with name (.*) and city (.*) exists in the system$/,
      async (arg0, arg1) => {
        universityName = arg0;
        universityCity = arg1;
        await accessBackend(defaultUser, async () => {
          let universities = await GET('university');
          universities.forEach((university) => {
            if (universities.includes(arg0, arg1) == false) {
              error = 'Univerisity does not exist';
              assert.fail(error);
            }
          })
        })
      }
      );

    and (
      /^user is a moderator for university with name (.*) and city (.*)$/,
      async (arg0, arg1) => {
        
      }
    ); // not sure how to implement this yet


   and(
    /^a course for university (.*) with codename (.*) does not already exist in the system$/,
    async (arg0, arg1) => {
      await accessBackend(defaultUser, async () => {
        await POST('course', {
          universityName: arg0,
          courseCodeName: arg1,

        });
        let courses = await GET('course');
        courseCount = courses.length;
      });
    }
   );

   when(
    /^user attempts to create a course for university (.*) with title (.*), codename (.*), and description (.*)$/,
      async(arg0, arg1, arg2, arg3) => {
        try {
          universityName = arg0;
          courseTitle = arg1;
          courseCodeName = arg2;
          courseDescription = arg3;

         universityNameInput.props.onChange({target: {value: universityName}});
          courseTitleInput.props.onChange({target: {value: courseTitle}});
          courseCodeNameInput.props.onChange({target: {value: courseCodeName}});
          courseDescriptionInput.props.onChange({target: {value: courseDescription}});

        await form.props.onSubmit({ preventDefault: () => {}});
        }
        catch(err){
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
    /^a new course for university (.*) with title (.*), codename (.*), and description (.*) is not added to the system$/,
    async (arg0, arg1, arg2, arg3) =>{
      await accessBackend(defaultUser, async () => {
        let courses = await GET('course');
        expect(courses.length).toBe(courseCount);
      })
    }
  )


  }
    )
});