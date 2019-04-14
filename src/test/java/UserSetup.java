import domain.authentication.UserLogin;
import domain.dto.AddressInformationDTO;
import domain.dto.UserDTO;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class UserSetup {
    private static String userEmail = "Alex2@RestAssured.com";
    private static String userPassword = "RmUxae9qZwXhhBhVdTkBZEY8KQMsCga49FhhmKxrWX7QeRdEcd";
    private static String ownerEmail = "Owner@RestAssured.com";
    private static String ownerPassword = "RmUxae9qZwXhhBhVdTkBZEY8KQMsCga49FhhmKxrWX7QeRdEcd";
    private static String token;

    public UserSetup(String specialUserEmail,String specialUserPassword) {
        userEmail = specialUserEmail;
        userPassword = specialUserPassword;
    }

    public static String getToken(){
        if (!login()){
            registerCustomer();
            login();
        }
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
        userDTO.setPassword1(userPassword);
        userDTO.setPassword2(userPassword);
        userDTO.setFirstName("Test");
        userDTO.setLastName("User");
        userDTO.setAddressInformationDTO(addressInformationDTO);
        given().contentType("application/json")
                .body(userDTO)
                .when().post("/jwt/register")
                .then().statusCode(200);

    }

    private static boolean login() {
        UserLogin userLogin = new UserLogin();
        userLogin.setEmail(userEmail);
        userLogin.setPassword(userPassword);
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
        userLogin.setPassword(ownerPassword);
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
