import { AssignModerator } from '../components/AssignModerator.jsx';
import { Container } from 'react-bootstrap';

export function AssignModeratorPage() {
  return (
    <Container>
      <h1>Assign Moderators for </h1>
      <hr/>
      <AssignModerator />
    </Container>
  );
}