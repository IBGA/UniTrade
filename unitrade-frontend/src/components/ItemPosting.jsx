import React, { useState, useEffect  } from "react";
import Card from 'react-bootstrap/Card';
import Container from 'react-bootstrap/Container';
import styled from 'styled-components';
import Select from 'react-select';
import { Form, Dropdown } from 'react-bootstrap';
import Button from "react-bootstrap/Button";
import { getNumOfDays } from '../utils/time';
import { GET } from '../utils/client';
import Badge from 'react-bootstrap/Badge';

const ItemPostingThumbnailStyle = styled.div`
  .item-posting-container {
    cursor: pointer;
  }

  .item-posting-container:hover {

  }

  .card-body {
    padding: 1rem;
  }

  .card-title {
    font-size: 1.5rem;
    margin-bottom: 0.5rem;
  }

  .card-subtitle {
    margin-bottom: 0.5rem;
  }

  .card-text {
    margin-bottom: 0.5rem;
  }

  .badge-body {
    background-color: var(--secondary) !important;
  }

`;

export function ItemPostingThumbnail(props) {
  const { title, description, date, university, person, courses, imageSrc, onClick } = props;

  return (
    <ItemPostingThumbnailStyle>
      <Container className="item-posting-container" onClick={onClick}>
        <Card bg="light">
          <Card.Img variant="top" src={imageSrc} />
          <Card.Body>
            <Card.Title>{title} {(getNumOfDays(date).includes("hours") || getNumOfDays(date).includes("minutes")) && <Badge className="badge-body" bg="secondary">New</Badge>}</Card.Title>
            <Card.Subtitle className="mb-2 text-muted">{university}</Card.Subtitle>
            <Card.Subtitle className="mb-2 text-muted">{getNumOfDays(date)}</Card.Subtitle>
            <Card.Subtitle className="mb-2 text-muted">{person}</Card.Subtitle>
            <Card.Text>{description}</Card.Text>
            {courses.length > 0 &&
              <Card.Text>
                <strong>Courses:</strong> {courses.join(", ")}
              </Card.Text>
            }
          </Card.Body>
        </Card>
      </Container>
    </ItemPostingThumbnailStyle>
  );
}

export function ItemPostingSearchHeader(props) {
  const [universityOptions, setUniversityOptions] = useState([]);

  useEffect(() => {
    async function getUniversities() {
        const res = await GET("university");
        const options = [{ value: '*', label: "All Universities" }].concat(res.map(d => ({ value: d.id, label: d.name })));
        setUniversityOptions(options)
    }
    getUniversities();
  }, []);

  const handleUniversitySelect = (option) => {
    props.onUniversitySelect(option);
  }

  return (
    <div className="mx-auto my-4 d-flex">
      <Select
        className="basic-single w-25 mx-2"
        classNamePrefix="select"
        isLoading={universityOptions.length === 1}
        isSearchable={true}
        defaultValue={{ value: '*', label: "All Universities" }}
        noOptionsMessage={() => "University not found"}
        name="university"
        options={universityOptions}
        onChange={handleUniversitySelect}
      />
    </div>
  );
}