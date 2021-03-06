package domain.item;

import domain.dto.ItemDTO;
import domain.dto.ReviewDTO;
import org.json.JSONObject;
import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
/**
 * @author Alex
 * Item entity
 **/
@Entity
public class Item implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "product_number",nullable=false, unique = true)
    private long productNumber;
    @Column(nullable=false)
    private double price;
    @Column(name = "product_name",nullable=false)
    private String productName;
    @Column(nullable = false)
    private int stock;
    @Column(name = "warehouse_location",nullable = false)
    private String warehouseLocation;
    @OneToMany(fetch=FetchType.EAGER,cascade = CascadeType.PERSIST)
    private List<Review> reviews  = new ArrayList<Review>();

    public Item() {
    }

    public Item(ItemDTO itemDTO) {
        try {
            this.id = itemDTO.getId();
            //ReviewDTOs converteren naar Reviews
            List<Review> convertedReviews = new ArrayList<>();
            for (ReviewDTO reviewDTO:itemDTO.getReviews()
            ) {
                convertedReviews.add(new Review(reviewDTO));
            }
            this.reviews = convertedReviews;
        }catch (NullPointerException e){
            //New item, dont set id and reviews
        }

        this.price = itemDTO.getPrice();
        this.productName = itemDTO.getProductName();
        this.productNumber = itemDTO.getProductNumber();
        this.stock = itemDTO.getStock();
        this.warehouseLocation = itemDTO.getWarehouseLocation();
    }

    //Getters en Setters
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getProductNumber() {
        return productNumber;
    }

    public void setProductNumber(long productNumber) {
        this.productNumber = productNumber;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getWarehouseLocation() {
        return warehouseLocation;
    }

    public void setWarehouseLocation(String warehouseLocation) {
        this.warehouseLocation = warehouseLocation;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public JSONObject toJSONObject() {
        JSONObject response = new JSONObject();
        response.put("id", this.id);
        response.put("price", this.price);
        response.put("productNumber",this.productNumber);
        response.put("productName",this.productName);
        response.put("stock",this.stock);
        response.put("warehouseLocation",this.warehouseLocation);
        response.put("reviews",this.reviews);
        return response;
    }

    @Override
    public String toString() {
        return "{" +
                "id=" + id +
                ", productNumber=" + productNumber +
                ", price=" + price +
                ", productName='" + productName + '\'' +
                ", stock=" + stock +
                ", warehouseLocation='" + warehouseLocation + '\'' +
                '}';
    }
}
