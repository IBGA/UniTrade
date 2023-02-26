import { useState, useEffect } from "react";
import { ItemPostingThumbnail, ItemPostingSearchHeader } from "../components/ItemPosting";
import Container from 'react-bootstrap/Container';
import Row from 'react-bootstrap/Row';
import Col from 'react-bootstrap/Col';

export function BrowseItemPostingPage() {
  const [selectedUniversityId, setSelectedUniversityId] = useState(null);
  const [itemPostings, setItemPostings] = useState([]);

  useEffect(() => {
    console.log("selectedUniversityId changed to", selectedUniversityId);
    let url = "";
    if (selectedUniversityId === "*" || selectedUniversityId === null) {
      url = "http://localhost:8080/itemposting";
    } else {
      url = `http://localhost:8080/itemposting/university/${selectedUniversityId}`;
    }
    fetch(url)
      .then(response => {
        if (response.ok) {
          return response.json();
        } else {
          setItemPostings([]);
          return [];
        }
      })
      .then(data => setItemPostings(data))
      .catch(error => console.error(error));
  }, [selectedUniversityId]);

  const handleUniversitySelect = (option) => {
    setSelectedUniversityId(option.value);
  }

  return (
    <Container className="my-4">
      <h1 className="text-center">Browse Items for Sale</h1>
      <p className="text-center">Here you can find a list of items that are currently available for sale. Browse through the thumbnails below and click on an item to see more details.</p>
      <ItemPostingSearchHeader onUniversitySelect={handleUniversitySelect} />
      {itemPostings.length > 0 ?
        <div>
          <h2>Results</h2>
          <Row className="mb-5">
            {itemPostings.map(posting =>
              <Col xs={12} md={6} lg={4} key={posting.id}>
                <ItemPostingThumbnail
                  title={posting.title}
                  description={posting.description}
                  date={posting.datePosted}
                  university={posting.university.name}
                  person={posting.poster.username}
                  courses={posting.courses}
                  imageSrc={""}
                />
              </Col>
            )}
          </Row>
        </div>
        :
        <h4 className="text-center">No item posting found.</h4>
      }
    </Container>
  );
};
