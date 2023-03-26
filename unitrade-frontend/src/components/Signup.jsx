import Card from 'react-bootstrap/Card';
import Button from 'react-bootstrap/Button';
import Form from 'react-bootstrap/Form';
import Container from 'react-bootstrap/Container';
import styled from "styled-components";
import { useState } from 'react';
import {GET, POST} from "../utils/client";
import Nav from 'react-bootstrap/Nav';
import ErrorToast from './toasts/ErrorToast';

const SignupStyle = styled.div`
    .signup-button {
        width: 100%;
    }

    .signup-container {
        max-width: 600px;
        min-height: 100vh;
        margin-top: 200px;
    }

    .border-light {
        border-color: white !important;
    }

    .signup-title {
        font-size: 2rem;
    }

    .login-link {
        margin-top: 20px;
    }

    .login-link-btn {
        color: var(--primary)
    }
`;

export function Signup() {
    const [firstName, setFirstName] = useState('');
    const [lastName, setLastName] = useState('');
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [username, setUsername] = useState('');

    const [loginSuccessful, setLoginSuccessful] = useState(false);

    const [error , setError] = useState('');
    const [showError, setShowError] = useState(false);


    const handleChangeFirstName = (e) => setFirstName(e.target.value);
    const handleChangeLastName = (e) => setLastName(e.target.value);
    const handleChangeEmail = (e) => setEmail(e.target.value);
    const handleChangePassword = (e) => setPassword(e.target.value);
    const handleChangeUsername = (e) => setUsername(e.target.value);

    const handleCloseError = () => setShowError(false);

    const submitForm = async (e) => {
        try {
            e.preventDefault();

            let response = await post('person', {firstName, lastName, email, password, username});
            if (typeof response === 'string') {
                throw new Error(response);
            }
            setLoginSuccessful(true);
        } catch (error) {
            console.log(error)
            setError(error.message);
            setShowError(true);
        }
    }

    if (loginSuccessful) {
        return (
            <Container>
                <h1>Signup Successful</h1>
                <Nav.Link className="login-link" href="/login">Click here to login!</Nav.Link>
            </Container>
        )
    }
    
    return (
        <SignupStyle>
            <ErrorToast message={error} onClose={handleCloseError} show={showError} />
            <Container className='signup-container'>
                <Card border="light">
                    <Card.Body>
                        <Card.Title className="text-center signup-title"><b>Create Account</b></Card.Title>
                        <Form onSubmit={submitForm}>
                            <Container style={{display:'flex', justifyContent:'space-between'}}>
                                <Form.Group className="mb-3" controlId="formBasicFirstName">
                                    <Form.Label>First Name</Form.Label>
                                    <Form.Control type="text" placeholder="First Name" onChange={handleChangeFirstName} />
                                </Form.Group>
                                <Form.Group className="mb-3" controlId="formBasicLastName">
                                    <Form.Label>Last Name</Form.Label>
                                    <Form.Control type="text" placeholder="Last Name" onChange={handleChangeLastName} />
                                </Form.Group>
                            </Container>

                            <Container style={{display:'flex', justifyContent:'space-between'}}>
                            <Form.Group className="mb-3" controlId="formBasicUsername">
                                <Form.Label>Username</Form.Label>
                                <Form.Control type="text" placeholder="Last Name" onChange={handleChangeUsername} />
                            </Form.Group>

                            <Form.Group className="mb-3" controlId="formBasicEmail">
                                <Form.Label>Email</Form.Label>
                                <Form.Control type="email" placeholder="Enter email" onChange={handleChangeEmail}/>
                            </Form.Group>
                            </Container>

                            <Container style={{display:'flex', justifyContent:'space-between'}}>
                            <Form.Group className="mb-3" controlId="formBasicPassword" style={{width:"100%"}}>
                                <Form.Label>Password</Form.Label>
                                <Form.Control type="password" placeholder="Password" onChange={handleChangePassword}/>
                            </Form.Group>
                            </Container>

                            <Button variant="dark" type="submit" className='signup-button'>
                                Sign up
                            </Button>
                            <Card.Text className='text-center login-link'>Already a member? <Card.Link className="login-link-btn" href="/login">Login here</Card.Link></Card.Text>                     
                        </Form>
                    </Card.Body>
                </Card>
            </Container>
        </SignupStyle>
    );
};