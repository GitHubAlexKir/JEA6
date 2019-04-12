package domain.item;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

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


    public Item() {
    }

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
    public Map toMap() {
        java.util.Map<String, String> map = new HashMap<>();
        map.put("id", String.valueOf(this.id));
        map.put("price", String.valueOf(this.price));
        map.put("productNumber",String.valueOf(this.productNumber));
        map.put("productName",this.productName);
        map.put("stock",String.valueOf(this.stock));
        return map;
    }
    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", productNumber=" + productNumber +
                ", price=" + price +
                ", productName='" + productName + '\'' +
                ", stock=" + stock +
                '}';
    }
}
