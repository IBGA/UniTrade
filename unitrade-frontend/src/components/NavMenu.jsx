import Container from 'react-bootstrap/Container';
import Nav from 'react-bootstrap/Nav';
import Navbar from 'react-bootstrap/Navbar';
import logo from '../assets/unitrade-nav.png';
import styled from "styled-components";

const NavMenuStyle = styled.div`
    .bg-white{
        background-color: white;
    }

    .nav-link {
        color: var(--black);
        font-size: 1rem;
    }

    .get-started-link {
        padding: 10px;
       background-color: #00c2cb7d;
       transition: background-color .2s linear;
       box-shadow: 0 2px 10px 0 rgb(55 51 115 / 30%);
       border-radius: 4px;
       font-weight: 550;
    }
`;

export function NavMenu() {
    return (
        <>
            <NavMenuStyle>
                <Navbar bg="white" variant="light" sticky="top">
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
                        <Nav.Link className="get-started-link" href="/login">Get Started</Nav.Link>
                    </Container>
                </Navbar>
            </NavMenuStyle>
        </>
    );
}