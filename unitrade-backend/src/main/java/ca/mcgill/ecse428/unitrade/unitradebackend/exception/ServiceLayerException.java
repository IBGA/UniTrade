package ca.mcgill.ecse428.unitrade.unitradebackend.exception;

import org.springframework.http.HttpStatus;

public class ServiceLayerException extends RuntimeException {
    private HttpStatus status;

    public ServiceLayerException(HttpStatus status, String message) {
        super(message);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return this.status;
    }
}
