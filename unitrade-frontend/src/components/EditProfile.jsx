import { useState, useEffect } from 'react';
import styled from 'styled-components';
import Card from 'react-bootstrap/Card';
import Button from 'react-bootstrap/Button';
import Form from 'react-bootstrap/Form';
import Container from 'react-bootstrap/Container';
import { GET, POST, PUT } from '../utils/client';
import ErrorToast from '../components/toasts/ErrorToast';
import SuccessToast from '../components/toasts/SuccessToast';
import { Image } from 'react-bootstrap';
import personIcon from '../assets/person-icon.svg';
import { useAuth } from './AuthProvider';

const EditProfileStyle = styled.div`
  .edit-profile-container {
  }
`;

export function EditProfile() {
  const { auth, user, loading } = useAuth();
  const [person, setPerson] = useState(null);

  const [error, setError] = useState('');
  const [showError, setShowError] = useState(false);

  const [success, setSuccess] = useState('');
  const [showSuccess, setShowSuccess] = useState(false);

  const [universityOptions, setUniversityOptions] = useState([]);
  const [courseOptions, setCourseOptions] = useState([]);

  const [firstName, setFirstName] = useState('');
  const [lastName, setLastName] = useState('');
  const [profilePicture, setProfilePicture] = useState('');
  const [universityId, setUniversityId] = useState(null);
  const [courseIds, setCourseIds] = useState([]);

  const handleSubmit = async (event) => {
    event.preventDefault();

    try {
      let res = await PUT(`person`, {
        ...user,
        profilePicture,
        firstName,
        lastName,
      });

      if (typeof res === 'string') {
        setError(res);
        setShowError(true);
        return;
      }

      let hi = await PUT(`person/universityId`, {
        ...user,
        universityId,
      });

      await PUT(`person/enrolledCourses`, {
        ...user,
        universityId,
        enrolledCourseIds: courseIds,
      });
    } catch (err) {
      setError(err);
      setShowError(true);
    }

    setSuccess('Profile updated successfully!');
    setShowSuccess(true);
  };

  const handleCloseError = () => setShowError(false);
  const handleCloseSuccess = () => setShowSuccess(false);

  // First loading of the page
  useEffect(() => {
    if (user) {
      setCourseIds(user.enrolledCourses.map((course) => course.id));
      setUniversityId(user.university ? user.university.id : 0);
      setProfilePicture(user.profilePicture);
      setFirstName(user.firstName);
      setLastName(user.lastName);

      // Get the university and course options
      async function getUniversityOptions() {
        setUniversityOptions(await GET('university'));
      }

      async function getCourseOptions() {
        if (universityId == null) setCourseOptions([]);
        else setCourseOptions(await GET(`course/university/${universityId}`));
      }

      getUniversityOptions();
      getCourseOptions();
    }
  }, [user]);

  // When the university changes, update the course options
  useEffect(() => {
    async function getCourseOptions() {
      if (universityId == null) setCourseOptions([]);
      else setCourseOptions(await GET(`course/university/${universityId}`));
    }

    getCourseOptions();
  }, [universityId]);

  const handleChangeUniversity = (event) => {
    setUniversityId(event.target.value);
    setCourseIds([]);
  };

  const handleChangeCourses = (event) => {
    let selectedCourse = Number(event.target.value);

    if (courseIds.includes(selectedCourse)) {
      setCourseIds(courseIds.filter((course) => course !== selectedCourse));
    } else {
      setCourseIds([...courseIds, selectedCourse]);
    }
  };

  const handleChangeProfilePicture = (event) => {
    setProfilePicture(event.target.value);
  };

  const handleChangeFirstName = (event) => {
    setFirstName(event.target.value);
  };

  const handleChangeLastName = (event) => {
    setLastName(event.target.value);
  };

  if (user)
    return (
      <EditProfileStyle>
        <SuccessToast
          message={success}
          onClose={handleCloseSuccess}
          show={showSuccess}
        />
        <ErrorToast
          message={error}
          onClose={handleCloseError}
          show={showError}
        />
        <Container className="edit-profile-container">
          <Card border="light">
            <Card.Body
              style={{
                display: 'flex',
                flexDirection: 'column',
                alignItems: 'center',
              }}
            >
              <Card.Title className="text-center login-title">
                <b>Edit Profile</b>
              </Card.Title>

              <Form onSubmit={handleSubmit} style={{ width: '80%' }}>
                <Container
                  style={{
                    display: 'flex',
                    gap: 20,
                    marginBottom: 20,
                    marginLeft: -20,
                  }}
                >
                  <Container style={{ width: 200, height: 200 }}>
                    <Image
                      src={profilePicture ? profilePicture : personIcon}
                      onError={(e) => {
                        e.target.onerror = null;
                        e.target.src = personIcon;
                      }}
                      style={{
                        width: 200,
                        height: 200,
                        cursor: 'pointer',
                        objectFit: 'cover',
                      }}
                    />
                  </Container>

                  <Container
                    style={{ display: 'flex', flexDirection: 'column' }}
                  >
                    <p
                      style={{
                        width: '100%',
                      }}
                    >
                      Username: {user.username}
                    </p>
                    <p
                      style={{
                        width: '100%',
                      }}
                    >
                      Email: {user.email}
                    </p>
                  </Container>
                </Container>

                <Form.Group className="mb-3" controlId="formBasicName">
                  <Form.Label>Profile Picture Link</Form.Label>
                  <Form.Control
                    type="text"
                    placeholder="Profile Picture Link"
                    value={profilePicture || ''}
                    onChange={handleChangeProfilePicture}
                  />
                </Form.Group>

                <Form.Group className="mb-3" controlId="formBasicName">
                  <Form.Label>First Name</Form.Label>
                  <Form.Control
                    type="text"
                    placeholder="First Name"
                    value={firstName || ''}
                    onChange={handleChangeFirstName}
                  />
                </Form.Group>

                <Form.Group className="mb-3" controlId="formBasicName">
                  <Form.Label>Last Name</Form.Label>
                  <Form.Control
                    type="text"
                    placeholder="Last Name"
                    value={lastName || ''}
                    onChange={handleChangeLastName}
                  />
                </Form.Group>

                <Form.Group className="mb-3">
                  <Form.Label>University</Form.Label>
                  <Form.Select
                    onChange={handleChangeUniversity}
                    value={universityId || 0}
                  >
                    <option value={0} disabled>
                      No university selected
                    </option>
                    {universityOptions.map((uni) => (
                      <option value={uni.id} key={uni.id}>
                        {uni.name}, {uni.city}
                      </option>
                    ))}
                  </Form.Select>
                </Form.Group>
                <Form.Group
                  className="mb-3 checkboxes"
                  onChange={handleChangeCourses}
                >
                  <Form.Label>Courses</Form.Label>
                  {Array.isArray(courseOptions) &&
                    courseOptions.map((crs) => (
                      <Form.Check
                        type="checkbox"
                        label={`${crs.codename} : ${crs.title}`}
                        value={crs.id}
                        key={crs.id}
                        checked={courseIds.includes(crs.id)}
                        onChange={handleChangeCourses}
                      ></Form.Check>
                    ))}
                </Form.Group>
                <Button
                  variant="dark"
                  type="submit"
                  className="update-profile-information"
                >
                  Update Profile
                </Button>
              </Form>
            </Card.Body>
          </Card>
        </Container>
      </EditProfileStyle>
    );
}
