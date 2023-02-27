import React, { useState, useEffect  } from "react";
import Card from 'react-bootstrap/Card';
import Container from 'react-bootstrap/Container';
import styled from 'styled-components';
import Select from 'react-select';
import { Form, Dropdown } from 'react-bootstrap';
import Button from "react-bootstrap/Button";

const ItemPostingThumbnailStyle = styled.div`
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
`;

export function ItemPostingThumbnail(props) {
  const { title, description, date, university, person, courses, imageSrc } = props;

  return (
    <ItemPostingThumbnailStyle>
      <Container className="login-container">
        <Card bg="light">
          <Card.Img variant="top" src={imageSrc} />
          <Card.Body>
            <Card.Title>{title}</Card.Title>
            <Card.Subtitle className="mb-2 text-muted">{university}</Card.Subtitle>
            <Card.Subtitle className="mb-2 text-muted">{date}</Card.Subtitle>
            <Card.Subtitle className="mb-2 text-muted">{person}</Card.Subtitle>
            <Card.Text>{description}</Card.Text>
            {courses.length > 0 &&
              <Card.Text>
                <strong>Courses:</strong> {courses}
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
    fetch('http://localhost:8080/university')
      .then(response => response.json())
      .then(data => {
        const options = [{ value: '*', label: "All Universities" }].concat(data.map(d => ({ value: d.id, label: d.name })));
        setUniversityOptions(options);
      })
      .catch(error => console.error(error))
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