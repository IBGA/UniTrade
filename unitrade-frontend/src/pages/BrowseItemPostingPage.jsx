import { ItemPostingThumbnail, ItemPostingSearchHeader } from "../components/ItemPosting";
import Container from 'react-bootstrap/Container';
import Row from 'react-bootstrap/Row';
import Col from 'react-bootstrap/Col';

export function BrowseItemPostingPage() {
  return (
    <Container className="my-4">
      <h1 className="text-center">Browse Items for Sale</h1>
      <p className="text-center">Here you can find a list of items that are currently available for sale. Browse through the thumbnails below and click on an item to see more details.</p>
      <ItemPostingSearchHeader/>
      <h2>Result</h2>
      <Row className="mb-5">
        <Col xs={12} md={6} lg={4}>
          <ItemPostingThumbnail
            title="Sample Item"
            description="This is a sample item posting"
            date="February 27, 2023"
            university="Sample University"
            person="John Smith"
            courses="Sample Course 1, Sample Course 2"
            imageSrc="https://picsum.photos/600/200/?random=1"
          />
        </Col>
        <Col xs={12} md={6} lg={4}>
          <ItemPostingThumbnail
            title="Another Item"
            description="This is another sample item posting"
            date="March 1, 2023"
            university="Another University"
            person="Jane Doe"
            courses="Another Course 1, Another Course 2"
            imageSrc="https://picsum.photos/600/200/?random=2"
          />
        </Col>
    </Row>
    <Row>
        <Col xs={12} md={6} lg={4}>
          <ItemPostingThumbnail
            title="Third Item"
            description="This is a third sample item posting"
            date="March 5, 2023"
            university="Third University"
            person="Bob Johnson"
            courses="Third Course 1, Third Course 2"
            imageSrc="https://picsum.photos/600/200/?random=4"
          />
        </Col>
      </Row>
    </Container>
  );
};
