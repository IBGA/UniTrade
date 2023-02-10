import Alert from 'react-bootstrap/Alert';
import styled from "styled-components";

const NotFoundStyle = styled.div`
    .not-found-page {
        position: absolute;
        left: 50%;
        top: 50%;
        transform: translate(-50%, -50%);        
    }
`;

export function NotFound () {
    return (
        <NotFoundStyle>
            <div className="not-found-page">
                <Alert variant="danger">
                    <Alert.Heading>Oh snap! This page does not exist!</Alert.Heading>
                    <hr />
                    <p>
                        Make sure you're at the right url.
                    </p>
                </Alert>
            </div>
        </NotFoundStyle>
    );
};