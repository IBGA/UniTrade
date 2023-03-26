package ca.mcgill.ecse428.unitrade.unitradebackend.controller;


import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import ca.mcgill.ecse428.unitrade.unitradebackend.exception.ServiceLayerException;
import ca.mcgill.ecse428.unitrade.unitradebackend.security.CustomUserDetails;

public class ControllerHelper {

    public static Long getAuthenticatedUserId() throws ServiceLayerException {

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            return ((CustomUserDetails) principal).getId();
        } else {
            throw new ServiceLayerException(HttpStatus.EXPECTATION_FAILED, "Could not get auth id. This error should never occur normally");
        }
    }
}