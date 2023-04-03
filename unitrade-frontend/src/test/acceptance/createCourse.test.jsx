import { loadFeature, defineFeature } from 'jest-cucumber';
import { beforeEach } from 'vitest';
import TestRenderer from 'react-test-renderer';
import { CreateCourse } from '../../components/CreateCourse';
import { GET, LOGIN, POST } from '../../utils/client';
import accessBackend from '../../utils/testUtils';
import ErrorToast from '../../components/toasts/ErrorToast';
import { expect } from 'vitest';

const feature = loadFeature('../features-untested/ID125_Create_course.feature');

let testRenderer;
let testInstance;

let form;

let allInputs;
let universityNameInput;
let universityCityInput;
let codeNameInput;
let titleInput;
let descriptionInput;
let universityName = '';
let universityCity = '';
let codeName = '';
let title = '';
let description = '';

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
    codeNameInput = allInputs[2];
    titleInput = allInputs[3];
    descriptionInput = allInputs[4];

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

   /* and (
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
      ); */

      and(
        /^a university with name (.*) and city (.*) already exists in the system$/,
        async (arg0, arg1) => {
          await accessBackend(defaultUser, async () => {
            await POST('university', {
              universityName: arg0,
              universityCity: arg1,
              
            });
            let universities = await GET('university');
          });
        }
      );
  

    and (
      /^user is a moderator for university with name (.*) and city (.*)$/,
      async (arg0, arg1) => {

      }
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
                if (course.codeName == arg1){
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
        title = arg1;
        codeName = arg2;
        description = arg3;

        universityNameInput.props.onChange({target: {value: universityName}});
        titleInput.props.onChange({target: {value: title}});
        codeNameInput.props.onChange({target: {value: codeName}});
        descriptionInput.props.onChange({target: {value: description}});

        await form.props.onSubmit({ preventDefault: () => {}}); 
      }
      );

      then (
        /^a new course for university (.*) with title (.*), codename (.*), and description (.*) is added to the system$/,
        async (arg0, arg1, arg2, arg3) => {
          await accessBackend(defaultUser, async () => {
            let courses = await GET('course');
            let course = courses[course.length-1];
         //   await expect(course.universityName).toBe(arg0);
            await expect(course.title).toBe(arg1);
            await expect(course.codeName).toBe(arg2);
            await expect(course.description).toBe(arg3)
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
      async (arg0, arg1) => {

      }
    ); // not sure how to implement this yet


   and(
    /^a course for university (.*) with codename (.*) already exists in the system$/,
    async (arg0, arg1) => {
      await accessBackend(defaultUser, async () => {
        await POST('course', {
          universityName: arg0,
          codeName: arg1,

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
          title = arg1;
          codeName = arg2;
          description = arg3;

         universityNameInput.props.onChange({target: {value: universityName}});
          titleInput.props.onChange({target: {value: title}});
          codeNameInput.props.onChange({target: {value: codeName}});
          descriptionInput.props.onChange({target: {value: description}});

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