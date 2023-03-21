import Card from 'react-bootstrap/Card';
import Button from 'react-bootstrap/Button';
import { useState } from 'react';
import { useMemo } from 'react';
import Form from 'react-bootstrap/Form';
import Container from 'react-bootstrap/Container';
import styled from "styled-components";

const CreateItemPostingStyle = styled.div`
    .post-button {
        width: 100%;
    }    

    .create-item-posting-container {
        max-width: 600px;
        min-height: 100vh;
        margin-top: 200px;
    }

    .border-light {
        border-color: white !important;
    }

    .create-item-posting-title {
        font-size: 2rem;
    }
`;

export function CreateItemPosting() {
    const course_map = {
        1: [
            {label: "ECSE428", value: 1},
            {label: "ECSE321", value: 2},
            {label: "ECSE211", value: 3}
        ],
        2: [
            {label: "CONC444", value: 4},
            {label: "CONC555", value: 5},
            {label: "CONC666", value: 6}
        ],
        3: [
            {label: "UDEM777", value: 7},
            {label: "UDEM888", value: 8},
            {label: "UDEM999", value: 9}
        ]
    };

    const universityOptions = [
        {label: "McGill", value: 1},
        {label: "Concordia", value: 2},
        {label: "UDEM", value: 3}
    ];

    const [university, setUniversity] = useState(1);
    const [course, setCourse] = useState(null);

    const changeUniversityOptionHandler = (e) => {
        setUniversity(e.target.value);
    };
        const changeCourseOptionHandler = (e) => {
        setCourse(e.target.value);
    }

    let courseOptions = course_map[university];


    return (
        <CreateItemPostingStyle>
            <Container className='create-item-posting-container'>
                <Card border="light">
                    <Card.Body>
                        <Card.Title className="text-center create-item-posting-title"><b>Create your Item Posting</b></Card.Title>
                        <Form>
                            <Form.Group className="mb-3">
                                <Form.Label>Title</Form.Label>
                                <Form.Control type="text" placeholder="My Posting Title"/>
                            </Form.Group>

                            <Form.Group className="mb-3">
                                <Form.Label>Description</Form.Label>
                                <Form.Control as="textarea" placeholder="Description of my item"/>
                            </Form.Group>

                            <Form.Group className="mb-3">
                                <Form.Label>University</Form.Label>
                                <Form.Select onChange={changeUniversityOptionHandler}>
                                    {universityOptions.map((university) => (
                                        <option value={university.value}>{university.label}</option>
                                    ))}
                                </Form.Select>
                            </Form.Group>

                            <Form.Group className="mb-3">
                                <Form.Label>Courses</Form.Label>
                                <Form.Select onChange={changeCourseOptionHandler}>
                                    {courseOptions.map((university) => (
                                        <option value={university.value}>{university.label}</option>
                                    ))}
                                </Form.Select>
                            </Form.Group>

                            <Form.Group className="mb-3">
                                <Form.Label>Price</Form.Label>
                                <Form.Control type="number" placeholder="My selling price"/>
                            </Form.Group>

                            <Button variant="dark" type="submit" className='post-button'>
                                Post
                            </Button>
                        </Form>
                    </Card.Body>
                </Card>
            </Container>
        </CreateItemPostingStyle>
    );
};