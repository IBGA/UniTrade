package ca.mcgill.ecse428.unitrade.unitradebackend.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Unipost extends Post{
    
    @OneToMany private List<Unipost> replies;

    public List<Unipost> getReplies() {
        return replies;
    }

    public void setReplies(List<Unipost> replies) {
        this.replies = replies;
    }
}
