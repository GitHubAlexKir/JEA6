import io.restassured.RestAssured;
import org.junit.BeforeClass;
import org.junit.Test;

import static io.restassured.RestAssured.expect;
import static io.restassured.RestAssured.given;
import static org.hamcrest.core.IsEqual.equalTo;

/**
 * @author Alex
 * TDD: authenticatie tests voor functionaliteit JAAS
 **/
public class JAASTest {
    private static String ownerEmail = "Owner@RestAssured.com";
    private static String workerEmail = "Worker@RestAssured.com";
    private static String password = "RmUxae9qZwXhhBhVdTkBZEY8KQMsCga49FhhmKxrWX7QeRdEcd";
    @BeforeClass
    public static void setup() {
        RestAssured.baseURI = "http://localhost/webshop";
        RestAssured.port = 8080;
    }
    //authenticatie success
    @Test
    public void loginSuccess(){
        String sessionId;
        sessionId =
                expect().
                        statusCode(200).
                        when().
                        get("/api/jaas/secure").sessionId();
        expect().
                statusCode(200).
                given().
                param("email", ownerEmail).
                param("password", password).
                cookie("JSESSIONID", sessionId).
                post("/jaas");
        sessionId =
                expect().
                        statusCode(200).
                        given().
                        cookie("JSESSIONID", sessionId).
                        when().
                        get("/api/jaas/secure").sessionId();
        System.out.println("Authenticated session.id: " + sessionId);
    }
    //authenticatie fails
    @Test
    public void loginFailed(){
        String sessionId;
        sessionId =
                expect().
                        statusCode(200).
                        when().
                        get("/api/jaas/secure").sessionId();
        expect().
                statusCode(401).
                given().
                param("email", ownerEmail).
                param("password", "notPassword").
                cookie("JSESSIONID", sessionId).
                post("/jaas");

    }
    // Owner role only success
    @Test
    public void ownerSecureSuccess(){
        String sessionId;
        sessionId =
                expect().
                        statusCode(200).
                        when().
                        get("/api/jaas/owner").sessionId();
        expect().
                statusCode(302).
                given().
                param("j_username", ownerEmail).
                param("j_password", password).
                cookie("JSESSIONID", sessionId).
                post("/j_security_check");
        System.out.println("Authenticated owner session.id: " + sessionId);
        RestAssured.sessionId = sessionId;
        given()
                .when().get("/api/jaas/owner").then().statusCode(200).body(equalTo("Owner role only"));

    }
    // Owner role only fails because logged in with worker role
    @Test
    public void ownerSecureFails(){

        String sessionId;
        sessionId =
                expect().
                        statusCode(200).
                        when().
                        get("/api/jaas/owner").sessionId();
        expect().
                statusCode(302).
                given().
                param("j_username", workerEmail).
                param("j_password", password).
                cookie("JSESSIONID", sessionId).
                post("/j_security_check");
        System.out.println("Authenticated worker session.id: " + sessionId);
        RestAssured.sessionId = sessionId;
        given()
                .when().get("/api/jaas/owner").then().statusCode(403);
    }

}
