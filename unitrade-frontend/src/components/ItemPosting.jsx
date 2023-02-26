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
            <Card.Text>
              <strong>Courses:</strong> {courses}
            </Card.Text>
          </Card.Body>
        </Card>
      </Container>
    </ItemPostingThumbnailStyle>
  );
}

export function ItemPostingSearchHeader(props) {
  const {universities, courses} = props;
  const [universityOptions, setUniversityOptions] = useState([]);

  useEffect(() => {
    fetch('http://localhost:8080/university', {
      method: 'GET'
    }).then(response => response.json())
      .then(data => { 
        console.log(data);
        const options = data.map(d => ({ value: d.id, label: d.name }));
        setUniversityOptions(options);
      })
      .catch(error => console.error(error))
    }, []);

  return (
    <div className="mx-auto my-4 d-flex">
      <Select
        className="basic-single w-25 mx-2"
        classNamePrefix="select"
        isLoading={universityOptions.length === 0}
        isClearable={true}
        isSearchable={true}
        noOptionsMessage={() => "University not found"}
        name="university"
        options={universityOptions}
      />
      <p></p>
      <Select
        className="basic-single w-25 mx-2"
        classNamePrefix="select"
        isLoading={false}
        isClearable={true}
        isSearchable={true}
        isDisabled={true}
        noOptionsMessage={() => "Course not found"}
        name="course"
        options={[
          { value: 'chocolate', label: 'Chocolate' },
          { value: 'strawberry', label: 'Strawberry' },
          { value: 'vanilla', label: 'Vanilla' }
        ]}
      />
    </div>
  );
}
