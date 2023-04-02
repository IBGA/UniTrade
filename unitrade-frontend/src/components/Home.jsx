import Container from 'react-bootstrap/Container';
import React, { useState, useEffect } from "react";
import styled from "styled-components";
import Carousel from 'react-bootstrap/Carousel';
import Button from 'react-bootstrap/Button';
import { useAuth } from "./AuthProvider";
import { ItemPostingThumbnail, ItemPostingSearchHeader } from "../components/ItemPosting";
import Row from 'react-bootstrap/Row';
import Col from 'react-bootstrap/Col';
import { useNavigate } from "react-router-dom";
import { GET } from '../utils/client';

const HomeStyle = styled.div`
.image {
    height: 500px;
    width: 100%;
    object-fit: cover;
}

.items-header {
    background-color: var(--secondary);
    padding: 3rem;
    border-radius: 2rem;
}

.header-details > h1 {
    color: white;
    margin-top: 3rem;
}

.header-details > p {
    color: white;
}

.header-details {
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
}

.start-btn {
    background-color: var(--primary);
    border: 3px solid var(--primary);
    margin-top: 2rem;
    color: white;
    padding: 1rem 2rem;
}

.start-btn:hover {
    background-color: var(--secondary);
}

.img > h3 {

}
`;

export function Home() {

    const { auth, setAuth } = useAuth();
    const [itemPostings, setItemPostings] = useState([]);
    const navigate = useNavigate();
  
    useEffect(() => {
  
      async function getItemPostings() {
        const res = await GET('itemposting');
        if (typeof res === "string" || res.error) {
          setItemPostings([]);
        } else {
          setItemPostings(res.slice(0, 9));     // Pick the first 9 item postings
        }
      }
  
      getItemPostings();
    }, []);


    const handleItemPostingClick = (id) => {
        navigate(`/item/${id}`);
      }

    return (
        <Container className="home-container">
            <HomeStyle>

            <div className='items-header'>
            <Carousel className='image'>
                    <Carousel.Item className='image'>
                        <img
                        className="d-block rounded-5 image"
                        src="https://pixabay.com/get/ge46614e76a3b3f12b6eccf00f18543931277950d6f7579e7c0e8a31c9e2b5c2d5bc75a66f82ec8041ece381b0150701089aac5ffb1f6c1a60f601848392727d72b1dcbb2e528bbe20071d92f6589828e_1280.jpg"
                        alt="First slide"
                        />
                        <Carousel.Caption>
                        <h3 className='bg-secondary text-white'>Have you considered finally getting productive at school?</h3>
                        {/* <p className='bg-secondary text-white'>UniTrade is the right initiative to connect with other students.</p> */}
                        </Carousel.Caption>
                    </Carousel.Item>
                    <Carousel.Item className='image'>
                        <img
                        className="d-block rounded-5 image"
                        src="https://pixabay.com/get/gdb2ba1b0f33a5cec9051fe992c8f1131507595e4cc23667bf3d769eb0c642c52287fe44c47c5c6f172c10e7d2db5165f92717a33ad94ac99b2feab1d953d82bafc10a5f9524f62b9d88ec9ca1f8ab945_1280.jpg"
                        alt="Second slide"
                        />

                        <Carousel.Caption>
                        <h3 className='bg-secondary text-white'>"Assume you are failing class" - Joe Smith</h3>
                        {/* <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit.</p> */}
                        </Carousel.Caption>
                    </Carousel.Item>
                    <Carousel.Item className='image'>
                        <img
                        className="d-block rounded-5 image"
                        src="https://pixabay.com/get/g188f6749aeaca47125addf074f9e51b8958d0b576d870b80184c56609e00e62219566c9f8f6cf0072525da5802678781f7744cf55282bcb5afc0a7fc471f2d46e9849b19e7697765bd24fc238bb554a5_1280.jpg"
                        alt="Third slide"
                        />

                        <Carousel.Caption>
                        <h3 className='bg-secondary text-white'>"Books go bbbrrrrr" - Joey</h3>
                        {/* <p>
                            Praesent commodo cursus magna, vel scelerisque nisl consectetur.
                        </p> */}
                        </Carousel.Caption>
                    </Carousel.Item>
                </Carousel>
                <div className='header-details'>
                    <h1 className="text-center">Welcome to UniTrade</h1>
                    <p className="text-center">The ultimate marketplace for students! Buy and sell anything from textbooks to furniture, and connect with fellow students in your area. Say goodbye to high prices and hello to a community of like-minded students looking to save money and make their college experience more enjoyable. Join now and start trading!</p>
                    
                    {auth ?
                    <Button variant="primary" size="lg" className='start-btn' href="/browse/item">
                        Browse Items
                    </Button>
                    :
                    <Button variant="primary" size="lg" className='start-btn' href="/login">
                        Getting Started
                    </Button>
                    }
                </div>
            </div>
            <Container className="my-4">
                <h3 className='mb-4'>Latest Item Postings</h3>
                {itemPostings.length > 0 ?
                <div>
                    <Row className="mb-5">
                        {itemPostings.map(posting => (
                        <Col xs={12} md={6} lg={4} key={posting.id}>
                            <ItemPostingThumbnail
                            title={posting.title}
                            description={posting.description}
                            date={posting.datePosted}
                            university={posting.university.name}
                            person={posting.poster.username}
                            courses={posting.courses.map(course => course.codename)}
                            imageSrc={""}
                            onClick={() => handleItemPostingClick(posting.id)}
                            />
                        </Col>
                        ))}
                    </Row>
                    </div>
                    :
                    <h4 className="text-center">No item posting found.</h4>
                }
            </Container>
            </HomeStyle>
        </Container>
    );
};