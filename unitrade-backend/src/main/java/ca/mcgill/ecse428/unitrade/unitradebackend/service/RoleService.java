package ca.mcgill.ecse428.unitrade.unitradebackend.service;

import java.util.List;
import java.util.ArrayList;

import ca.mcgill.ecse428.unitrade.unitradebackend.model.Role;
import ca.mcgill.ecse428.unitrade.unitradebackend.model.University;
import ca.mcgill.ecse428.unitrade.unitradebackend.model.Role.ModerationRole;
import ca.mcgill.ecse428.unitrade.unitradebackend.model.Person;
import ca.mcgill.ecse428.unitrade.unitradebackend.repository.RoleRepository;
import ca.mcgill.ecse428.unitrade.unitradebackend.repository.UniversityRepository;
import ca.mcgill.ecse428.unitrade.unitradebackend.repository.PersonRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import ca.mcgill.ecse428.unitrade.unitradebackend.exception.ServiceLayerException;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RoleService {

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PersonRepository personRepository;

    @Autowired
    UniversityRepository universityRepository;

    @Transactional
    public Role createRole(
        Long requesterId,
        Long newModId,
        Long universityId,
        ModerationRole role
    ) {
        if (requesterId == newModId) {
            throw new ServiceLayerException(HttpStatus.BAD_REQUEST, "Requester and new moderator cannot be the same person");
        }

        if (newModId == null){
            throw new ServiceLayerException(HttpStatus.BAD_REQUEST, "Person Id cannot be null");
        }

        if (universityId == null){
            throw new ServiceLayerException(HttpStatus.BAD_REQUEST, "University Id cannot be null");
        }

        if (role == null){
            throw new ServiceLayerException(HttpStatus.BAD_REQUEST, "Role cannot be null");
        }

        Person newMod = personRepository.findById(newModId).orElse(null);
        if (newMod == null) {
            throw new ServiceLayerException(HttpStatus.NOT_FOUND, 
                    String.format("Person with id %d not found", newModId));
        }

        University university = universityRepository.findById(universityId).orElse(null);
        if (university == null) {
            throw new ServiceLayerException(HttpStatus.NOT_FOUND, 
                    String.format("University with id %d not found", universityId));
        }

        if (role == ModerationRole.HELPER) {
            Person requester = personRepository.findById(requesterId).orElse(null);
            if (requester == null) {
                throw new ServiceLayerException(HttpStatus.NOT_FOUND, 
                        String.format("Person with id %d not found", requesterId));
            }

            try {
                Role r = getRoleFromPersonAndUniversity(requesterId, universityId);
                if (r.getRole() != ModerationRole.ADMINISTRATOR) {
                    throw new ServiceLayerException(HttpStatus.FORBIDDEN, String.format(
                            "Requester with id %d is not an admin of university with id %d", requesterId, universityId));
                }
            } catch (ServiceLayerException e) {
                throw new ServiceLayerException(HttpStatus.FORBIDDEN, String.format(
                        "Requester with id %d is not a admin of university with id %d", requesterId, universityId));
            }
        }

        Role newRole = new Role();
        newRole.setPerson(newMod);
        newRole.setUniversity(university);
        newRole.setRole(role);
        return roleRepository.save(newRole);
    }

    @Transactional
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    @Transactional
    public Role getRoleFromPerson(Long personId){ // should we return a modrole or role

        if (personId == null){
            throw new ServiceLayerException(HttpStatus.BAD_REQUEST, "Person cannot be null"); 
        }

        Person person = personRepository.findById(personId).orElse(null);
        if (person == null) {
            throw new ServiceLayerException(HttpStatus.NOT_FOUND, 
                    String.format("Person with id %d not found", personId));
        }

        Role role = roleRepository.findByPerson(person);

       // ModerationRole modRole = role.getRole();
        roleRepository.save(role);

        return role;

    }

    @Transactional
    public Role getRole(Long id){

        if (id == null){
            throw new ServiceLayerException(HttpStatus.BAD_REQUEST, "Id cannot be null");
        }

        Role role = roleRepository.findById(id).orElse(null);
        if (role == null) {
            throw new ServiceLayerException(HttpStatus.NOT_FOUND, 
                    String.format("Role with id %d not found", id));
        }
        return role; //.getRole();

    }

    @Transactional
    public Role getRoleFromPersonAndUniversity(Long personId, Long universityId) {
        if (personId == null){
            throw new ServiceLayerException(HttpStatus.BAD_REQUEST, "Person Id cannot be null");
        }

        if (universityId == null){
            throw new ServiceLayerException(HttpStatus.BAD_REQUEST, "University Id cannot be null");
        }

        Person person = personRepository.findById(personId).orElse(null);
        if (person == null) {
            throw new ServiceLayerException(HttpStatus.NOT_FOUND, 
                    String.format("Person with id %d not found", personId));
        }

        University university = universityRepository.findById(universityId).orElse(null);
        if (university == null) {
            throw new ServiceLayerException(HttpStatus.NOT_FOUND, 
                    String.format("University with id %d not found", universityId));
        }

        Role role = roleRepository.findByPersonAndUniversity(person, university);
        if (role == null) {
            throw new ServiceLayerException(HttpStatus.NOT_FOUND, 
                    String.format("Role with person id %d and university id %d not found", personId, universityId));
        }
        return role;
    }


    @Transactional
    public Role updateRole(Long personId, ModerationRole modRole){
        if (personId == null){
            throw new ServiceLayerException(HttpStatus.BAD_REQUEST, "Person cannot be null");
        }

        if (modRole == null){
            throw new ServiceLayerException(HttpStatus.BAD_REQUEST, "Moderation Role cannot be null");
        }

        Role role = roleRepository.findByPerson(personRepository.findById(personId));
        role.setRole(modRole);
        return roleRepository.save(role);
    }

    @Transactional
    public List<Person> getAllPersonsWithRole(Long roleId){
        List<Person> allPersons = personRepository.findAll();
        List<Person> allPersonsWithRole = new ArrayList<Person>();
        Role role = roleRepository.findById(roleId).get();

        for (int i = 0; i < allPersons.size(); i++){
            Person person  = allPersons.get(i);
            Role personRole = roleRepository.findByPerson(person);
            if (personRole == role){
                allPersonsWithRole.add(person);
            }

        }

        return allPersonsWithRole;

    } 
    
}