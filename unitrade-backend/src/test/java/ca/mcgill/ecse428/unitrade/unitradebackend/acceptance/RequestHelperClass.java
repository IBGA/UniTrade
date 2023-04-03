package ca.mcgill.ecse428.unitrade.unitradebackend.acceptance;

import java.util.Base64;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import ca.mcgill.ecse428.unitrade.unitradebackend.dto.Request.PersonRequestDto;
import ca.mcgill.ecse428.unitrade.unitradebackend.dto.Response.PersonResponseDto;

public class RequestHelperClass {

    PersonRequestDto personDto;

    public RequestHelperClass(boolean doCreateAccount) {
        PersonRequestDto personDto = new PersonRequestDto();
        personDto.setEmail("testEmail");
        personDto.setUsername("testUsername");
        personDto.setPassword("testPassword");
        personDto.setFirstName("testFirstName");
        personDto.setLastName("testLastName");
        this.personDto = personDto;
        if (doCreateAccount) this.createAccount();
    }

    public RequestHelperClass(PersonRequestDto personDto, boolean doCreateAccount) {
        this.personDto = personDto;
        if (doCreateAccount) this.createAccount();
    }

    private void createAccount() {
        try {
            (new RequestHelperClass(false)).post("http://localhost:8080/person", PersonResponseDto.class, this.personDto,
            false);
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode().value() != 409) throw e;
        }
    }

    private <T, U> ResponseEntity<T> commitRequest(String url, Class<T> responseType, HttpMethod method, U body, boolean authenticate) throws RestClientException {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();

        if (authenticate) {
            headers.set("Authorization",
                    "Basic " + Base64.getEncoder().encodeToString((personDto.getEmail() + ":" + personDto.getPassword()).getBytes()));
            headers.set("Access-Control-Allow-Credentials", "true");
        }

        HttpEntity<?> entity;
        if (body == null) {
            entity = new HttpEntity<Void>(null, headers);
        } else {
            entity = new HttpEntity<U>(body, headers);
        }

        ResponseEntity<T> response = restTemplate.exchange(
                url,
                method,
                entity,
                responseType);

        return response;
    }

    public <T> ResponseEntity<T> get(String url, Class<T> responseType, boolean authenticate) throws RestClientException {
        return commitRequest(url, responseType, HttpMethod.GET, null, authenticate);
    }

    public <T, U> ResponseEntity<T> post(String url, Class<T> responseType, U body, boolean authenticate) throws RestClientException {
        return commitRequest(url, responseType, HttpMethod.POST, body, authenticate);
    }

    public <T, U> ResponseEntity<T> put(String url, Class<T> responseType, U body, boolean authenticate) throws RestClientException {
        return commitRequest(url, responseType, HttpMethod.PUT, body, authenticate);
    }

    public ResponseEntity<Object> delete(String url, boolean authenticate) throws RestClientException {
        return commitRequest(url, Object.class, HttpMethod.DELETE, null, authenticate);
    }
}