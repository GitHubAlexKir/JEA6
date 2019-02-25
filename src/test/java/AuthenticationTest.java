import io.restassured.RestAssured;
import org.junit.BeforeClass;
import org.junit.Test;

import static io.restassured.RestAssured.get;
import static org.hamcrest.core.IsEqual.equalTo;

public class AuthenticationTest {
    @BeforeClass
    public static void init() {
        RestAssured.baseURI = "http://localhost/1";
        RestAssured.port = 8080;
    }
    //TDD: authenticatie
    @Test
    public void testGetAccountsError() {
        get("/api/account/get/all")
                .then()
                .statusCode(equalTo(401));
    }

}
