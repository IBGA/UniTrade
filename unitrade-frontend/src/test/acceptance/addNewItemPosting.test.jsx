import { loadFeature, defineFeature } from 'jest-cucumber';
import { beforeEach } from 'vitest';
import TestRenderer from 'react-test-renderer';
import { CreateItemPosting } from '../../components/CreateItemPosting';
import { AuthProvider } from '../../components/AuthProvider';
import { GET, LOGIN, POST, DELETE } from '../../utils/client';
import accessBackend from '../../utils/testUtils';
import ErrorToast from '../../components/toasts/ErrorToast';
import { expect } from 'vitest';

const feature = loadFeature('../features/ID017_Create_new_item_posting.feature');

let testRenderer;
let testInstance;

let form;

let allInputs;
let titleInput;
let descriptionInput;
let imageLinkInput;
let universityIdInput;
let courseIdsInput;
let priceInput;
let title;
let description;
let imageLink;
let universityName;
let universityCity;
let courseCodename;
let price;

let error = ''

let defaultUser = {
    email: 'default@user.com',
    username: 'default@user.com',
    firstName: 'Default',
    lastName: 'User',
    password: 'DefaultUser'
}

// Mock navigate function,
vi.mock('react-router-dom', () => ({
    useNavigate: vi.fn(),
}));

defineFeature(feature, (test) => {
    beforeEach(async() =>{
        await POST('person', defaultUser, false);

        error = '';
        testRenderer = TestRenderer.create(
            <AuthProvider>
                <CreateItemPosting />
            </AuthProvider>
        );
        testInstance = testRenderer.root;
        error = ''

        while (form == null) {
            try {
                await LOGIN(defaultUser.email, defaultUser.password);
                form = testInstance.findByType('form');
        
                allInputs = testInstance.findByType('form').findAllByType('input');
                let allSelects = testInstance.findByType('form').findAllByType('select');
                let allTextAreas = testInstance.findByType('form').findAllByType('textarea');

                titleInput = allInputs[0];
                descriptionInput = allTextAreas[0];
                imageLinkInput = allInputs[1];
                universityIdInput = allSelects[0];
                courseIdsInput = allSelects[1];
                priceInput = allInputs[2];
            } catch (e) {
                continue;
            }
        }

        await accessBackend(defaultUser, async () => {
            let university = await POST('university', {
                name: 'Test_university',
                city: 'Test_city',
                description: 'Test_description',
            });

            let course = await POST('course', {
                title: 'Test_course',
                codename: 'Test_codename',
                description: 'Test_description',
                universityId: university.id
            });
        });
    });

    afterEach( async() => {
        await accessBackend(defaultUser, async () => {
            let universities = await GET('university');
            // Remove last university created
            if (universities.length > 0) {
                DELETE(`university/${universities[universities.length-1].id}`, true);
            }

            let courses = await GET('course');
            // Remove last course created
            if (courses.length > 0) {
                DELETE(`course/${courses[courses.length-1].id}`, true);
            }
        });
      });

    test('University is found in the system and item posting is created (Normal Flow)', ({
        given,
        and,
        when,
        then,
    }) => {
        given('user is logged in', async() => {
            await POST('person', defaultUser, false);
            await LOGIN(defaultUser.email, defaultUser.password);
        });
        and (
            /^a university with name (.*) and city (.*) already exists in the system$/,
            async (arg0, arg1) => {
                await accessBackend(defaultUser, async () => {
                    let universities = await GET('university');
                    arg0 = arg0.replace(/["]+/g, '');
                    arg1 = arg1.replace(/["]+/g, '');
                    let found = false;
                    universities.forEach((university) => {
                        if (university.name === arg0 && university.city === arg1){
                            found = true;
                        }
                    });
                    if (!found) {
                        error = 'University does not exist';
                        assert.fail(error);
                    }
                })
            }
        );
        and (
            /^a course with codename (.*) already exists in the system$/,
            async (arg0) => {
                await accessBackend(defaultUser, async () => {
                    let courses = await GET('course');
                    arg0 = arg0.replace(/["]+/g, '');
                    let found = false;
                    courses.forEach((course) => {
                        if (course.codename === arg0){
                            found = true;
                        }
                    });
                    if (!found) {
                        error = 'Course does not exist';
                        assert.fail(error);
                    }
                })
            }
        );

        when(
            /^user attempts to create a item posting with title (.*), description (.*), imagelink (.*), university name (.*), university city (.*), course codename (.*), and price (.*)$/,
            async (arg0, arg1, arg2, arg3, arg4, arg5, arg6) => {
                await accessBackend(defaultUser, async () => {
                    title = arg0.replace(/["]+/g, '');
                    description = arg1.replace(/["]+/g, '');
                    imageLink = arg2.replace(/["]+/g, '');
                    universityName = arg3.replace(/["]+/g, '');
                    universityCity = arg4.replace(/["]+/g, '')
                    courseCodename = arg5.replace(/["]+/g, '');
                    price = arg6.replace(/["]+/g, '');

                    // Transform data here
                    let university = await GET(`university/${universityCity}/${universityName}`, true)
                    let course = await GET(`course/codename/${courseCodename}`, true);

                    titleInput.props.onChange({ target: { value: title } });
                    descriptionInput.props.onChange({ target: { value: description } });
                    imageLinkInput.props.onChange({ target: { value: imageLink } });
                    universityIdInput.props.onChange({ target: { value: university.id } });
                    courseIdsInput.props.onChange({ target: { selectedOptions: [{value: course.id }] } });
                    priceInput.props.onChange({ target: { value: price } });
            
                    await form.props.onSubmit({ preventDefault: () => {} });
                })
            }
        );

        then(
            /^a new itemposting with title (.*), description (.*), imagelink (.*), university name (.*), university city (.*), course codename (.*), and price (.*) is added to the system$/,
            async (arg0, arg1, arg2, arg3, arg4, arg5, arg6) => {
                await accessBackend(defaultUser, async () => {
                let itempostings = await GET('itemposting', true);
                let itemposting = itempostings[itempostings.length - 1];
                await expect(itemposting.title).toBe(arg0.replace(/["]+/g, ''));
                await expect(itemposting.description).toBe(arg1.replace(/["]+/g, ''));
                await expect(itemposting.imageLink).toBe(arg2.replace(/["]+/g, ''));

                let university = await GET(`university/${itemposting.university.id}`, true);
                let course = await GET(`course/${itemposting.courses[0].id}`, true);

                await expect(university.name).toBe(arg3.replace(/["]+/g, ''));
                await expect(university.city).toBe(arg4.replace(/["]+/g, ''));
                await expect(course.codename).toBe(arg5.replace(/["]+/g, ''));
                await expect(itemposting.price).toBe(parseInt(arg6.replace(/["]+/g, ''), 10));

                // Cleanup
                DELETE(`itemposting/${itemposting.id}`, true);
            })}

        );
    })

    test('University is not found in the system and item posting is not created (Error Flow)', ({
        given,
        and,
        when,
        then,
    }) => {
        given('user is logged in', async() => {
            await POST('person', defaultUser, false);
            await LOGIN(defaultUser.email, defaultUser.password);
        });
        and (
            /^a university with name (.*) and city (.*) does not already exists in the system$/,
            async (arg0, arg1) => {
                await accessBackend(defaultUser, async () => {
                    let universities = await GET('university', true);
                    arg0 = arg0.replace(/["]+/g, '');
                    arg1 = arg1.replace(/["]+/g, '');
                    universities.forEach((university) => {
                        if (university.name === arg0 && university.city === arg1){
                            error = 'University already exists';
                            assert.fail(error);
                        }
                    });
                })
            }
        );
        and (
            /^a course with codename (.*) does not already exists in the system$/,
            async (arg0) => {
                await accessBackend(defaultUser, async () => {
                    let courses = await GET('course');
                    arg0 = arg0.replace(/["]+/g, '');
                    courses.forEach((course) => {
                        if (course.codename === arg0){
                            error = 'Course already exists';
                            assert.fail(error);
                        }
                    });
                })
            }
        );

        when(
            /^user attempts to create a item posting with title (.*), description (.*), imagelink (.*), university name (.*), university city (.*), course codename (.*), and price (.*)$/,
            async (arg0, arg1, arg2, arg3, arg4, arg5, arg6) => {
                title = arg0;
                description = arg1;
                imageLink = arg2;
                universityName = arg3;
                universityCity = arg4
                courseCodename = arg5;
                price = arg6;

                // Transform data here
                let university = await GET(`university/${universityCity}/${universityName}`, true)
                let course = await GET(`course/codename/${courseCodename}`, true);

                titleInput.props.onChange({ target: { value: title } });
                descriptionInput.props.onChange({ target: { value: description } });
                imageLinkInput.props.onChange({ target: { value: imageLink } });
                universityIdInput.props.onChange({ target: { value: university.id } });
                courseIdsInput.props.onChange({ target: { selectedOptions: [{value: course.id }] } });
                priceInput.props.onChange({ target: { value: price } });
        
                await form.props.onSubmit({ preventDefault: () => {} });
            }
        );

        then(
            'the item posting is not created and an error is thrown',
            async () => {
                await setTimeout(() => {
                    expect(testInstance.findByType(ErrorToast).props.show).toBe(true);
                }, 1000);
            }
        );
    })
})

