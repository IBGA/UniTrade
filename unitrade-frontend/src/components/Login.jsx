import Card from 'react-bootstrap/Card';
import Button from 'react-bootstrap/Button';
import Form from 'react-bootstrap/Form';
import Container from 'react-bootstrap/Container';
import styled from "styled-components";

const LoginStyle = styled.div`
    .login-button {
        width: 100%;
        background-color: var(--green);
        border-color: var(--green);
    }

    .login-container {
        display: flex;
        justify-content: center;
        align-items: center;
        height: calc(100vh - 80px);
        width: 100%;
        color: var(--primary);
    }

    .login-card {
        flex-direction: row;
        border: 0px;
        min-width: 500px;
        width: 70%;
        min-height: 400px;
        height: 50%;
    }

    .card-image {
        /* Remove the default rounded borders on the left side */
        border-bottom-left-radius: 0px;
        border-top-left-radius: 0px;
        object-fit: cover;
        background-position: center;
        width: 50%;
    }

    .card-form {
        /* Vertically center the form */
        display: flex;
        flex-direction: column;
        justify-content: center;
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
        color: var(--secondary)
    }
`;

export function Login() {
    return (
        <LoginStyle>
            <Container className='login-container'>
                <Card className='login-card shadow'>
                    <Card.Body>
                        <Card.Title className="text-center login-title"><b>Welcome Back!</b></Card.Title>
                        <Form className='card-form'>
                            <Form.Group className="mb-3" controlId="formBasicEmail">
                                <Form.Label>Email</Form.Label>
                                <Form.Control type="email" placeholder="Email" />
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
                    <Card.Img className="card-image" src="https://images.unsplash.com/photo-1558022103-603c34ab10ce?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxzZWFyY2h8Mnx8Zm9ycmVzdHxlbnwwfHwwfHw%3D&w=1000&q=80" />
                </Card>
            </Container>
        </LoginStyle>
    );
};