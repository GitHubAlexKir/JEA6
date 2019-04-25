package domain.authentication;

import com.fasterxml.jackson.annotation.JsonFormat;
import domain.dto.UserDTO;
import org.apache.commons.codec.digest.DigestUtils;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

@Entity
@Cacheable(false)
public class User implements Serializable {
          
    @Id
    @Column(unique=true, nullable=false, length=128)
    private String email;
  
    @Column(nullable=false, length=128)
    private String firstName;
      
    @Column(nullable=false, length=128)
    private String lastName;
    @Column(nullable=false, length=128) //sha-512 + hex
    private String password;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    @Column(nullable=false)
    private Date registeredOn;
          
    @ElementCollection(targetClass = Privilege.class)
    @CollectionTable(name = "User_Privilege",
                    joinColumns       = @JoinColumn(name = "email", nullable=false), 
                    uniqueConstraints = { @UniqueConstraint(columnNames={"email","privilege_name"}) } )
    @Enumerated(EnumType.STRING)
    @Column(name="privilege_name", length=64, nullable=false)
    private List<Privilege> privileges;

    @OneToOne(cascade = CascadeType.PERSIST)
    private AddressInformation addressInformation;
     
    public User(){
         
    }
     
    public User(UserDTO user){
         
        if (user.getPassword1() == null || user.getPassword1().length() == 0
                || !user.getPassword1().equals(user.getPassword2()) )
            throw new RuntimeException("Password 1 and Password 2 have to be equal (typo?)");
         
        this.email        = user.getEmail();
        this.firstName    = user.getFirstName();
        this.lastName     = user.getLastName();
        this.password     = DigestUtils.sha512Hex(user.getPassword1() );
        this.registeredOn = new Date();
        this.addressInformation = new AddressInformation(user.getAddressInformationDTO() );
    }
 
    public String getFirstName() {
        return firstName;
    }
 
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
 
    public String getLastName() {
        return lastName;
    }
 
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
 
    public String getEmail() {
        return email;
    }
  
    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }
 
    public void setPassword(String password) {
        this.password = password;
    }
 
    public Date getRegisteredOn() {
        return registeredOn;
    }
 
    public void setRegisteredOn(Date registeredOn) {
        this.registeredOn = registeredOn;
    }

    public AddressInformation getAddressInformation() {
        return addressInformation;
    }

    public void setAddressInformation(AddressInformation addressInformation) {
        this.addressInformation = addressInformation;
    }

    public List<Privilege> getPrivileges() {
        return privileges;
    }
 
    public void setPrivileges(List<Privilege> privileges) {
        this.privileges = privileges;
    }

    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", password='" + password + '\'' +
                ", registeredOn=" + registeredOn +
                ", privileges=" + privileges.toString() +
                ", addressInformation=" + addressInformation.toString() +
                '}';
    }
}