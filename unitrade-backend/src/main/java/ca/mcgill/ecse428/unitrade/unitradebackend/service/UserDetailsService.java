package ca.mcgill.ecse428.unitrade.unitradebackend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import ca.mcgill.ecse428.unitrade.unitradebackend.model.Person;
import ca.mcgill.ecse428.unitrade.unitradebackend.repository.PersonRepository;
import ca.mcgill.ecse428.unitrade.unitradebackend.security.CustomUserDetails;

@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    @Autowired private PersonRepository personRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Person person = personRepository.findByEmail(email);
        if (person == null || !person.isEnabled()) throw new UsernameNotFoundException("User does not exist or is not available.");

        String[] roles = {"ROLE_USER"}; // Default logged-in user role

        UserDetails userDetails = new CustomUserDetails(
                person.getId(),
                person.getEmail(),
                person.getPassword(),
                AuthorityUtils.createAuthorityList(roles));
        return userDetails;
    }
}
