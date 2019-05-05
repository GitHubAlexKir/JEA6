import domain.order.Order;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
/**
 * @author Alex
 * Setup Voor Order dat betaald is voor Invoice
 **/
 class InvoiceSetup {
     static Order createOrderAndSetOnPaid() {
        Response responseCreate = given()
                .contentType("application/json")
                .body(OrderSetup.createOrderDTO())
                .when().post("/order").then()
                .statusCode(200)
                .extract().response();
        Order order = responseCreate.getBody().jsonPath().getObject("order", Order.class);
        Response responsePaid = given()
                .contentType("application/json")
                .when().post("/order/paid/" +order.getId()).then()
                .statusCode(200)
                .extract().response();
        return responsePaid.getBody().jsonPath().getObject("order", Order.class);
    }
}
