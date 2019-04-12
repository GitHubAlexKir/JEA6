package domain.dto;



public class ItemDTO {
    private long id;
    private long productNumber;
    private double price;
    private String productName;
    private int stock;

    public ItemDTO() {
    }

    public ItemDTO(long productNumber, double price, String productName, int stock) {
        this.productNumber = productNumber;
        this.price = price;
        this.productName = productName;
        this.stock = stock;
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

    @Override
    public String toString() {
        return "ItemDTO{" +
                "id=" + id +
                ", productNumber=" + productNumber +
                ", price=" + price +
                ", productName='" + productName + '\'' +
                ", stock=" + stock +
                '}';
    }
}
