import React from "react";
import styled from "styled-components";
import Card from 'react-bootstrap/Card';
import Button from 'react-bootstrap/Button';
import Form from 'react-bootstrap/Form';
import Container from 'react-bootstrap/Container';

const CreateUniversityStyle = styled.div`
    .create-university-button {
        width: 100%;
    }
`;

export function CreateUniversity() {
    return (
        <CreateUniversityStyle>
            <Container className='create-university-container'>
                <Card border="light">
                    <Card.Body>
                        <Card.Title className="text-center login-title"><b>Add a University</b></Card.Title>
                        <Form>
                            <Form.Group className="mb-3" controlId="formBasicName">
                                <Form.Label>Name</Form.Label>
                                <Form.Control type="text" placeholder="University Name" />
                            </Form.Group>

                            <Form.Group className="mb-3" controlId="formBasicCity">
                                <Form.Label>City</Form.Label>
                                <Form.Control type="text" placeholder="City" />
                            </Form.Group>

                            <Form.Group className="mb-3" controlId="formBasicDescription">
                                <Form.Label>Description</Form.Label>
                                <Form.Control type="text" placeholder="Short Description" />
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
