package domain.dto;

import java.util.List;
/**
 * @author Alex
 * OrderDTO class
 **/
public class OrderDTO {
    private long id;
    private String userEmail;
    private List<ItemDTO> items;
    private boolean dispatched;
    private AddressInformationDTO addressInformationDTO;
    private String expectedArrival;
    private boolean paid;

    public OrderDTO() {
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

    public List<ItemDTO> getItems() {
        return items;
    }

    public void setItems(List<ItemDTO> items) {
        this.items = items;
    }

    public boolean isDispatched() {
        return dispatched;
    }

    public void setDispatched(boolean dispatched) {
        this.dispatched = dispatched;
    }

    public AddressInformationDTO getAddressInformationDTO() {
        return addressInformationDTO;
    }

    public void setAddressInformationDTO(AddressInformationDTO addressInformationDTO) {
        this.addressInformationDTO = addressInformationDTO;
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

    @Override
    public String toString() {
        return "{" +
                "id=" + id +
                ", userEmail='" + userEmail + '\'' +
                ", items=" + items +
                ", dispatched=" + dispatched +
                ", addressInformationDTO=" + addressInformationDTO.toString() +
                ", expectedArrival='" + expectedArrival + '\'' +
                ", paid=" + paid +
                '}';
    }
}
