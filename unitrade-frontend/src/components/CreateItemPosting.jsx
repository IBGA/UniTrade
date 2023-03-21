import Card from 'react-bootstrap/Card';
import Button from 'react-bootstrap/Button';
import Form from 'react-bootstrap/Form';
import Container from 'react-bootstrap/Container';
import styled from "styled-components";
const CreateItemPostingStyle = styled.div`
    .create-item-posting-title {
        font-size: 2rem;
    }
`;

export function CreateItemPosting() {
    return (
        <CreateItemPostingStyle>
            <Container className='create-item-posting-container'>
                <Card border="light">
                    <Card.body>
                        <Card.Title className="text-cent create-item-posting-title"><b>Create your Item Posting</b></Card.Title>
                    </Card.body>
                </Card>
            </Container>
        </CreateItemPostingStyle>
    );
};