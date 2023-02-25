package ca.mcgill.ecse428.unitrade.unitradebackend.dto.Request;

public class ItemPostingRequestDto extends PostRequestDto{
    
    private boolean isAvailable;
    private Double price;
    private Long buyerId;

    public Long getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(Long buyerId) {
        this.buyerId = buyerId;
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
