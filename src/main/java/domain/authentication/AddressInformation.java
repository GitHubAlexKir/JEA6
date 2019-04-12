package domain.authentication;

import domain.dto.AddressInformationDTO;

import javax.persistence.*;
import java.io.Serializable;

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
}
