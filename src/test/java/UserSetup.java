import domain.authentication.UserLogin;
import domain.dto.AddressInformationDTO;
import domain.dto.UserDTO;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
/**
 * @author Alex
 * Setup om gebruikers te registreren voor tests.
 **/
public class UserSetup {
    //registratie gegevens
    private static String userEmail = "Alex@RestAssured.com";
    private static String ownerEmail = "Owner@RestAssured.com";
    private static String workerEmail = "Worker@RestAssured.com";
    private static String password = "RmUxae9qZwXhhBhVdTkBZEY8KQMsCga49FhhmKxrWX7QeRdEcd";
    private static String token;

    public UserSetup() {
    }
    //token verkrijgen
    static String getToken(){
        //Check of gebruikers al geregistreerd zijn, als niet worden de gebruikers aangemaakt.
        if (!login(false)){
            registerCustomer();
            registerAdminWithoutRole();
            registerWorkerWithoutRole();
            login(false);
        }
        return token;
    }
    //Owner token verkrijgen
    static String getOwnerToken(){
        login(true);
        return token;
    }
    //registreren
    private static void register(UserDTO userDTO){
        given().contentType("application/json")
                .body(userDTO)
                .when().post("/jwt/register")
                .then().statusCode(200);
    }
    //Account customer registreren
    private static void registerCustomer() {
        System.out.println("Creating Customer account with email " + userEmail);
        AddressInformationDTO addressInformationDTO = new AddressInformationDTO();
        addressInformationDTO.setAddressee("Test User");
        addressInformationDTO.setAddress("Test Address 15");
        addressInformationDTO.setCity("TestCity");
        addressInformationDTO.setZip("4444XX");
        UserDTO userDTO = new UserDTO();
        userDTO.setEmail(userEmail);
        userDTO.setPassword1(password);
        userDTO.setPassword2(password);
        userDTO.setFirstName("Test");
        userDTO.setLastName("User");
        userDTO.setAddressInformationDTO(addressInformationDTO);
        register(userDTO);

    }
    //Account admin registreren
    private static void registerAdminWithoutRole() {
        System.out.println("Creating Admin account with email " + userEmail);
        System.out.println("DONT FORGET TO CHANGE ROLE IN DB");
        AddressInformationDTO addressInformationDTO = new AddressInformationDTO();
        addressInformationDTO.setAddressee("Test ADMIN");
        addressInformationDTO.setAddress("Test Address 15");
        addressInformationDTO.setCity("TestCity");
        addressInformationDTO.setZip("4444XX");
        UserDTO userDTO = new UserDTO();
        userDTO.setEmail(ownerEmail);
        userDTO.setPassword1(password);
        userDTO.setPassword2(password);
        userDTO.setFirstName("Test");
        userDTO.setLastName("ADMIN");
        userDTO.setAddressInformationDTO(addressInformationDTO);
        register(userDTO);

    }
    //Account worker registreren
    private static void registerWorkerWithoutRole() {
        System.out.println("Creating Worker account with email " + workerEmail);
        System.out.println("DONT FORGET TO CHANGE ROLE IN DB");
        AddressInformationDTO addressInformationDTO = new AddressInformationDTO();
        addressInformationDTO.setAddressee("Test WORKER");
        addressInformationDTO.setAddress("Test Address 15");
        addressInformationDTO.setCity("TestCity");
        addressInformationDTO.setZip("4444XX");
        UserDTO userDTO = new UserDTO();
        userDTO.setEmail(workerEmail);
        userDTO.setPassword1(password);
        userDTO.setPassword2(password);
        userDTO.setFirstName("Test");
        userDTO.setLastName("ADMIN");
        userDTO.setAddressInformationDTO(addressInformationDTO);
        register(userDTO);

    }
    //login check, als ingelogd wordt token uit response gehaald. boolean voor admin
    private static boolean login(boolean adminLogin) {
        UserLogin userLogin = new UserLogin();
        if (adminLogin){
            userLogin.setEmail(ownerEmail);
        }
        else {
            userLogin.setEmail(userEmail);
        }
        userLogin.setPassword(password);
        Response r = given()
                .contentType("application/json")
                .body(userLogin)
                .when().post("/jwt/login").then()
                .extract().response();
        System.out.println("Login statuscode: " + r.getStatusCode());
        if (r.getStatusCode() == 200) {
            token = r.getBody().jsonPath().getString("data");
            System.out.println("jwttoken: " +token);
            return true;
        }
        return false;
    }
}
