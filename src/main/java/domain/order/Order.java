package domain.order;

import domain.authentication.AddressInformation;
import domain.dto.ItemDTO;
import domain.dto.OrderDTO;
import domain.item.Item;
import org.json.JSONObject;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
/**
 * @author Alex
 * Order entity met andere tabelnaam vanwege MySQL syntax
 **/
@Entity
@Table(name = "UserOrder")
public class Order implements Serializable {
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
    @Column(name = "expected_arrival")
    private String expectedArrival;
    @Column(nullable = false)
    private boolean paid;

    public Order() {
    }

    public Order(OrderDTO orderDTO) {
        this.userEmail = orderDTO.getUserEmail();
        this.dispatched = orderDTO.isDispatched();
        //ItemDTOs van OrderDTO converteren naar Items
        List<Item> convertedItems = new ArrayList<>();
        for (ItemDTO itemDTO:orderDTO.getItems()
             ) {
            convertedItems.add(new Item(itemDTO));
        }
        this.items = convertedItems;
        this.expectedArrival = "";
        this.paid = orderDTO.isPaid();
        this.addressInformation = new AddressInformation(orderDTO.getAddressInformationDTO());
    }
    //Getters en Setters
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
    public String getExpectedArrival() {
        return expectedArrival;
    }

    public void setExpectedArrival(String expectedArrival) {
        this.expectedArrival = expectedArrival;
    }

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    //Totale bedrag van alle items in order
    public double getTotal(){
        double total = 0.00;
        for (Item i:this.items
             ) {
            total+=i.getPrice();
        }
        return total;
    }
    // Order converteren naar JSONObject.
    // dit is vanwege een bug in de verouderde versie van Jersey in glassfish.
    // Andere oplossing is Gson maar met dit heb je meer controle erover.
    public JSONObject toJSONObject() {
        JSONObject response = new JSONObject();
       response.put("id", this.id);
        response.put("userEmail", this.userEmail);
        response.put("items",this.items);
        response.put("dispatched", this.dispatched);
        response.put("addressInformation",this.addressInformation.toJSONObject());
        response.put("expectedArrival", this.expectedArrival);
        response.put("paid", this.paid);
        return response;
    }
    // toString override voor logging/debugging
    @Override
    public String toString() {
        return "{" +
                "id=" + id +
                ", userEmail='" + userEmail + '\'' +
                ", items=" + items +
                ", addressInformation=" + addressInformation.toString() +
                ", dispatched=" + dispatched +
                ", expectedArrival='" + expectedArrival + '\'' +
                ", paid=" + paid +
                '}';
    }
}
