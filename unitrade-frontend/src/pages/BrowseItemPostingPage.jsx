import React, { useState, useEffect } from "react";
import styled from 'styled-components';
import { ItemPostingThumbnail, ItemPostingSearchHeader } from "../components/ItemPosting";
import Container from 'react-bootstrap/Container';
import Row from 'react-bootstrap/Row';
import Col from 'react-bootstrap/Col';
import { GET } from '../utils/client';
import { useNavigate } from "react-router-dom";

const BrowseItemsStyle = styled.div`
  .items-header {
    background-color: var(--secondary);
  }
  .items-header > h1 {
    color: white;
  }
  .items-header > p {
    color: white;
  }
`;

export function BrowseItemPostingPage() {
  const [selectedUniversityId, setSelectedUniversityId] = useState(null);
  const [itemPostings, setItemPostings] = useState([]);
  const navigate = useNavigate();

  useEffect(() => {
    // console.log("selectedUniversityId changed to", selectedUniversityId);

    async function getItemPostings() {
      let url = "";
      if (selectedUniversityId === "*" || selectedUniversityId === null) {
        url = "itemposting";
      } else {
        url = `university/${selectedUniversityId}`;
      }
      const res = await GET(url);
      if (typeof res === "string" || res.error) {
        setItemPostings([]);
      } else {
        setItemPostings(res);
      }
    }

    getItemPostings();
  }, [selectedUniversityId]);

  const handleUniversitySelect = (option) => {
    setSelectedUniversityId(option.value);
  }

  const handleItemPostingClick = (id) => {
    navigate(`/item/${id}`);
  }

  return (
    <BrowseItemsStyle>
      <div className="items-header p-4">
        <h1 className="text-center">Browse Items for Sale</h1>
        <p className="text-center">Here you can find a list of items that are currently available for sale. Browse through the thumbnails below and click on an item to see more details.</p>
      </div>
    <Container className="my-4">
      <ItemPostingSearchHeader onUniversitySelect={handleUniversitySelect}/>
      {itemPostings.length > 0 ?
        <div>
          <h2>Results</h2>
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
    </BrowseItemsStyle>
  );
};
