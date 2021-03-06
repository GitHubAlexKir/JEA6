package domain.invoice;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.json.JSONObject;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
/**
 * @author Alex
 * Invoice entity
 **/
@Entity
public class Invoice implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "order_id",nullable=false, unique = true)
    private long orderId;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    @Column(nullable=false)
    private Date transaction_time;
    @Column(nullable=false)
    private double total;

    public Invoice() {
    }
    //Getters en Setters
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public Date getTransaction_time() {
        return transaction_time;
    }

    public void setTransaction_time(Date transaction_time) {
        this.transaction_time = transaction_time;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public JSONObject toJSONObject() {
        JSONObject response = new JSONObject();
        response.put("id", this.id);
        response.put("orderId", this.orderId);
        response.put("transaction_time",this.transaction_time);
        response.put("total", this.total);
        return response;
    }
    @Override
    public String toString() {
        return "Invoice\n" +
                "id=" + id + "\n" +
                "orderId=" + orderId + "\n" +
                "transaction_time=" + transaction_time + "\n" +
                "total=" + total;
    }
}
