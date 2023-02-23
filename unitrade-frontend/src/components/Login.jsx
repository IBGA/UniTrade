import Card from 'react-bootstrap/Card';
import Button from 'react-bootstrap/Button';
import Form from 'react-bootstrap/Form';
import Container from 'react-bootstrap/Container';
import styled from "styled-components";

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
    return (
        <LoginStyle>
            <Container className='login-container'>
                <Card border="light">
                    <Card.Body>
                        <Card.Title className="text-center login-title"><b>Welcome Back!</b></Card.Title>
                        <Form>
                            <Form.Group className="mb-3" controlId="formBasicEmail">
                                <Form.Label>Email</Form.Label>
                                <Form.Control type="email" placeholder="Enter email" />
                            </Form.Group>

                            <Form.Group className="mb-3" controlId="formBasicPassword">
                                <Form.Label>Password</Form.Label>
                                <Form.Control type="password" placeholder="Password" />
                            </Form.Group>
                            <Button variant="dark" type="submit" className='login-button'>
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