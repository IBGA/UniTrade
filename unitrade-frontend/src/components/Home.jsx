import Alert from 'react-bootstrap/Alert';
import styled from "styled-components";

const HomeStyle = styled.div`
    .home-page {
        position: absolute;
        left: 50%;
        top: 50%;
        transform: translate(-50%, -50%);        
    }
`;

export function Home () {
    return (
        <HomeStyle>
            <div className="home-page">
                <Alert variant="success">
                    <Alert.Heading>Home Page</Alert.Heading>
                    <hr />
                    <p>
                        Hello! This is a react-bootstrap alert component. Please try to use react-bootstrap components as much as possible to make things look consistent and pretty. Use custom elements only when it's absolutely necessary.
                        Check the documentation to see what they have to offer and check the code to see how they're implemented.
                    </p>
                    <p>
                        As for custom css, I'm using style-components as they're simple and organized.
                    </p>
                </Alert>
            </div>
        </HomeStyle>
    );
};