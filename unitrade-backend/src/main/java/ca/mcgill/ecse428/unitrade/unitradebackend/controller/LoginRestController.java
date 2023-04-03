package ca.mcgill.ecse428.unitrade.unitradebackend.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@CrossOrigin(origins = "${allowed.origins}")
@RestController
@ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Course created"),
        @ApiResponse(responseCode = "400", description = "Invalid input"),
        @ApiResponse(responseCode = "404", description = "Referenced resource not found"),
        @ApiResponse(responseCode = "409", description = "Unique constraint violation")
})
public class LoginRestController {

    @PostMapping("/login")
    public void login(HttpServletRequest request) {
        request.getSession();  // create new session (or get current one)
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/authenticated")
    public void authenticated() {}

    @PostMapping("/logout")
    public void logout(HttpSession session) {
        session.invalidate();  // Invalidate session
    }
}
