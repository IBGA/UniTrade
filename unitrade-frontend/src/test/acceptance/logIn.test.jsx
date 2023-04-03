import { loadFeature, defineFeature } from 'jest-cucumber';
import { beforeEach } from 'vitest';
import TestRenderer from 'react-test-renderer';
import { Login } from '../../components/Login';
import Alert from 'react-bootstrap/Alert';
import { GET, LOGIN, LOGOUT, POST, DELETE } from '../../utils/client';
import accessBackend from '../../utils/testUtils';
import { expect } from 'vitest';

const feature = loadFeature('../features/ID020_Log_in.feature');

let testRenderer;
let testInstance;

let form;

let allInputs;
let emailInput;
let passwordInput;
let email;
let password;

let error = ''

// Mock navigate function,
vi.mock('react-router-dom', () => ({
    useNavigate: vi.fn(),
}));

defineFeature(feature, (test) => {
    beforeEach(async() =>{

        error = '';
        testRenderer = TestRenderer.create(
            <Login />
        );
        testInstance = testRenderer.root;
        error = ''

        form = testInstance.findByType('form');
        allInputs = testInstance.findByType('form').findAllByType('input');
        emailInput = allInputs[0];
        passwordInput = allInputs[1];
    });

    test('User username exists in system and password is correct. (Normal Flow)', ({
        given,
        and,
        when,
        then
    }) => {
        given('user is not logged in', async() => {
            await LOGOUT();
        });
        and(
            /^a user with username (.*) exists in the system$/,
            async (arg0) => {
                arg0 = arg0.replace(/["]+/g, '');
                let user = {
                    email: arg0+'@email.com',
                    username: arg0,
                    firstName: arg0,
                    lastName: arg0,
                    password: 'default'
                };
                await POST('person', user, false);
            }
        );
        and(
            /^the password for user (.*) is (.*)$/,
            async (arg0, arg1) => {
                arg0 = arg0.replace(/["]+/g, '');
                arg1 = arg1.replace(/["]+/g, '');

                let user = {
                    email: arg0+'@email.com',
                    username: arg0,
                    firstName: arg0,
                    lastName: arg0,
                    password: 'default'
                };
                await accessBackend(user, async () => {
                    user.password = arg1;
                    await POST('person/password', user, true)
                });
            }
        );
        when(
            /^user logs in with username (.*) and password (.*)$/,
            async (arg0, arg1) => {
                arg0 = arg0.replace(/["]+/g, '');
                arg1 = arg1.replace(/["]+/g, '');

                let user = {
                    email: arg0+'@email.com',
                    username: arg0,
                    firstName: arg0,
                    lastName: arg0,
                    password: arg1
                };
                email = user.email;
                password = user.password;

                emailInput.props.onChange({ target: { value: email } });
                passwordInput.props.onChange({ target: { value: password } });

                await form.props.onSubmit({ preventDefault: () => {} });
            }
        )
        then(
            /^user is logged in and redirected to the home page$/,
            async () => {
                let user = GET('person', true);
                await expect(user).toBeDefined();

                // Cleanup
                DELETE('person', true);
            }
        )
    })
    test('User username exists in system but password is incorrect. (Error Flow)', ({
        given,
        and,
        when,
        then
    }) => {
        given('user is not logged in', async() => {
            await LOGOUT();
        });
        and(
            /^a user with username (.*) exists in the system$/,
            async (arg0) => {
                arg0 = arg0.replace(/["]+/g, '');
                let user = {
                    email: arg0+'@email.com',
                    username: arg0,
                    firstName: arg0,
                    lastName: arg0,
                    password: 'default'
                };
                await POST('person', user, false);
            }
        );
        and(
            /^the password for user (.*) is not (.*)$/,
            async (arg0, arg1) => {
                arg0 = arg0.replace(/["]+/g, '');
                arg1 = arg1.replace(/["]+/g, '');

                let user = {
                    email: arg0+'@email.com',
                    username: arg0,
                    firstName: arg0,
                    lastName: arg0,
                    password: 'default'
                };
                await accessBackend(user, async () => {
                    user.password = arg1+'not-password';
                    await POST('person/password', user, true)
                });
            }
        );
        when(
            /^user logs in with username (.*) and password (.*)$/,
            async (arg0, arg1) => {
                arg0 = arg0.replace(/["]+/g, '');
                arg1 = arg1.replace(/["]+/g, '');

                let user = {
                    email: arg0+'@email.com',
                    username: arg0,
                    firstName: arg0,
                    lastName: arg0,
                    password: arg1
                };
                email = user.email;
                password = user.password;

                emailInput.props.onChange({ target: { value: email } });
                passwordInput.props.onChange({ target: { value: password } });

                await form.props.onSubmit({ preventDefault: () => {} });
            }
        );
        then(
            /^an error is thrown and user is not logged in$/,
            async () => {
                await setTimeout(() => {
                    expect(testInstance.findByType(Alert).props.show).toBe(true);
                }, 10000);

                // Cleanup
                LOGIN(email, password);
                DELETE('person', true);
                LOGOUT();
            }
        )
    });
    test('Username does not exist in system. (Error Flow)', ({
        given,
        and,
        when,
        then
    }) => {
        given('user is not logged in', async() => {
            await LOGOUT();
        });
        and(
            /^a user with username (.*) does not in the system$/,
            async (arg0)=> {
                arg0 = arg0.replace(/["]+/g, '');
                let exists = await GET('person/exists/username/'+arg0, false)
                if (exists) {
                    error = 'User exists in system';
                    assert.fail(error);
                }
            }
        )
        when(
            /^user logs in with username (.*) and password (.*)$/,
            async (arg0, arg1) => {
                arg0 = arg0.replace(/["]+/g, '');
                arg1 = arg1.replace(/["]+/g, '');

                let user = {
                    email: arg0+'@email.com',
                    username: arg0,
                    firstName: arg0,
                    lastName: arg0,
                    password: arg1
                };
                email = user.email;
                password = user.password;

                emailInput.props.onChange({ target: { value: email } });
                passwordInput.props.onChange({ target: { value: password } });

                await form.props.onSubmit({ preventDefault: () => {} });
            }
        );
        then(
            /^an error is thrown and user is not logged in$/,
            async () => {
                await setTimeout(() => {
                    expect(testInstance.findByType(Alert).props.show).toBe(true);
                }, 1000);

                // Cleanup
                LOGIN(email, password);
                DELETE('person', true);
                LOGOUT();
            }
        )
    })
})