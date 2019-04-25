import domain.order.Order;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static io.restassured.RestAssured.*;

public class OrderTest {
    private static Order createdOrder = null;

    @BeforeClass
    public static void setup()
    {
        RestAssured.baseURI = "http://localhost/webshop/api";
        RestAssured.port = 8080;
        String token = UserSetup.getToken();
        RestAssured.requestSpecification = new RequestSpecBuilder().addHeader("Authorization", "Bearer "+token).build();
    }
    @Test
    public void CreateOrder(){
        Response r = given()
                .contentType("application/json")
                .body(OrderSetup.createOrderDTO())
                .when().post("/order").then()
                .statusCode(200)
                .extract().response();
        createdOrder  =  r.getBody().jsonPath().getObject("order", Order.class);
        System.out.println("//created Order");
        System.out.println(createdOrder.toString());
        System.out.println();
    }

    @AfterClass
    public static void cleanUp() {
        if (createdOrder != null) {
            RestAssured.requestSpecification = new RequestSpecBuilder().addHeader("Authorization", "Bearer " + UserSetup.getOwnerToken()).build();
            System.out.println("//OrderTest cleanup");
            given()
                    .when().delete("/order/" + createdOrder.getId()).then()
                    .statusCode(200);
            System.out.println("deleted Order with Id " + createdOrder.getId());
        }
    }

}
