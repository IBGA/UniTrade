package ca.mcgill.ecse428.unitrade.unitradebackend.service;

import java.util.List;
import java.util.Optional;
import java.util.ArrayList;

import ca.mcgill.ecse428.unitrade.unitradebackend.model.Role;
import ca.mcgill.ecse428.unitrade.unitradebackend.model.Role.ModerationRole;
import ca.mcgill.ecse428.unitrade.unitradebackend.model.Person;
import ca.mcgill.ecse428.unitrade.unitradebackend.repository.RoleRepository;
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

    @Transactional
    public Role getRole(Person person){ // should we return a modrole or role
        
        if (person == null){
            throw new ServiceLayerException(HttpStatus.BAD_REQUEST, "Person cannot be null"); 
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