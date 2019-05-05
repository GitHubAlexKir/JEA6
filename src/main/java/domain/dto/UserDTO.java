package domain.dto;
/**
 * @author Alex
 * UserDTO class
 **/
public class UserDTO {
 
    private String email;
    private String firstName;
    private String lastName;
    private String password1;
    private String password2;
    private AddressInformationDTO addressInformationDTO;

    public UserDTO() {
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
  
    public String getPassword1() {
        return password1;
    }
     
    public void setPassword1(String password) {
        this.password1 = password;
    }
     
    public String getPassword2() {
        return password2;
    }
     
    public void setPassword2(String password) {
        this.password2 = password;
    }

    public AddressInformationDTO getAddressInformationDTO() {
        return addressInformationDTO;
    }

    public void setAddressInformationDTO(AddressInformationDTO addressInformationDTO) {
        this.addressInformationDTO = addressInformationDTO;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", password1='" + password1 + '\'' +
                ", password2='" + password2 + '\'' +
                ", addressInformationDTO=" + addressInformationDTO.toString() +
                '}';
    }
}