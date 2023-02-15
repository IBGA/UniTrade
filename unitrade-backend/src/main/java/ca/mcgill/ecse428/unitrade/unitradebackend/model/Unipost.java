package ca.mcgill.ecse428.unitrade.unitradebackend.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Unipost extends Post{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @OneToMany private List<Unipost> replies;

    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    public List<Unipost> getReplies() {
        return replies;
    }

    public void setReplies(List<Unipost> replies) {
        this.replies = replies;
    }
}
