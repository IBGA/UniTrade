package ca.mcgill.ecse428.unitrade.unitradebackend.repository;

import ca.mcgill.ecse428.unitrade.unitradebackend.model.Role;
import ca.mcgill.ecse428.unitrade.unitradebackend.model.University;
import ca.mcgill.ecse428.unitrade.unitradebackend.model.Person;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long>{

    public Role findByPerson(Person person);

	public Role findByPerson(Optional<Person> findById);

    public Role findByPersonAndUniversity(Person person, University university);
}
