package domain.order;

import com.google.gson.Gson;
import domain.authentication.AddressInformation;
import domain.dto.ItemDTO;
import domain.dto.OrderDTO;
import domain.item.Item;
import org.json.JSONObject;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="UserOrder")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false,name = "user_email")
    private String userEmail;
    @OneToMany(fetch=FetchType.EAGER)
    private List<Item> items  = new ArrayList<Item>();
    @OneToOne(cascade = CascadeType.PERSIST)
    private AddressInformation addressInformation;
    @Column(nullable = false)
    private boolean dispatched;

    public Order() {
    }
    public Order(OrderDTO orderDTO) {
        this.userEmail = orderDTO.getUserEmail();
        this.dispatched = orderDTO.isDispatched();
        List<Item> convertedItems = new ArrayList<>();
        for (ItemDTO itemDTO:orderDTO.getItems()
             ) {
            convertedItems.add(new Item(itemDTO));
        }
        this.items = convertedItems;
        this.addressInformation = new AddressInformation(orderDTO.getAddressInformationDTO());
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public boolean isDispatched() {
        return dispatched;
    }

    public void setDispatched(boolean dispatched) {
        this.dispatched = dispatched;
    }

    public AddressInformation getAddressInformation() {
        return addressInformation;
    }

    public void setAddressInformation(AddressInformation addressInformation) {
        this.addressInformation = addressInformation;
    }

    public JSONObject toMap() {
        JSONObject response = new JSONObject();
       response.put("id", this.id);
        response.put("userEmail", this.userEmail);
        response.put("items",this.items);
        response.put("dispatched", this.dispatched);
        response.put("addressInformation",this.addressInformation.toMap());
        return response;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", userEmail='" + userEmail + '\'' +
                ", items=" + items +
                ", addressInformation=" + addressInformation.toString() +
                ", dispatched=" + dispatched +
                '}';
    }
}