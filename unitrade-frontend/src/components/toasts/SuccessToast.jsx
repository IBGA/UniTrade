import { Container } from 'react-bootstrap';
import { Toast, ToastContainer } from 'react-bootstrap';

const ErrorToast = ({ message, onClose, show }) => {
  return (
    <ToastContainer position="top-start" style={{ padding: 20 }}>
      <Toast bg="success" delay={3000} autohide show={show} onClose={onClose}>
        <Toast.Header>
          <strong className="me-auto">Success</strong>
        </Toast.Header>
        <Toast.Body>{message}</Toast.Body>
      </Toast>
    </ToastContainer>
  );
};

export default ErrorToast;
