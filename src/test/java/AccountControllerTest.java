import io.restassured.RestAssured;
import org.junit.BeforeClass;
import org.junit.Test;

import static io.restassured.RestAssured.get;
import static org.hamcrest.core.IsEqual.equalTo;

public class AccountControllerTest {

    @BeforeClass
    public static void init() {
        RestAssured.baseURI = "http://localhost/1";
        RestAssured.port = 8080;
    }

    @Test
    public void testUserFetchesSuccess() {
        get("/api/account/get/1")
                .then()
                .body("id", equalTo(1))
                .body("age",equalTo(19))
                .body("email", equalTo("a@h.nl"))
                .body("name", equalTo("Alex"))
                .body("password", equalTo("123456789011"));
    }
}
