import domain.dto.ReviewDTO;
import domain.item.Item;
import domain.item.Review;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author Alex
 * TDD: review tests voor functionaliteit Bean Validation
 **/
public class ReviewTest {
    private static Item createdItem = null;
    private static Review createdReview = null;

    //Restassured instellen met token, Item aanmaken om mee te testen
    @BeforeClass
    public static void setup()
    {
        RestAssured.baseURI = "http://localhost/webshop/api";
        RestAssured.port = 8080;
        createdItem = ReviewSetup.createItem();
        String token = UserSetup.getToken();
        RestAssured.requestSpecification = new RequestSpecBuilder().addHeader("Authorization", "Bearer "+token).build();
    }
    //Bean validatie: Author en Appreciation mag niet leeg zijn
    @Test
    public void createReviewValidationErrorEmpty(){
        ReviewDTO reviewDTO = new ReviewDTO();
        reviewDTO.setContent("lekker");
        Response r = given()
                .contentType("application/json")
                .body(reviewDTO)
                .when().post("/item/review/" + createdItem.getId()).then()
                .statusCode(409)
                .extract().response();
        String expectedErrorMessageAppreciation =  "Waardering moet minimaal 1 zijn.";
        String expectedErrorMessageAuthor =  "Auteur mag niet leeg zijn.";
        String errormessage  =  r.getBody().jsonPath().getString("errorMsg");
        assertTrue(errormessage.contains(expectedErrorMessageAppreciation));
        assertTrue(errormessage.contains(expectedErrorMessageAuthor));
        System.out.println();

    }
    //Bean validatie: Appriciation moet minimaal 1 zijn.
    @Test
    public void createReviewValidationErrorMinAppreciation(){
        ReviewDTO reviewDTO = new ReviewDTO();
        reviewDTO.setContent("lekker");
        reviewDTO.setAppreciation(0);
        reviewDTO.setAuthor("Iemand");
        Response r = given()
                .contentType("application/json")
                .body(reviewDTO)
                .when().post("/item/review/" + createdItem.getId()).then()
                .statusCode(409)
                .extract().response();
        String expectedErrorMessage = "Waardering moet minimaal 1 zijn.\n";
        String errormessage  =  r.getBody().jsonPath().getString("errorMsg");
        assertEquals(expectedErrorMessage,errormessage);
        System.out.println();
    }
    //Bean validatie: Appriciation moet maximaal 5 zijn.
    @Test
    public void createReviewValidationErrorMaxAppreciation(){
        ReviewDTO reviewDTO = new ReviewDTO();
        reviewDTO.setContent("lekker");
        reviewDTO.setAppreciation(6);
        reviewDTO.setAuthor("Iemand");
        Response r = given()
                .contentType("application/json")
                .body(reviewDTO)
                .when().post("/item/review/" + createdItem.getId()).then()
                .statusCode(409)
                .extract().response();
        String expectedErrorMessage = "Waardering moet maximaal 5 zijn.\n";
        String errormessage  =  r.getBody().jsonPath().getString("errorMsg");
        assertEquals(expectedErrorMessage,errormessage);
        System.out.println();
    }
    //Bean validatie: Geen errors, gemaakte review wordt teruggegeven.
    @Test
    public void createReviewValidationSuccess(){
        ReviewDTO reviewDTO = new ReviewDTO();
        reviewDTO.setContent("lekker");
        reviewDTO.setAppreciation(5);
        reviewDTO.setAuthor("Iemand");
        Response r = given()
                .contentType("application/json")
                .body(reviewDTO)
                .when().post("/item/review/" + createdItem.getId()).then()
                .statusCode(200)
                .extract().response();
        createdReview  =  r.getBody().jsonPath().getObject("review", Review.class);
        System.out.println("//created Review");
        System.out.println(createdItem.toString());
        System.out.println();
    }
    //Tests cleanup
    @AfterClass
    public static void cleanUp() {
        if (createdItem != null) {
            //Token setten: Alleen Owner role mag een item verwijderen
            RestAssured.requestSpecification = new RequestSpecBuilder().addHeader("Authorization", "Bearer "+UserSetup.getOwnerToken()).build();
            given()
                    .when().delete("/item/" + createdItem.getId()).then()
                    .statusCode(200);
            System.out.println("deleted item with id " + createdItem.getId());
        }
        if (createdReview != null) {
            given()
                    .contentType("application/json")
                    .body(createdReview)
                    .when().post("/item/review/remove/" + createdItem.getId()).then()
                    .statusCode(200);
            System.out.println("deleted Review with id " + createdReview.getId());
        }
    }

}
