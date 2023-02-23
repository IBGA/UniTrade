import Card from 'react-bootstrap/Card';
import Button from 'react-bootstrap/Button';
import Form from 'react-bootstrap/Form';
import Container from 'react-bootstrap/Container';
import styled from "styled-components";

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
    return (
        <SignupStyle>
            <Container className='signup-container'>
                <Card border="light">
                    <Card.Body>
                        <Card.Title className="text-center signup-title"><b>Create Account</b></Card.Title>
                        <Form>
                            <Form.Group className="mb-3" controlId="formBasicEmail">
                                <Form.Label>Email</Form.Label>
                                <Form.Control type="email" placeholder="Enter email" />
                            </Form.Group>

                            <Form.Group className="mb-3" controlId="formBasicPassword">
                                <Form.Label>Password</Form.Label>
                                <Form.Control type="password" placeholder="Password" />
                            </Form.Group>
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