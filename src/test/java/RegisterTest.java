import domain.authentication.User;
import domain.dto.AddressInformationDTO;
import domain.dto.UserDTO;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static io.restassured.RestAssured.given;

import static org.junit.Assert.assertEquals;

public class RegisterTest {
    private static User createdUser = null;
    private static String registerEmail = "RegisterUser@RestAssured.com";
    private static UserDTO userDTO = new UserDTO();

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
        userDTO.setEmail(registerEmail);
        userDTO.setFirstName("Test");
        userDTO.setLastName("User");
        userDTO.setAddressInformationDTO(addressInformationDTO);
    }
    // Wachtwoord met Hoofdletter, cijfer en minimaal 12 tekens lang
    @Test
    public void registerSuccessWithStrongPassword(){
        assertEquals(200, register("Twe1fthChara","Twe1fthChara").statusCode());
    }
    // Wachtwoord zonder Hoofdletter, cijfer, en minimaal 12 tekens lang.
    @Test
    public void registerFailWithWeakPasswordContainingAllErrorResonses(){
        Response r = register("eeeeeeeeeee","eeeeeeeeeee");
        assertEquals(409, r.statusCode());
        String expectedErrorMsg = "Wachtwoordlengte moet minimaal 12 tekens lang zijn\n" + "Wachtwoord moet minimaal één hoofdletter bevatten\n" + "Wachtwoord moet minimaal één cijfer hebben\n";
        String responseErrorMsg = r.getBody().jsonPath().getString("errorMsg");
        responseErrorMsg = responseErrorMsg.replaceAll("[\\n]", "\n");
        assertEquals(expectedErrorMsg,responseErrorMsg);
    }
    // Wachtwoord zonder Hoofdletter
    @Test
    public void registerFailWithWeakPasswordWithoutCap(){
        Response r = register("twe1fthchara","twe1fthchara");
        assertEquals(409, r.statusCode());
        String expectedErrorMsg = "Wachtwoord moet minimaal één hoofdletter bevatten\n";
        String responseErrorMsg = r.getBody().jsonPath().getString("errorMsg");
        responseErrorMsg = responseErrorMsg.replaceAll("[\\n]", "\n");
        assertEquals(expectedErrorMsg,responseErrorMsg);
    }
    // Wachtwoord zonder Cijfer
    @Test
    public void registerFailWithWeakPasswordWithoutNuber(){
        Response r = register("TwelfthChara","TwelfthChara");
        assertEquals(409, r.statusCode());
        String expectedErrorMsg = "Wachtwoord moet minimaal één cijfer hebben\n";
        String responseErrorMsg = r.getBody().jsonPath().getString("errorMsg");
        responseErrorMsg = responseErrorMsg.replaceAll("[\\n]", "\n");
        assertEquals(expectedErrorMsg,responseErrorMsg);
    }
    // Wachtwoord zonder minimaal 12 tekens
    @Test
    public void registerFailWithWeakPasswordWithoutEnoughChar(){
        Response r = register("Twe1fthChar","Twe1fthChar");
        assertEquals(409, r.statusCode());
        String expectedErrorMsg = "Wachtwoordlengte moet minimaal 12 tekens lang zijn\n";
        String responseErrorMsg = r.getBody().jsonPath().getString("errorMsg");
        responseErrorMsg = responseErrorMsg.replaceAll("[\\n]", "\n");
        assertEquals(expectedErrorMsg,responseErrorMsg);
    }

    private Response register(String password,String passwordConfirm){
        userDTO.setPassword1(password);
        userDTO.setPassword2(passwordConfirm);
        Response r = given()
                .contentType("application/json")
                .body(userDTO)
                .when().post("/jwt/register").then()
                .extract().response();
        if (r.statusCode() == 200) {
            createdUser = r.getBody().jsonPath().getObject("data", User.class);
            System.out.println("//created User");
            System.out.println(createdUser.toString());
        }else {
            System.out.println("//User not created.");
        }
        System.out.println();
        return r;
    }

    @AfterClass
    public static void cleanUp()
    {
        if (createdUser != null)
        {
            RestAssured.requestSpecification = new RequestSpecBuilder().addHeader("Authorization", "Bearer "+UserSetup.getOwnerToken()).build();
            System.out.println("//RegisterTest cleanup");
            given()
                    .when().delete("/jwt/" + createdUser.getEmail()).then()
                    .statusCode(200);
            System.out.println("deleted User with email " + createdUser.getEmail());
        }
    }
}
