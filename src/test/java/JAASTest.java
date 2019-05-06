import org.junit.Test;

import static io.restassured.RestAssured.expect;

/**
 * @author Alex
 * TDD: authenticatie tests voor functionaliteit JAAS
 **/
public class JAASTest {
    private static String ownerEmail = "Owner@RestAssured.com";
    //private static String workerEmail = "Worker@RestAssured.com";
    private static String password = "RmUxae9qZwXhhBhVdTkBZEY8KQMsCga49FhhmKxrWX7QeRdEcd";
    @Test
    public void loginSuccess(){
        String sessionId;
        sessionId =
                expect().
                        statusCode(200).
                        when().
                        get("/jaas").sessionId();
        expect().
                statusCode(302).
                given().
                param("j_username", ownerEmail).
                param("j_password", password).
                cookie("JSESSIONID", sessionId).
                post("j_security_check");
        sessionId =
                expect().
                        statusCode(200).
                        given().
                        cookie("JSESSIONID", sessionId).
                        when().
                        get("/jaas").sessionId();
        System.out.println("Authenticated session.id: " + sessionId);
    }

}
