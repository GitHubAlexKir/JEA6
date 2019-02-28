import io.restassured.RestAssured;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;

import static io.restassured.RestAssured.get;
import static org.hamcrest.core.IsEqual.equalTo;

public class AuthenticationTest {
   @BeforeClass
   public static void init() {
       RestAssured.baseURI = "http://localhost/1";
       RestAssured.port = 8080;
   }
   //TDD: authenticatie mislukt
   @Test
   public void testGetAccountsError() {
       get("/api/account/get/all")
               .then()
               .statusCode(equalTo(401));
   }

    //TDD: authenticatie
    @Test
    public void testGetAccountsSUccsess() {
        get("/api/account/get/all")
                .then()
                .statusCode(equalTo(200));
    }
    @Test
    public void login()
    {
       //todo
    }

}
