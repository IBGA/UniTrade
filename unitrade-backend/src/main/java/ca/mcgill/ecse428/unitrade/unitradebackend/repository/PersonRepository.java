package ca.mcgill.ecse428.unitrade.unitradebackend.repository;

import ca.mcgill.ecse428.unitrade.unitradebackend.model.Person;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long>{
    public Person findByEmail(String email);
    public Person findByUsername(String username);
}
