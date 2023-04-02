import Card from 'react-bootstrap/Card';
import Button from 'react-bootstrap/Button';
import React, { useState, useEffect } from 'react';
import Form from 'react-bootstrap/Form';
import Container from 'react-bootstrap/Container';
import Alert from 'react-bootstrap/Alert';
import styled from "styled-components";
import { useAuth } from "./AuthProvider";
import {GET, POST} from "../utils/client";
import { useNavigate } from "react-router-dom";
import ErrorToast from './toasts/ErrorToast';

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
    const { user } = useAuth();
    const [universityOptions, setUniversityOptions] = useState([]);
    const [courseOptions, setCourseOptions] = useState([]);
    const [title, setTitle] = useState("");
    const [imageLink, setImageLink] = useState("");
    const [description, setDescription] = useState("");
    const [price, setPrice] = useState("");
    const [universityId, setUniversityId] = useState(null);
    const [courseIds, setCourseIds] = useState([]);
    const [errorMsg, setErrorMsg] = useState("");
    const [error, showError] = useState(false);
    const [loading, setLoading] = useState(false);
    const navigate = useNavigate();

    async function getUniversityOptions() {
        let url = "university";
        const res = await GET(url, true);
        if (typeof res === "string" || res.error){
            setErrorMsg(res.error == undefined ? res.toString() : res.error.toString() )
            setUniversityOptions([]);
            showError(true)
        } else {
            let opts = res.map((university) => (
                {label: university.name, value:university.id}
            ));
            setUniversityOptions(opts);
            if (opts.length > 0) {
                setUniversityId(opts[0].value);
            }
        }
    }

    // On loadup, getUniOptions and set default uni
    useEffect(() => {
        getUniversityOptions()
    }, []);

    useEffect( () => {

        async function getCourseOptions() {
            if (universityId !== null && universityId !== undefined) {
                let res = await GET(`course/university/${universityId}`, true);
                if (typeof res === "string" || res.error){
                    setErrorMsg(res.error == undefined ? res.toString() : res.error.toString() )
                    setCourseOptions([]);
                    showError(true)
                } else {
                    setCourseOptions(res.map((course) => (
                        {label: course.codename, value:course.id}
                    )));
                }
            } else {
                setCourseOptions([]);
            }
        }
        getCourseOptions();

    }, [universityId, universityOptions]);

    const changeTitleHandler = (e) => {setTitle(e.target.value)};
    const changeImageLinkHandler = (e) => {setImageLink(e.target.value)};
    const changeDescriptionHandler = (e) => {setDescription(e.target.value)};
    const changePriceHandler = (e) => {setPrice(e.target.value)};
    const changeUniversityOptionHandler = (e) => {setUniversityId(e.target.value)};
    const changeCourseOptionHandler = (e) => {setCourseIds([].slice.call(e.target.selectedOptions).map(item => item.value))};

    const handleSubmit = async (event) => {
        event.preventDefault();
        setLoading(true)
        //let d = new Date();
        //let datePosted = `${d.getFullYear()}-${("0" + (d.getMonth()+1)).slice(-2)}-${("0" + d.getDate()).slice(-2)}`;
        const itemPosting = {title, description, imageLink, universityId, courseIds, price};
        
        let res = await POST('itemposting', itemPosting);

        if (typeof res === "string" || res.error) {
            setErrorMsg(typeof res === "string" ? res : res.error)
            showError(true)
            setLoading(false)
        } else {
            setLoading(false)
            //navigate('/browse/item');
        }

    }

    function handleCloseError() {
        showError(false);
    }

    return (
        <CreateItemPostingStyle>
            <ErrorToast message={errorMsg} onClose={handleCloseError} show={error} />
            {user != null &&
            <Container className='create-item-posting-container'>
                <Card border="light">
                    <Card.Body>
                        <Card.Title className="text-center create-item-posting-title"><b>Create your Item Posting</b></Card.Title>
                        <Form onSubmit={handleSubmit}>
                            <Form.Group className="mb-3">
                                <Form.Label>Title</Form.Label>
                                <Form.Control type="text" placeholder="My Posting Title" value={title} onChange={changeTitleHandler}/>
                            </Form.Group>

                            <Form.Group className="mb-3">
                                <Form.Label>Description</Form.Label>
                                <Form.Control as="textarea" placeholder="Description of my item" value={description} onChange={changeDescriptionHandler}/>
                            </Form.Group>

                            <Form.Group className="mb-3">
                                <Form.Label>Imagelink</Form.Label>
                                <Form.Control type="text" placeholder="https://website.com/book.png" value={imageLink} onChange={changeImageLinkHandler}/>
                            </Form.Group>

                            <Form.Group className="mb-3">
                                <Form.Label>Price</Form.Label>
                                <Form.Control type="number" placeholder="My selling price" value={price} onChange={changePriceHandler}/>
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
                                <Form.Select onChange={changeCourseOptionHandler} multiple as="select">
                                    {courseOptions.map((course) => (
                                        <option value={course.value}>{course.label}</option>
                                    ))}
                                </Form.Select>
                            </Form.Group>

                            <Button variant="dark" type="submit" className='post-button' disabled={loading}>
                                Post
                            </Button>
                        </Form>
                    </Card.Body>
                </Card>
            </Container>}
            {user == null && 
                <Alert variant="danger">
                <Alert.Heading>Oh snap! You are not logged in</Alert.Heading>
                <hr />
                <p>
                    {errorMsg}
                </p>
            </Alert>
            }
        </CreateItemPostingStyle>
    );
};