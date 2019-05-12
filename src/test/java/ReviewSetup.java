import domain.dto.ItemDTO;
import domain.item.Item;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class ReviewSetup {
    static Item createItem(){
        RestAssured.baseURI = "http://localhost/webshop/api";
        RestAssured.port = 8080;
        RestAssured.requestSpecification = new RequestSpecBuilder().addHeader("Authorization", "Bearer " + UserSetup.getOwnerToken()).build();
        ItemDTO itemDTO = new ItemDTO();
        itemDTO.setProductName("Test Item For ReviewTest");
        itemDTO.setProductNumber(4827504446L);
        itemDTO.setPrice(199.99);
        itemDTO.setStock(50);
        itemDTO.setWarehouseLocation("34C");
        Response r = given()
                .contentType("application/json")
                .body(itemDTO)
                .when().post("/item").then()
                .statusCode(200)
                .extract().response();
        return r.getBody().jsonPath().getObject("item", Item.class);
    }
}
