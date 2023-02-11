package ca.mcgill.ecse428.unitrade.unitradebackend.model;
import jakarta.persistence.*;

@Entity
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
}