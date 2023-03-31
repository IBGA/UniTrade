import { loadFeature, defineFeature } from 'jest-cucumber';
import { beforeEach } from 'vitest';
import TestRenderer from 'react-test-renderer';
import { CreateItemPosting } from '../../components/CreateItemPosting';
import { GET, LOGIN, POST } from '../../utils/client';
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
let universityId;
let courseIds;
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
        await LOGIN(defaultUser.email, defaultUser.password);

        error = '';
        testRenderer = TestRenderer.create(<CreateItemPosting />);
        testInstance = testRenderer.root;

        form = testInstance.findByType('form');

        allInputs = testInstance.findByType('form').findAllByType('input');
        titleInput = allInputs[0];
        descriptionInput = allInputs[1];
        imageLinkInput = allInputs[2];
        universityIdInput = allInputs[3];
        courseIdsInput = allInputs[4];
        priceInput = allInputs[5];
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
            /^a university with id (.*) already exists in the system$/,
            async (arg0) => {
                await accessBackend(defaultUser, async () => {
                    let universities = await GET('university');
                    universities.forEach((university) => {
                        if (university.id == arg0){
                            return;
                        }
                    });
                    error = 'University does not exist';
                    assert.fail(error);
                })
            }
        );
        and (
            /^a course with id (.*) already exists in the system$/,
            async (arg0) => {
                await accessBackend(defaultUser, async () => {
                    let courses = await GET('course');
                    courses.forEach((course) => {
                        if (course.id == arg0){
                            return;
                        }
                    });
                    error = 'Course does not exist';
                    assert.fail(error);
                })
            }
        );

        when(
            /^user attempts to create a item posting with title (.*), description (.*), imagelink (.*), universityId (.*), courseIds (.*), and price (.*)$/,
            async (arg0, arg1, arg2, arg3, arg4, arg5) => {
                title = arg0;
                description = arg1;
                imageLink = arg2;
                universityId = arg3;
                courseIds = [arg4];
                price = arg5;
        
                titleInput.props.onChange({ target: { value: title } });
                descriptionInput.props.onChange({ target: { value: description } });
                imageLinkInput.props.onChange({ target: { value: imageLink } });
                universityIdInput.props.onChange({ target: { value: universityId } });
                courseIdsInput.props.onChange({ target: { value: courseIds } });
                priceInput.props.onChange({ target: { value: price } });
        
                await form.props.onSubmit({ preventDefault: () => {} });
            }
        );

        then(
            /^a new itemposting with title (.*), description (.*), imagelink (.*), universityId (.*), courseIds (.*), and price (.*) is added to the system$/,
            async (arg0, arg1, arg2, arg3, arg4, arg5) => {
                await accessBackend(defaultUser, async () => {
                let itempostings = await GET('itemposting');
                let itemposting = itempostings[itempostings.length - 1];
                await expect(itemposting.title).toBe(arg0);
                await expect(itemposting.description).toBe(arg1);
                await expect(itemposting.imageLink).toBe(arg2);
                await expect(itemposting.universityId).toBe(arg3);
                await expect(itemposting.courseIds).toBe(arg4);
                await expect(itemposting.price).toBe(arg5);
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
            /^a university with id (.*) does not already exists in the system$/,
            async (arg0) => {
                await accessBackend(defaultUser, async () => {
                    let universities = await GET('university');
                    universities.forEach((university) => {
                        if (university.id == arg0){
                            error = 'University already exists';
                            assert.fail(error);
                        }
                    });
                })
            }
        );
        and (
            /^a course with id (.*) does not already exists in the system$/,
            async (arg0) => {
                await accessBackend(defaultUser, async () => {
                    let courses = await GET('course');
                    courses.forEach((course) => {
                        if (course.id == arg0){
                            error = 'University already exists';
                            assert.fail(error);
                        }
                    });
                })
            }
        );

        when(
            /^user attempts to create a item posting with title (.*), description (.*), imagelink (.*), universityId (.*), courseIds (.*), and price (.*)$/,
            async (arg0, arg1, arg2, arg3, arg4, arg5) => {
                title = arg0;
                description = arg1;
                imageLink = arg2;
                universityId = arg3;
                courseIds = [arg4];
                price = arg5;
        
                titleInput.props.onChange({ target: { value: title } });
                descriptionInput.props.onChange({ target: { value: description } });
                imageLinkInput.props.onChange({ target: { value: imageLink } });
                universityIdInput.props.onChange({ target: { value: universityId } });
                courseIdsInput.props.onChange({ target: { value: courseIds } });
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

