package ca.mcgill.ecse428.unitrade.unitradebackend.model;

import jakarta.persistence.*;

@Entity
public class ItemPosting extends Post{

    private boolean isAvailable;

    public Person getBuyer() {
        return buyer;
    }

    public void setBuyer(Person buyer) {
        this.buyer = buyer;
    }

    @OneToOne private Person buyer;

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }
}
