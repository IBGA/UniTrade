import { Container } from "react-bootstrap"
import { Toast } from "react-bootstrap"

const ErrorToast = ({message, onClose, show}) => {
    return (
        <Container style={{width:1000}}>
        <Toast
        bg="danger"
        position="top-center"
        delay={3000}
        autohide
        show={show}
        onClose={onClose}
        >
            <Toast.Header>
                <strong className="me-auto">Error</strong>
          </Toast.Header>
          <Toast.Body>
            {message}
          </Toast.Body>
        </Toast>
        </Container>
    )
}

export default ErrorToast