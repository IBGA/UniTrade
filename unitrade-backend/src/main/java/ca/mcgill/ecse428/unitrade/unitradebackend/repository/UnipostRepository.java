package ca.mcgill.ecse428.unitrade.unitradebackend.repository;

import ca.mcgill.ecse428.unitrade.unitradebackend.model.Unipost;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UnipostRepository extends JpaRepository<Unipost, Long>{
    
}
