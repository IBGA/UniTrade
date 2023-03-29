import React from "react";
import styled from "styled-components";
import Card from 'react-bootstrap/Card';
import Button from 'react-bootstrap/Button';
import Form from 'react-bootstrap/Form';
import Container from 'react-bootstrap/Container';
import {get, post} from "../utils/client";

const CreateCourseStyle = styled.div`
    .create-course-button {
        width: 100%;
    }
`;

export function CreateCourse() {
    const [title, setCourseTitle] = React.useState("");
    const [codeName, setCodeName] = React.useState("");
    const [description, setDescription] = React.useState("");
    const [selectedUniversityId, setSelectedUniversityId] = useState(null);

    url = `http://localhost:8080/university/${selectedUniversityId}`;

    const handleTitleChange = (event) => setCourseTitle(event.target.value);
    const handleCodeNameChange = (event) => setCodeName(event.target.value);
    const handleDescriptionChange = (event) => setDescription(event.target.value);

    const handleSubmit = async (event) => {
        event.preventDefault();
        const course = {title, codeName, description, moderation:[]};
        await post('course', course);
    }
    const handleUniversitySelect = (option) => {
        setSelectedUniversityId(option.value);
      }

    return (
        <CreateCourseStyle>
            <Container className='create-course-container'>
                <Card border="light">
                    <Card.Body>
                    <CreateNewCourseHeader onUniversitySelect={handleUniversitySelect} />
                        <Card.Title className="text-center login-title"><b>Create a Course</b></Card.Title>
                        <Form onSubmit={handleSubmit}>
                            <Form.Group className="mb-3" controlId="formBasicTitle">
                                <Form.Label>Title</Form.Label>
                                <Form.Control type="text" placeholder="Course Title" value={title} onChange={handleTitleChange} />
                            </Form.Group>

                            <Form.Group className="mb-3" controlId="formBasicCodeName">
                                <Form.Label>CodeName</Form.Label>
                                <Form.Control type="text" placeholder="CodeName" value={codeName} onChange={handleCodeNameChange} />
                            </Form.Group>

                            <Form.Group className="mb-3" controlId="formBasicDescription">
                                <Form.Label>Description</Form.Label>
                                <Form.Control type="text" placeholder="Short Description" value={description} onChange={handleDescriptionChange} />
                            </Form.Group>
                            <Button variant="dark" type="submit" className='create-course-button'>
                                Create Course
                            </Button>
                        </Form>
                    </Card.Body>
                </Card>
            </Container>
        </CreateCourseStyle>
    );
}
