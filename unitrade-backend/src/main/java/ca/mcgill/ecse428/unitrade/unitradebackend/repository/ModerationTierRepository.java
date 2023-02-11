package ca.mcgill.ecse428.unitrade.unitradebackend.repository;

import ca.mcgill.ecse428.unitrade.unitradebackend.model.ModerationTier;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ModerationTierRepository extends JpaRepository<ModerationTier, Long>{
    
}
