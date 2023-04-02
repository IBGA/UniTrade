import React, { useState, useEffect } from 'react';
import { Container, Table, Button } from 'react-bootstrap';
import { DELETE, GET, POST }  from '../utils/client';
import { useAuth } from "./AuthProvider";
import styled from 'styled-components';
import Alert from 'react-bootstrap/Alert';
import SuccessToast from '../components/toasts/SuccessToast';
import ErrorToast from '../components/toasts/ErrorToast';

const AssignModeratorStyle = styled.div`
`;

export function AssignModerator() {
  const { user } = useAuth();
  const [data, setData] = useState(null);
  const [universityName, setUniversityName] = useState(null);
  const [universityId, setUniversityId] = useState(null);
  const [errorMessage, setErrorMessage] = useState(null);
  const [error, showError] = useState(false);
  const [helpers, setHelpers] = useState([]);
  const [nonHelpers, setNonHelpers] = useState([]);
  const [success, setSuccess] = useState('');
  const [showSuccess, setShowSuccess] = useState(false);

  const handleCloseSuccess = () => setShowSuccess(false);

  async function fetchData() {
    const res = await GET(`person/check/admin/or/helper`);
    if (typeof res === "string" || res.error){
      setErrorMessage(res.error == undefined ? res.toString() : res.error.toString() )
      showError(true)
      console.log("Error: " + res.error);
      return;
    }
    setData(res);
    const res2 = await GET(`person/self`);
    if (typeof res2 === "string" || res2.error){
      setErrorMessage(res2.error == undefined ? res2.toString() : res2.error.toString() )
      showError(true)
      console.log("Error: " + res2.error);
      return;
    }
    setUniversityName(res2.university.name);
    setUniversityId(res2.university.id);

    const res3 = await GET(`person/non-helper/from/university/${res2.university.id}`);

    if (typeof res3 === "string" || res3.error){
      setErrorMessage(res3.error == undefined ? res3.toString() : res3.error.toString() )
      showError(true)
      console.log("Error: " + res3.error);
      return;
    }

    setNonHelpers(res3);

    const res4 = await GET(`person/helper/from/university/${res2.university.id}`);

    if (typeof res4 === "string" || res4.error){
      setErrorMessage(res4.error == undefined ? res4.toString() : res4.error.toString() )
      showError(true)
      console.log("Error: " + res4.error);
      return;
    }
    setHelpers(res4);
  }

  function handleDemoteModerator(username) {
    async function demoteModerator() {
      const res = await DELETE(`role/person/${username}/university/${universityId}`)
      if (typeof res === "string" || res.error){
        setErrorMessage(res.error == undefined ? res.toString() : res.error.toString() )
        showError(true)
        console.log("Error: " + res.error);
        return;
      }
      setSuccess('Helper demoted successfully!');
      setShowSuccess(true);
      fetchData();
    }
    demoteModerator();
  }

  function handleAssignModerator(username) {
    async function assignModerator() {

      var body = {
        "personUsername": username,
        "universityId": universityId
      }

      const res = await POST(`role/helper`, body, true);
      if (typeof res === "string" || res.error){
        setErrorMessage(res.error == undefined ? res.toString() : res.error.toString() )
        showError(true)
        console.log("Error: " + res.error);
        return;
      }
      setSuccess('Moderator assigned successfully!');
      setShowSuccess(true);
      fetchData();
    }
    assignModerator();
  }

  useEffect(() => {
    fetchData();
  }, []);

  return (
    <AssignModeratorStyle>
      <SuccessToast
          message={success}
          onClose={handleCloseSuccess}
          show={showSuccess}
      />
      <Container>
        {errorMessage ? (
          <Alert variant="danger">
            <Alert.Heading>You can't assign helpers</Alert.Heading>
            <hr />
            <p>
              {errorMessage}
            </p>
          </Alert>
        ) : (
          <div>
            <h1>Assign Helpers for <b>{universityName}</b></h1>
            <hr/>
            <h2>Current Helpers</h2>
            <Table striped bordered hover responsive>
              <thead>
                <tr>
                  <th>Username</th>
                  <th>First Name</th>
                  <th>Email</th>
                  <th col={1}>Action</th>
                </tr>
              </thead>
              <tbody>
                {helpers && helpers.map((user) => (
                  <tr key={user.username}>
                    <td>{user.username}</td>
                    <td>{user.firstName}</td>
                    <td>{user.email}</td>
                    <td>
                      <Button variant="danger" onClick={() => handleDemoteModerator(user.username)}>Demote Helper</Button>
                    </td>
                  </tr>
                ))}
              </tbody>
            </Table>
            <h2>Students</h2>
            <Table striped bordered hover responsive>
              <thead>
                <tr>
                  <th>Username</th>
                  <th>First Name</th>
                  <th>Email</th>
                  <th col={1}>Action</th>
                </tr>
              </thead>
              <tbody>
                {nonHelpers && nonHelpers.map((user) => (
                  <tr key={user.username}>
                    <td>{user.username}</td>
                    <td>{user.firstName}</td>
                    <td>{user.email}</td>
                    <td>
                      <Button variant="primary" onClick={() => handleAssignModerator(user.username)}>Assign Moderator</Button>
                    </td>
                  </tr>
                ))}
              </tbody>
            </Table>
          </div>
        )}
      </Container>
    </AssignModeratorStyle>
  );
}
