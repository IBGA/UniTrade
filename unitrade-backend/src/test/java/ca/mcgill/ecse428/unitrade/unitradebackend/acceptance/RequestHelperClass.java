package ca.mcgill.ecse428.unitrade.unitradebackend.acceptance;

import java.util.Base64;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

public class RequestHelperClass {

    private static <T, U> ResponseEntity<T> commitRequest(String url, Class<T> responseType, HttpMethod method, U body, boolean authenticate) throws RestClientException {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();

        if (authenticate) {
            headers.set("Authorization",
                    "Basic " + Base64.getEncoder().encodeToString(("testEmail" + ":" + "testPassword").getBytes()));
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

    public static <T> ResponseEntity<T> get(String url, Class<T> responseType, boolean authenticate) throws RestClientException {
        return commitRequest(url, responseType, HttpMethod.GET, null, authenticate);
    }

    public static <T, U> ResponseEntity<T> post(String url, Class<T> responseType, U body, boolean authenticate) throws RestClientException {
        return commitRequest(url, responseType, HttpMethod.POST, body, authenticate);
    }

    public static <T, U> ResponseEntity<T> put(String url, Class<T> responseType, U body, boolean authenticate) throws RestClientException {
        return commitRequest(url, responseType, HttpMethod.PUT, body, authenticate);
    }

    public static ResponseEntity<Object> delete(String url, boolean authenticate) throws RestClientException {
        return commitRequest(url, Object.class, HttpMethod.DELETE, null, authenticate);
    }
}