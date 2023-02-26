package ca.mcgill.ecse428.unitrade.unitradebackend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import ca.mcgill.ecse428.unitrade.unitradebackend.model.Person;
import ca.mcgill.ecse428.unitrade.unitradebackend.repository.PersonRepository;

@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    @Autowired private PersonRepository personRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Person person = personRepository.findByEmail(email);
        if (person == null) throw new UsernameNotFoundException("User does not exist or is not available.");
        if (!person.isEnabled()) throw new UsernameNotFoundException("User does not exist or is not available.");
        
        String[] roles = {"USER"}; // Default logged-in user role

        UserDetails userDetails = 
            User.withUsername(person.getEmail())
                .password(person.getPassword())
                .roles(roles)
                .build();
        return userDetails;
    }
    
}
