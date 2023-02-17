import Container from 'react-bootstrap/Container';
import Nav from 'react-bootstrap/Nav';
import Navbar from 'react-bootstrap/Navbar';
import logo from '../assets/unitrade-nav.png';
import styled from "styled-components";
import { Form } from 'react-bootstrap';
import Button from 'react-bootstrap/Button';

const NavMenuStyle = styled.div`
    .bg-white{
        background-color: white;
    }

    .nav-link {
        color: var(--black);
        font-size: 1rem;
    }
`;

export function NavMenu() {
    return (
        <>
            <NavMenuStyle>
                <Navbar bg="white" variant="light" fixed="top">
                    <Container>
                        <Navbar.Brand href="/">
                            <img
                                alt=""
                                src={logo}
                                width="125"
                                height="50"
                                className="d-inline-block align-top"
                            />{' '}
                        </Navbar.Brand>
                        <Nav className="me-auto">
                            <Nav.Link href="/">Home</Nav.Link>
                            <Nav.Link href="/">Pricing</Nav.Link>
                            <Nav.Link href="/">About</Nav.Link>
                        </Nav>
                        <Form className="d-flex">
                            <Form.Control
                                type="search"
                                placeholder="Search"
                                className="me-2"
                                aria-label="Search"
                            />
                            <Button variant="outline-success">Search</Button>
                        </Form>
                    </Container>
                </Navbar>
            </NavMenuStyle>
        </>
    );
}