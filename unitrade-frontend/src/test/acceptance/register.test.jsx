import { loadFeature, defineFeature } from 'jest-cucumber';
import { beforeEach } from 'vitest';
import TestRenderer from 'react-test-renderer';
import { get, post } from '../../utils/client';
import { Signup } from '../../components/Signup';

const feature = loadFeature('../features/ID015_Register_user_account.feature');

let testRenderer = TestRenderer.create(<Signup />);
let testInstance = testRenderer.root;

let form = testInstance.findByType('form');
let allInputs = testInstance.findByType('form').findAllByType('input');

let firstNameInput = allInputs[0];
let lastNameInput = allInputs[1];
let emailInput = allInputs[2];
let usernameInput = allInputs[3];
let passwordInput = allInputs[4];

let firstName = '';
let lastName = '';
let email = '';
let username = '';
let password = '';

defineFeature(feature, (test) => {
    beforeEach(() => {
        firstName = '';
        lastName = '';
        email = '';
        username = '';
        password = '';
    });

    test('User email and username does not already exist in system. (Normal Flow)', ({ given, and, when, then }) => {
        given('user is not logged in', () => {

        });

        and(/^a user with email (.*) or username (.*) does not already exist in the system$/, async (arg0, arg1) => {
            let persons = await get('person');
            persons.forEach((person) => {
                if (person.email === arg0 || person.username === arg1) {
                    error = 'User already exists';
                    assert.fail(error);
                }});
        });

        when(/^user registers with email (.*), username (.*), first name (.*), last name (.*) and password (.*)$/, async (arg0, arg1, arg2, arg3, arg4) => {
            emailInput.props.onChange({ target: { value: arg0 } });
            usernameInput.props.onChange({ target: { value: arg1 } });
            firstNameInput.props.onChange({ target: { value: arg2 } });
            lastNameInput.props.onChange({ target: { value: arg3 } });
            passwordInput.props.onChange({ target: { value: arg4 } });

            await form.props.onSubmit({ preventDefault: () => {} });
        });

        then(/^a new user account with email (.*), username (.*), first name (.*), last name (.*) and password (.*) is created$/, async (arg0, arg1, arg2, arg3, arg4) => {
            let person = await get(`person/username/${arg0}`);
            expect(person.email).toBe(arg0);
            expect(person.username).toBe(arg1);
            expect(person.firstName).toBe(arg2);
            expect(person.lastName).toBe(arg3);
            expect(person.password).toBe(arg4);
        });
});

test('User email or username already exists in system. (Error Flow)', ({ given, and, when, then }) => {
        given('user is not logged in', () => {

        });

        and(/^a user with email (.*) or username (.*) already exists in the system$/, async (arg0, arg1) => {
            let person = await get(`person/username/${arg0}`);
            if (person == null) {
                post('person', {
                    email: arg0,
                    username: arg1,
                    firstName: 'Test',
                    lastName: 'Test',
                    password: 'Test'
                });
            }

            // If code gets here, user already exists
        });

        when(/^user registers with email (.*), username (.*), first name (.*), last name (.*) and password (.*)$/, async (arg0, arg1, arg2, arg3, arg4) => {
            emailInput.props.onChange({ target: { value: arg0 } });
            usernameInput.props.onChange({ target: { value: arg1 } });
            firstNameInput.props.onChange({ target: { value: arg2 } });
            lastNameInput.props.onChange({ target: { value: arg3 } });
            passwordInput.props.onChange({ target: { value: arg4 } });

            await form.props.onSubmit({ preventDefault: () => {} });
        });

        then('an error is thrown and no new user account is created', () => {
            expect(error).toBe('User already exists');
        });
});
    
});