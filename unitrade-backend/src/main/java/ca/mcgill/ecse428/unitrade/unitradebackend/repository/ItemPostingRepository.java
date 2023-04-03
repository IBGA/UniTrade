package ca.mcgill.ecse428.unitrade.unitradebackend.repository;

import ca.mcgill.ecse428.unitrade.unitradebackend.model.Course;
import ca.mcgill.ecse428.unitrade.unitradebackend.model.ItemPosting;
import ca.mcgill.ecse428.unitrade.unitradebackend.model.University;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemPostingRepository extends JpaRepository<ItemPosting, Long>{
    public List<ItemPosting> findByUniversity(University university);
    public List<ItemPosting> findByCourses(Course course);
    public List<ItemPosting> findByUniversityAndCourses(University university, Course course);
}
