import Alert from 'react-bootstrap/Alert';
import { Container } from 'react-bootstrap';
import styled from "styled-components";

const NotFoundStyle = styled.div`
    .not-found-page{
        margin-top: 10rem;
    }
`;

export function NotFound() {
    return (
        <NotFoundStyle>
            <Container>
                <div className="not-found-page">
                    <Alert variant="danger">
                        <Alert.Heading>Oh snap! This page does not exist!</Alert.Heading>
                        <hr />
                        <p>
                            Make sure you're at the right url.
                        </p>
                    </Alert>
                </div>
            </Container>
        </NotFoundStyle>
    );
};