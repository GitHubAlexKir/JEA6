import domain.invoice.Invoice;
import domain.order.Order;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static io.restassured.RestAssured.given;

public class InvoiceTest {
    //Restassured instellen met token
    private static Order createdOrder = null;
    private static Invoice createdInvoice = null;
    @BeforeClass
    public static void setup()
    {
        RestAssured.baseURI = "http://localhost/webshop/api";
        RestAssured.port = 8080;
        String token = UserSetup.getToken();
        RestAssured.requestSpecification = new RequestSpecBuilder().addHeader("Authorization", "Bearer "+token).build();
        createdOrder = InvoiceSetup.createOrderAndSetOnPaid();
        System.out.println("Order to test with: " + createdOrder.toString());
    }

    //Test invoice ophalen
    @Test
    public void getInvoice()
    {
        createdInvoice = given()
                .when().get("/invoice/" + createdOrder.getId()).then()
                .statusCode(200)
                .extract().response().as(Invoice.class);
        System.out.println("//Created invoice: " + createdInvoice.toString());
    }
    // Test voor opruimen van aangemaakte order
    @AfterClass
    public static void cleanUp() {
        System.out.println("//OrderTest cleanup");
        if (createdOrder != null) {
            RestAssured.requestSpecification = new RequestSpecBuilder().addHeader("Authorization", "Bearer " + UserSetup.getOwnerToken()).build();
            given()
                    .when().delete("/order/" + createdOrder.getId()).then()
                    .statusCode(200);
            System.out.println("deleted Order with Id " + createdOrder.getId());
        }
        if (createdInvoice != null){
            RestAssured.requestSpecification = new RequestSpecBuilder().addHeader("Authorization", "Bearer " + UserSetup.getOwnerToken()).build();
            given()
                    .when().delete("/invoice/" + createdInvoice.getId()).then()
                    .statusCode(200);
            System.out.println("deleted Invoice with Id " + createdInvoice.getId());
        }
    }
}
