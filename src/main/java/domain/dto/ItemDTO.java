package domain.dto;



public class ItemDTO {
    private long id;
    private long productNumber;
    private double price;
    private String productName;
    private int stock;
    private String warehouseLocation;

    public ItemDTO() {
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
