import React from "react";
import styled from "styled-components";
import Card from 'react-bootstrap/Card';
import Button from 'react-bootstrap/Button';
import Form from 'react-bootstrap/Form';
import Container from 'react-bootstrap/Container';
import {GET, POST} from "../utils/client";
import ErrorToast from "../components/toasts/ErrorToast";

const CreateUniversityStyle = styled.div`
    .create-university-button {
        width: 100%;
    }
`;

export function CreateUniversity() {
    const [name, setName] = React.useState("");
    const [city, setCity] = React.useState("");
    const [description, setDescription] = React.useState("");

    const handleNameChange = (event) => setName(event.target.value);
    const handleCityChange = (event) => setCity(event.target.value);
    const handleDescriptionChange = (event) => setDescription(event.target.value);

    const [error , setError] = React.useState('');
    const [showError, setShowError] = React.useState(false);

    const [universityCreated, setUniversityCreated] = React.useState(false);

    const handleCloseError = () => setShowError(false);

    const handleSubmit = async (event) => {
        event.preventDefault();
        const university = {name, city, description};
        let res = await POST('university', university);
        if (typeof res === 'string') {
            setError(res);
            setShowError(true);
        } else {
            setUniversityCreated(true);
        }
    }

    if (universityCreated) {
        return (
            <Container>
                <h1>You have successfully created a University!</h1>
                {/* <Nav.Link className="login-link" href="/login">Click here to login!</Nav.Link>  */}
            </Container>
        )
    }

    return (
        <CreateUniversityStyle>
            <ErrorToast message={error} onClose={handleCloseError} show={showError} />
            <Container className='create-university-container'>
                <Card border="light">
                    <Card.Body>
                        <Card.Title className="text-center login-title"><b>Add a University</b></Card.Title>
                        <Form onSubmit={handleSubmit}>
                            <Form.Group className="mb-3" controlId="formBasicName">
                                <Form.Label>Name</Form.Label>
                                <Form.Control type="text" placeholder="University Name" value={name} onChange={handleNameChange} />
                            </Form.Group>

                            <Form.Group className="mb-3" controlId="formBasicCity">
                                <Form.Label>City</Form.Label>
                                <Form.Control type="text" placeholder="City" value={city} onChange={handleCityChange} />
                            </Form.Group>

                            <Form.Group className="mb-3" controlId="formBasicDescription">
                                <Form.Label>Description</Form.Label>
                                <Form.Control type="text" placeholder="Short Description" value={description} onChange={handleDescriptionChange} />
                            </Form.Group>
                            <Button variant="dark" type="submit" className='create-university-button'>
                                Create University
                            </Button>
                        </Form>
                    </Card.Body>
                </Card>
            </Container>
        </CreateUniversityStyle>
    );
}
