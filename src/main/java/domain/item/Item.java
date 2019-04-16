package domain.item;

import domain.dto.ItemDTO;

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
    @Column(name = "warehouse_location",nullable = false)
    private String warehouseLocation;


    public Item() {
    }
    public Item(ItemDTO itemDTO) {
        try {
            this.id = itemDTO.getId();
        }catch (NullPointerException e){
            //New item, dont set id
        }
        this.price = itemDTO.getPrice();
        this.productName = itemDTO.getProductName();
        this.productNumber = itemDTO.getProductNumber();
        this.stock = itemDTO.getStock();
        this.warehouseLocation = itemDTO.getWarehouseLocation();
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

    public String getWarehouseLocation() {
        return warehouseLocation;
    }

    public void setWarehouseLocation(String warehouseLocation) {
        this.warehouseLocation = warehouseLocation;
    }

    public Map toMap() {
        java.util.Map<String, String> map = new HashMap<>();
        map.put("id", String.valueOf(this.id));
        map.put("price", String.valueOf(this.price));
        map.put("productNumber",String.valueOf(this.productNumber));
        map.put("productName",this.productName);
        map.put("stock",String.valueOf(this.stock));
        map.put("warehouseLocation",this.warehouseLocation);
        return map;
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
