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
import Image1 from '../assets/background1.jpg';
import Image2 from '../assets/background2.jpg';
import Image3 from '../assets/background3.jpg';

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
                        src={Image1}
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
                        src={Image2}
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
                        src={Image3}
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