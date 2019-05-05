package domain.authentication;

import domain.dto.AddressInformationDTO;
import org.json.JSONObject;

import javax.persistence.*;
import java.io.Serializable;
/**
 * @author Alex
 * AddressInformation class voor gegevens voor verzending
 **/
@Entity
@Cacheable(false)
public class AddressInformation implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable=false)
    private String addressee;
    @Column(nullable=false)
    private String address;
    @Column(nullable=false)
    private String zip;
    @Column(nullable=false)
    private String city;

    public AddressInformation() {
    }

    public AddressInformation(AddressInformationDTO addressInformationDTO) {
        try{
            this.id = addressInformationDTO.getId();
        }catch (NullPointerException e){
            //new AddressInformation
        }
        this.addressee = addressInformationDTO.getAddressee();
        this.address = addressInformationDTO.getAddress();
        this.zip = addressInformationDTO.getZip();
        this.city = addressInformationDTO.getCity();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAddressee() {
        return addressee;
    }

    public void setAddressee(String addressee) {
        this.addressee = addressee;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return "AddressInformation{" +
                "id=" + id +
                ", addressee='" + addressee + '\'' +
                ", address='" + address + '\'' +
                ", zip='" + zip + '\'' +
                ", city='" + city + '\'' +
                '}';
    }

    public JSONObject toJSONObject() {
        JSONObject response = new JSONObject();
        response.put("id", this.id);
        response.put("addressee",this.addressee);
        response.put("address",this.address);
        response.put("zip",this.zip);
        response.put("city",this.city);
        return response;
    }
}
