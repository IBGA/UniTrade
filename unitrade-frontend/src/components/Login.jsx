import Card from 'react-bootstrap/Card';
import Button from 'react-bootstrap/Button';
import Form from 'react-bootstrap/Form';
import Container from 'react-bootstrap/Container';
import Alert from 'react-bootstrap/Alert';
import Collapse from 'react-bootstrap/Collapse';
import Spinner from 'react-bootstrap/Spinner';
import styled from "styled-components";
import { useNavigate } from "react-router-dom";
import { useState, useEffect } from 'react';
import { LOGIN, GET } from "../utils/client";


const LoginStyle = styled.div`
    .login-button {
        width: 100%;
    }
    .login-container {
        max-width: 600px;
        min-height: 100vh;
        margin-top: 200px;
    }
    .border-light {
        border-color: white !important;
    }
    .login-title {
        font-size: 2rem;
    }
    .register-link {
        margin-top: 20px;
    }
    .register-link-btn {
        color: var(--primary)
    }
`;

export function Login() {

    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [waiting, setWaiting] = useState(false);
    const [loginError, setLoginError] = useState(false);
    const [loginErrorMsg, setLoginErrorMsg] = useState('');
    const navigate = useNavigate();

    useEffect(() => {
        async function loginPulse() {
            // Check if user is already logged in
            const res = await GET('authenticated', {});
            if (!res.error) navigate('/');
        }
        loginPulse();
    }, []);

    async function handleLoginSubmit(e) {
        e.preventDefault();
        setLoginError(false);
        setWaiting(true);
    
        const res = await LOGIN(email, password);
        setWaiting(false);

        if (res.error) {
            // Login failed, show error message
            if (res.error.toString() === "Unauthorized") {
                setLoginErrorMsg("Invalid email or password")
            } else {
                setLoginErrorMsg(res.error.toString())
            }
            setLoginError(true);
        } else {
            // Successful login, redirect to home page
            navigate('/');
        }
    }

    return (
        <LoginStyle>
            <Container className='login-container'>
                <Card border="light">
                    <Card.Body>
                        <Card.Title className="text-center login-title"><b>Welcome Back!</b></Card.Title>
                        <Collapse in={loginError}>
                            <Alert variant="danger" onClose={() => setLoginError(false)} show={loginError} dismissible>
                                <Alert.Heading>Oh snap! You got an error!</Alert.Heading>
                                <p>{loginErrorMsg}</p>
                            </Alert>
                        </Collapse>
                        <Form onSubmit={handleLoginSubmit}>
                            <Form.Group className="mb-3" controlId="formBasicEmail">
                                <Form.Label>Email</Form.Label>
                                <Form.Control type="email" placeholder="Enter email" onChange={(e) => setEmail(e.target.value)}/>
                            </Form.Group>

                            <Form.Group className="mb-3" controlId="formBasicPassword">
                                <Form.Label>Password</Form.Label>
                                <Form.Control type="password" placeholder="Password" onChange={(e) => setPassword(e.target.value)}/>
                            </Form.Group>
                            <Button variant="dark" type="submit" className='login-button' disabled={waiting}>
                                {waiting && 
                                    <Spinner
                                        as="span"
                                        animation="border"
                                        size="sm"
                                        role="status"
                                        aria-hidden="true"
                                    />
                                }
                                Login
                            </Button>
                            <Card.Text className='text-center register-link'>Don't have an account? <Card.Link className="register-link-btn" href="/signup">Register here</Card.Link></Card.Text>                     
                        </Form>
                    </Card.Body>
                </Card>
            </Container>
        </LoginStyle>
    );
};