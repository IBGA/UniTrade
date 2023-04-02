package ca.mcgill.ecse428.unitrade.unitradebackend.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import jakarta.persistence.*;

@Entity
@DiscriminatorValue("ItemPosting")
public class ItemPosting extends Post{

    private boolean isAvailable;
    private Double price;
    @OneToOne(cascade=CascadeType.DETACH)
    //@OnDelete(action = OnDeleteAction.CASCADE)
    private Person buyer;

    public Person getBuyer() {
        return buyer;
    }

    public void setBuyer(Person buyer) {
        this.buyer = buyer;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
