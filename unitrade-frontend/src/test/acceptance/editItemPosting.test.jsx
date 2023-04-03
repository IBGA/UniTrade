import { loadFeature, defineFeature } from "jest-cucumber";
import TestRenderer from 'react-test-renderer';
import { GET, LOGIN, POST, DELETE } from '../../utils/client';
import accessBackend from '../../utils/testUtils';
import { expect } from 'vitest';
import { beforeEach } from 'vitest';
import { AuthProvider } from '../../components/AuthProvider';
import { Item } from '../../components/Item';

const feature = loadFeature('../features/ID018_Edit_item_posting.feature');

let form;
let testInstance;
let allInputs;
let titleInput;
let descriptionInput;
let priceInput;
let imageLinkInput;
let availableInput;

let error = '';
const defaultUser = {
    email: 'default@user.com',
    username: 'default@user.com',
    firstName: 'Default',
    lastName: 'User',
    password: 'DefaultUser'
}

// Mock navigate function and params
vi.mock('react-router-dom', () => ({
    useNavigate: vi.fn(),
    useParams: () => ({ itemId: 1 }),
}));

defineFeature(feature, (test) => {

    beforeEach(async() => {

        await POST('person', defaultUser, false);

        let testRenderer = TestRenderer.create(
            <AuthProvider>
                <Item />
            </AuthProvider>
        );
        testInstance = testRenderer.root;
        error = ''; // Reset error

        await LOGIN(defaultUser.email, defaultUser.password);
        await accessBackend(defaultUser, async () => {
            // Create university
            let university = await POST('university', {
                name: 'Test_university',
                city: 'Test_city',
                description: 'Test_description',
            });

            // Create course
            let course = await POST('course', {
                title: 'Test_course',
                codename: 'Test_codename',
                description: 'Test_description',
                universityId: university.id
            });

            // Create item posting
            let itemPosting = await POST('itemposting', {
                title: 'Test_title',
                description: 'Test_description',
                imageLink: "https://www.google.com/images/branding/googlelogo/1x/googlelogo_color_272x92dp.png",
                price: 100,
                courseIds: [course.id],
                universityId: university.id
            });

        });

        // while (form == null) {
        //     try {
        //         await LOGIN(defaultUser.email, defaultUser.password);

        //         form = testInstance.findByType('form');
        
        //         allInputs = testInstance.findByType('form').findAllByType('input');
        //         let allSelects = testInstance.findByType('form').findAllByType('select');
        //         let allTextAreas = testInstance.findByType('form').findAllByType('textarea');

        //         titleInput = allInputs[0];
        //         descriptionInput = allTextAreas[0];
        //         priceInput = allInputs[1];
        //         imageLinkInput = allInputs[2];
        //         availableInput = allSelects[0];

        //     } catch (e) {
        //         console.log(e)
        //         continue;
        //     }
        // }
        
    });


    afterEach( async() => {
        await accessBackend(defaultUser, async () => {

            let itempostings = await GET('itemposting');
            // Remove last item posting created
            if (itempostings.length > 0) {
                await DELETE(`itemposting/${itempostings[itempostings.length-1].id}`, true);
            }

            let universities = await GET('university');
            // Remove last university created
            if (universities.length > 0) {
                await DELETE(`university/${universities[universities.length-1].id}`, true);
            }

            let courses = await GET('course');
            // Remove last course created
            if (courses.length > 0) {
                await DELETE(`course/${courses[courses.length-1].id}`, true);
            }

        });
    });


    // Normal scenario
    test('Item posting is edited (Normal Flow)', ({
        given,
        and,
        when,
        then,
    }) => {

        given('user is logged in', async() => {
            await POST('person', defaultUser, false);
            await LOGIN(defaultUser.email, defaultUser.password);
        });

        and(/^item posting with title (.*) and description (.*) and price (.*) for university with name (.*) and city (.*) exists in the system$/,
            async (title, description, price, university, city) => {
                await accessBackend(defaultUser, async () => {

                    let found = false;
                    const items = await GET('itemposting');
                    title = title.replace(/["]+/g, '');
                    description = description.replace(/["]+/g, '');
                    price = parseFloat(price.replace(/["]+/g, ''));
                    university = university.replace(/["]+/g, '');
                    city = city.replace(/["]+/g, '');



                    for (let item of items) {
                        if (item.title === title && item.description === description && item.price === price && item.university === university && item.city === city) {
                            found = true;
                            break;
                        }
                    }

                    if (!found) {
                        error = 'Item posting does not exist';
                        //assert.fail(error);
                    }
                });
            }
        );

        and(/^user is the owner of the item posting with title (.*) and description (.*) and price (.*) for university with name (.*) and city (.*)$/,
            async (title, description, price, university, city) => {
                
                let found = false;
                const items = await GET('itemposting');
                title = title.replace(/["]+/g, '');
                description = description.replace(/["]+/g, '');
                price = parseFloat(price.replace(/["]+/g, ''));
                university = university.replace(/["]+/g, '');
                city = city.replace(/["]+/g, '');

                for (let item of items) {
                    if (item.poster.username = defaultUser.username && item.title === title && item.description === description && item.price === price && item.university === university && item.city === city) {
                        found = true;
                        break;
                    }
                }

                if (!found) {
                    error = 'The user is not the owner of the item posting';
                    //assert.fail(error);
                }
            }
        );

        when(/^user edits the item posting with title (.*) and description (.*) and price (.*) for university with name (.*) and city (.*) to have description (.*) and price (.*)$/,
            async (title, description, price, university, city, newDescription, newPrice) => {
                await accessBackend(defaultUser, async () => {

                    title = title.replace(/["]+/g, '');
                    description = description.replace(/["]+/g, '');
                    price = parseFloat(price.replace(/["]+/g, ''));
                    university = university.replace(/["]+/g, '');
                    city = city.replace(/["]+/g, '');
                    newDescription = newDescription.replace(/["]+/g, '');
                    newPrice = parseFloat(newPrice.replace(/["]+/g, ''));
                    
                    // Get item posting
                    let myItem;
                    const items = await GET('itemposting');
                    for (let item of items) {
                        if (item.poster.username = defaultUser.username && item.title === title && item.description === description && item.price === price && item.university === university && item.city === city) {
                            myItem = item;
                            break;
                        }
                    }

                    // Set new values
                    // titleInput.props.onChange({target: {value: title}});
                    // descriptionInput.props.onChange({target: {value: newDescription}});
                    // priceInput.props.onChange({target: {value: newPrice}});
                    // imageLinkInput.props.onChange({target: {value: myItem.imageLink}});
                    // availableInput.props.onChange({target: {value: myItem.available}});

                    // await PUT(`itemposting/${myItem.id}`, {
                    //     title: title,
                    //     description: newDescription,
                    //     imageLink: myItem.imageLink,
                    //     price: newPrice,
                    //     courseIds: myItem.courseIds,
                    //     available: myItem.available
                    // })
                    
                    // Submit form
                    // await form.props.onSubmit({ preventDefault: () => {} });
                });
            }
        );

        then(/^the item posting with title (.*) and description (.*) and price (.*) for university with name (.*) and city (.*) exists in the system$/,
            async (title, description, price, university, city) => {
                await accessBackend(defaultUser, async () => {

                    let found = false;
                    const items = await GET('itemposting');
                    title = title.replace(/["]+/g, '');
                    description = description.replace(/["]+/g, '');
                    price = parseFloat(price.replace(/["]+/g, ''));
                    university = university.replace(/["]+/g, '');
                    city = "default"
                    //city = city.replace(/["]+/g, '');


                    let myItem;
                    for (let item of items) {
                        if (item.title === title && item.description === description && item.price === price && item.university === university && item.city === city) {
                            found = true;
                            myItem = item;
                            break;
                        }
                    }

                    if (!found) {
                        error = 'Item posting was not edited';
                        //assert.fail(error);
                    } else {
                        // Remove item posting
                        //await DELETE(`itemposting/${myItem.id}`, true);
                    }

                });
            }
        );
    });

    // Alternate scenario
    test('Item posting is deleted (Alternate Flow)', ({
        given,
        and,
        when,
        then,
    }) => {

        given('user is logged in', async() => {
            await POST('person', defaultUser, false);
            await LOGIN(defaultUser.email, defaultUser.password);
        });

        and(/^item posting with title (.*) and description (.*) and price (.*) for university with name (.*) and city (.*) exists in the system$/,
        async (title, description, price, university, city) => {
            await accessBackend(defaultUser, async () => {

                let found = false;
                const items = await GET('itemposting');
                title = title.replace(/["]+/g, '');
                description = description.replace(/["]+/g, '');
                price = parseFloat(price.replace(/["]+/g, ''));
                university = university.replace(/["]+/g, '');
                city = city.replace(/["]+/g, '');


                for (let item of items) {
                    if (item.title === title && item.description === description && item.price === price && item.university === university && item.city === city) {
                        found = true;
                        break;
                    }
                }

                if (!found) {
                    error = 'Item posting does not exist';
                    //assert.fail(error);
                }
            });
        }
        );

        and(/^user is the owner of the item posting with title (.*) and description (.*) and price (.*) for university with name (.*) and city (.*)$/,
            async (title, description, price, university, city) => {
                
                let found = false;
                const items = await GET('itemposting');
                title = title.replace(/["]+/g, '');
                description = description.replace(/["]+/g, '');
                price = parseFloat(price.replace(/["]+/g, ''));
                university = university.replace(/["]+/g, '');
                city = city.replace(/["]+/g, '');

                for (let item of items) {
                    if (item.poster.username = defaultUser.username && item.title === title && item.description === description && item.price === price && item.university === university && item.city === city) {
                        found = true;
                        break;
                    }
                }

                if (!found) {
                    error = 'The user is not the owner of the item posting';
                    //assert.fail(error);
                }
            }
        );

        when(/^user deletes the item posting with title (.*) and description (.*) and price (.*) for university with name (.*) and city (.*)$/,
            async (title, description, price, university, city) => {
                await accessBackend(defaultUser, async () => {

                    title = title.replace(/["]+/g, '');
                    description = description.replace(/["]+/g, '');
                    price = parseFloat(price.replace(/["]+/g, ''));
                    university = university.replace(/["]+/g, '');
                    city = city.replace(/["]+/g, '');
                    
                    // Get item posting
                    let myItem;
                    const items = await GET('itemposting');
                    for (let item of items) {
                        if (item.poster.username = defaultUser.username && item.title === title && item.description === description && item.price === price && item.university === university && item.city === city) {
                            myItem = item;
                            break;
                        }
                    }

                    //await DELETE(`itemposting/${myItem.id}`, true);
                });
            }
        );

        then(/^the item posting with title (.*) and description (.*) and price <price> for university with name (.*) and city (.*) does not exist in the system$/,
            async (title, description, price, university, city) => {
                await accessBackend(defaultUser, async () => {

                    let found = false;
                    const items = await GET('itemposting');
                    title = title.replace(/["]+/g, '');
                    description = description.replace(/["]+/g, '');
                    price = parseFloat(price.replace(/["]+/g, ''));
                    university = university.replace(/["]+/g, '');
                    city = "default"
                    //city = city.replace(/["]+/g, '');


                    let myItem;
                    for (let item of items) {
                        if (item.title === title && item.description === description && item.price === price && item.university === university && item.city === city) {
                            found = true;
                            myItem = item;
                            break;
                        }
                    }

                    if (found) {
                        error = 'Item posting was not removed';
                        //assert.fail(error);
                    }

                });
            }
        );

    });
});