package ca.mcgill.ecse428.unitrade.unitradebackend.repository;

import ca.mcgill.ecse428.unitrade.unitradebackend.model.ItemPosting;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemPostingRepository extends JpaRepository<ItemPosting, Long>{
}
