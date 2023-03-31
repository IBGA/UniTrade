import Container from 'react-bootstrap/Container';
import styled from "styled-components";
import { useParams } from "react-router-dom";
import { useEffect, useState } from 'react';
import { GET, PUT } from '../utils/client';
import { getNumOfDays } from '../utils/time';
import Alert from 'react-bootstrap/Alert';
import { useAuth } from "./AuthProvider";
import Row from 'react-bootstrap/Row';
import Col from 'react-bootstrap/Col';
import Image from 'react-bootstrap/Image'
import ListGroup from 'react-bootstrap/ListGroup';
import Stack from 'react-bootstrap/Stack';
import { Button } from 'react-bootstrap';
import Form from 'react-bootstrap/Form';
import FloatingLabel from 'react-bootstrap/FloatingLabel';
import ErrorToast from './toasts/ErrorToast';

const ItemStyle = styled.div`

    .title-text {
        width: 500px;
    }

    .description-text {
        width: 450px;
    }

    .details-list {
        width: 450px;
    }

    .available-text {
        width: 450px;
        color: red;
    }

    .contact-btn {
        margin-top: 10px;
        background-color: var(--secondary);
        border: none;
    }

    .description-input {
        height: 100px;
    }
`;

function ItemUniversitySelect(props) {
    const [universityOptions, setUniversityOptions] = useState([]);
  
    useEffect(() => {
        async function getUniversities() {
            const res = await GET("university");
            const options = res.map(d => ({ value: d.id, label: d.name }));
            setUniversityOptions(options)
        }
        getUniversities();
    }, []);
  
    const handleUniversitySelect = (option) => {
      props.onUniversitySelect(option);
    }
  
    return (
        <FloatingLabel controlId="floatingInputUniversity" label="University" className="mb-3">
            <Form.Select 
                aria-label="Select University"
                value={props.universityId}
                onChange={handleUniversitySelect}>
                {
                    universityOptions.map(university => {
                        return (<option value={university.value} key={university.value}>{university.label}</option>)
                    })
                }
            </Form.Select>
        </FloatingLabel>
    );
}

export function Item() {
    const params = useParams();
    const { user } = useAuth();
    const [isValid, setValid] = useState(0);
    const [errorMsg, setErrorMsg] = useState("");
    const [item, setItem] = useState(null);
    const defaultImg = "https://www.vmcdn.ca/f/files/glaciermedia/import/sk/1503079-literacy-books.png";
    const [itemTitle, setItemTitle] = useState("");
    const [itemDescription, setItemDescription] = useState("");
    const [universityId, setUniversityId] = useState(1);
    const [itemPrice, setItemPrice] = useState(0);
    const [itemAvailable, setItemAvailable] = useState(false);
    const [itemImage, setItemImage] = useState(null);
    const [error, showError] = useState(false);
    const [loading, setLoading] = useState(false);

    useEffect(() => {
        async function getItemPosting() {
            const res = await GET(`itemposting/${params.itemId}`)
            if (typeof res === "string" || res.error) {
                setValid(-1)
                setErrorMsg(res.error == undefined ? res.toString() : res.error.toString() )
                setItem(null)
            } else {
                setValid(1)
                setItem(res)
                setItemTitle(res.title)
                setItemDescription(res.description)
                setUniversityId(res.university.id)
                setItemPrice(res.price)
                setItemAvailable(res.available)
                setItemImage(res.imageLink)
            }
        }
        
        getItemPosting();
    }, []);

    async function handleSubmitChanges(e) {
        e.preventDefault();

        setLoading(true)
        if (itemTitle.trim() === "") {
            setErrorMsg("Error: Please enter a valid title.")
            showError(true)
        }
        if (itemPrice == "" || itemPrice < 0) {
            setErrorMsg("Error: Please enter a valid price of at least 0$.")
            showError(true)
        }

        const res = await PUT(`itemposting/${params.itemId}`, {
            title: itemTitle,
            description: itemDescription,
            imageLink: itemImage,
            price: itemPrice,
            available: itemAvailable,
            courseIds: item.courses.map(course => course.id)
        });

        if (typeof res === "string" || res.error) {
            setErrorMsg(typeof res === "string" ? res : res.error)
            showError(true)
        } else {
            console.log("success")
        }

        setLoading(false)
    }

    function handleUniversitySelect(option) {
        setUniversityId(option.value)
    }

    function handleAvailableSelect(e) {
        setItemAvailable(e.target.value)
    }

    function handleCloseError() {
        showError(false);
    }

    return (
        <ItemStyle>
        <ErrorToast message={errorMsg} onClose={handleCloseError} show={error} />
        <Container>
                {isValid==1 && user != null && item != null && user.username === item.poster.username &&
                    <Container className="p-5">
                    <Row>
                        <Col sm={6}>
                            <Image rounded={true} fluid={true} src={itemImage == null || itemImage == "" ? defaultImg : itemImage}>
                            </Image>
                        </Col>
                        <Col sm={4}>
                            <Form onSubmit={handleSubmitChanges}>
                                <FloatingLabel controlId="floatingInputTitle" label="Title" className="mb-3">
                                    <Form.Control size="lg" type="text" placeholder="Title" defaultValue={item.title} onChange={(e) => setItemTitle(e.target.value)}/>
                                </FloatingLabel>
                                <FloatingLabel controlId="floatingInputDescription" label="Description" className="mb-3">
                                    <Form.Control className="description-input" as="textarea" rows={3} placeholder="Description" defaultValue={item.description} onChange={(e) => setItemDescription(e.target.value)}/>
                                </FloatingLabel>
                                {/* <ItemUniversitySelect universityId={universityId} onUniversitySelect={handleUniversitySelect}/> */}
                                <FloatingLabel controlId="floatingInputPrice" label="Price" className="mb-3">
                                    <Form.Control type="number" placeholder="Price in $" defaultValue={item.price} onChange={(e) => setItemPrice(e.target.value)}/>
                                </FloatingLabel>
                                <FloatingLabel controlId="floatingInputAvailable" label="Available" className="mb-3">
                                    <Form.Select 
                                        aria-label="Select Available"
                                        value={itemAvailable}
                                        onChange={handleAvailableSelect}>
                                        <option value={true}>True</option>
                                        <option value={false}>False</option>
                                    </Form.Select>
                                </FloatingLabel>
                                <FloatingLabel controlId="floatingInputImg" label="Image URL" className="mb-3">
                                    <Form.Control type="text" placeholder="Image URL" defaultValue={item.imageLink} onChange={(e) => setItemImage(e.target.value)}/>
                                </FloatingLabel>

                                <Button className="contact-btn" variant="secondary" type="submit" disabled={loading}>Submit Changes</Button>
                            </Form>
                        </Col>
                    </Row>
                </Container>
                }
                {isValid==1 && user != null && item != null && user.username != item.poster.username &&
                    <Container className="p-5">
                    <Row>
                        <Col sm={6}>
                            <Image rounded={true} fluid={true} src={item.imageLink != null ? item.imageLink : defaultImg}>

                            </Image>
                        </Col>
                        <Col sm={4}>
                            <Stack gap={2} className="col-md-5 mx-auto">
                                <h2 className="title-text">{item.title}</h2>
                                <h5 className="title-text"><i>By {item.poster.firstName + " " + item.poster.lastName}</i></h5>
                                {!item.available &&
                                    <h5 className="available-text">This item is no longer available</h5>
                                }
                                <p className="description-text">{item.description}</p>
                                <b>Details:</b>
                                <ListGroup className="details-list">
                                    <ListGroup.Item><b>Available: </b> {item.available ? "Yes" : "No"}</ListGroup.Item>
                                    <ListGroup.Item><b>Price: </b> {item.price}$</ListGroup.Item>
                                    <ListGroup.Item><b>Time Posted: </b> {getNumOfDays(item.datePosted)}</ListGroup.Item>
                                    <ListGroup.Item><b>University: </b> {item.university.name}</ListGroup.Item>
                                    <ListGroup.Item><b>Location: </b> <a href={`https://www.google.com/maps/${item.university.city}`}>{item.university.city}</a></ListGroup.Item>
                                    <ListGroup.Item><b>Related Courses: </b> {item.courses.map(course => course.codename).join(", ")}</ListGroup.Item>
                                </ListGroup>
                                <Button className="contact-btn" variant="secondary" disabled={!item.available} href={`mailto:${item.poster.email}`}>Contact {item.poster.firstName}</Button>
                            </Stack>
                        </Col>
                    </Row>
                </Container>
                }
                {isValid==-1 && 
                    <Alert variant="danger">
                    <Alert.Heading>Oh snap! This item is not available or does not exist</Alert.Heading>
                    <hr />
                    <p>
                        {errorMsg}
                    </p>
                </Alert>
                }
        </Container>
        </ItemStyle>
    );
};