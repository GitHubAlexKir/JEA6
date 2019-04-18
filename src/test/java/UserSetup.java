import domain.authentication.UserLogin;
import domain.dto.AddressInformationDTO;
import domain.dto.UserDTO;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class UserSetup {
    private static String userEmail = "Alex@RestAssured.com";
    private static String password = "RmUxae9qZwXhhBhVdTkBZEY8KQMsCga49FhhmKxrWX7QeRdEcd";
    private static String ownerEmail = "Owner@RestAssured.com";
    private static String workerEmail = "Worker@RestAssured.com";
    private static String token;

    public UserSetup(String specialUserEmail,String specialUserPassword) {
        userEmail = specialUserEmail;
        password = specialUserPassword;
    }

    public static String getToken(){
        if (!login()){
            registerCustomer();
            registerAdminWithoutRole();
            registerWorkerWithoutRole();
            login();
        }
        registerWorkerWithoutRole();
        return token;
    }
    public static String getOwnerToken(){
        loginAdmin();
        return token;
    }
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
        given().contentType("application/json")
                .body(userDTO)
                .when().post("/jwt/register")
                .then().statusCode(200);

    }
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
        given().contentType("application/json")
                .body(userDTO)
                .when().post("/jwt/register")
                .then().statusCode(200);

    }
    private static void registerWorkerWithoutRole() {
        System.out.println("Creating Worker account with email " + userEmail);
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
        given().contentType("application/json")
                .body(userDTO)
                .when().post("/jwt/register")
                .then().statusCode(200);

    }

    private static boolean login() {
        UserLogin userLogin = new UserLogin();
        userLogin.setEmail(userEmail);
        userLogin.setPassword(password);
        Response r = given()
                .contentType("application/json")
                .body(userLogin)
                .when().post("/jwt/login").then()
                .extract().response();
        System.out.println("Login statuscode: " + r.getStatusCode());
        if (r.getStatusCode() == 200) {
            token = r.getBody().jsonPath().getString("data");
            System.out.println("Customer jwttoken: " +token);
            return true;
        }
        return false;
    }
    private static void loginAdmin() {
        UserLogin userLogin = new UserLogin();
        userLogin.setEmail(ownerEmail);
        userLogin.setPassword(password);
        Response r = given()
                .contentType("application/json")
                .body(userLogin)
                .when().post("/jwt/login").then().statusCode(200)
                .extract().response();
        System.out.println("Login statuscode: " + r.getStatusCode());
        token = r.getBody().jsonPath().getString("data");
        System.out.println("Owner jwttoken: " +token);
    }
}
