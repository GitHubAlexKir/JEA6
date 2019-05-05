import domain.dto.AddressInformationDTO;
import domain.dto.UserDTO;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;
/**
 * @author Alex
 * Register tests voor functionaliteit
 **/
public class RegisterTest {
    private static boolean createdUser = false;
    private static String registerEmail = "RegisterUser@RestAssured.com";
    private static UserDTO userDTO = new UserDTO();

    //Restassured instellen, userDTO object voor tests aanmaken.
    @BeforeClass
    public static void setup() {
        RestAssured.baseURI = "http://localhost/webshop/api";
        RestAssured.port = 8080;
        System.out.println("Register-account email: " + registerEmail);
        AddressInformationDTO addressInformationDTO = new AddressInformationDTO();
        addressInformationDTO.setAddressee("Test User");
        addressInformationDTO.setAddress("Test Address 15");
        addressInformationDTO.setCity("TestCity");
        addressInformationDTO.setZip("4444XX");
        userDTO.setEmail(UUID.randomUUID().toString() + registerEmail);
        userDTO.setFirstName("Test");
        userDTO.setLastName("User");
        userDTO.setAddressInformationDTO(addressInformationDTO);
    }
    // Test voor wachtwoord met Hoofdletter, cijfer en minimaal 12 tekens lang
    @Test
    public void registerSuccessWithStrongPassword(){
        assertEquals(200, register("Twe1fthChara","Twe1fthChara").statusCode());
    }
    // Test voor wachtwoord zonder Hoofdletter, cijfer, en minimaal 12 tekens lang.
    @Test
    public void registerFailWithWeakPasswordContainingAllErrorResonses(){
        Response r = register("eeeeeeeeeee","eeeeeeeeeee");
        assertEquals(409, r.statusCode());
        String expectedErrorMsg = "Wachtwoordlengte moet minimaal 12 tekens lang zijn\n" + "Wachtwoord moet minimaal één hoofdletter bevatten\n" + "Wachtwoord moet minimaal één cijfer hebben\n";
        String responseErrorMsg = r.getBody().jsonPath().getString("errorMsg");
        responseErrorMsg = responseErrorMsg.replaceAll("[\\n]", "\n");
        assertEquals(expectedErrorMsg,responseErrorMsg);
    }
    // Test voor wachtwoord zonder Hoofdletter
    @Test
    public void registerFailWithWeakPasswordWithoutCap(){
        Response r = register("twe1fthchara","twe1fthchara");
        assertEquals(409, r.statusCode());
        String expectedErrorMsg = "Wachtwoord moet minimaal één hoofdletter bevatten\n";
        String responseErrorMsg = r.getBody().jsonPath().getString("errorMsg");
        responseErrorMsg = responseErrorMsg.replaceAll("[\\n]", "\n");
        assertEquals(expectedErrorMsg,responseErrorMsg);
    }
    // Test voor wachtwoord zonder Cijfer
    @Test
    public void registerFailWithWeakPasswordWithoutNuber(){
        Response r = register("TwelfthChara","TwelfthChara");
        assertEquals(409, r.statusCode());
        String expectedErrorMsg = "Wachtwoord moet minimaal één cijfer hebben\n";
        String responseErrorMsg = r.getBody().jsonPath().getString("errorMsg");
        responseErrorMsg = responseErrorMsg.replaceAll("[\\n]", "\n");
        assertEquals(expectedErrorMsg,responseErrorMsg);
    }
    // Test voor wachtwoord zonder minimaal 12 tekens
    @Test
    public void registerFailWithWeakPasswordWithoutEnoughChar(){
        Response r = register("Twe1fthChar","Twe1fthChar");
        assertEquals(409, r.statusCode());
        String expectedErrorMsg = "Wachtwoordlengte moet minimaal 12 tekens lang zijn\n";
        String responseErrorMsg = r.getBody().jsonPath().getString("errorMsg");
        responseErrorMsg = responseErrorMsg.replaceAll("[\\n]", "\n");
        assertEquals(expectedErrorMsg,responseErrorMsg);
    }

    //register methode
    private Response register(String password,String passwordConfirm){
        userDTO.setEmail(UUID.randomUUID().toString() + registerEmail);
        userDTO.setPassword1(password);
        userDTO.setPassword2(passwordConfirm);
        Response r = given()
                .contentType("application/json")
                .body(userDTO)
                .when().post("/jwt/register").then()
                .extract().response();
        if (r.statusCode() == 200) {
            createdUser = true;
            System.out.println("//created User");
        }else {
            System.out.println("//User not created.");
        }
        System.out.println();
        return r;
    }

    //Cleanup van tests
    @AfterClass
    public static void cleanUp()
    {
        if (createdUser)
        {
            RestAssured.requestSpecification = new RequestSpecBuilder().addHeader("Authorization", "Bearer "+UserSetup.getOwnerToken()).build();
            System.out.println("//RegisterTest cleanup");
            given()
                    .when().delete("/jwt/" + userDTO.getEmail()).then()
                    .statusCode(200);
            System.out.println("deleted User with email " + userDTO.getEmail());
        }
    }
}
