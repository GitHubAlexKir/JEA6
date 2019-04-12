import domain.dto.ItemDTO;
import domain.item.Item;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

import static io.restassured.RestAssured.given;

public class ItemTest {
    private static Item createdItem = null;
    @BeforeClass
    public static void setup()
    {
        RestAssured.baseURI = "http://localhost/webshop/api";
        RestAssured.port = 8080;
        //jwt token //todo
    }

    @Test
    public void CreateItem(){
        ItemDTO itemDTO = new ItemDTO();
        itemDTO.setProductName("Bacardi Carto Blanca 3L");
        itemDTO.setProductNumber(1l);
        itemDTO.setPrice(299.99);
        itemDTO.setStock(3);
        Response r = given()
                .contentType("application/json")
                .body(itemDTO)
                .when().post("/item").then()
                .statusCode(200)
                .extract().response();

        createdItem  =  r.getBody().jsonPath().getObject("item", Item.class);
        System.out.println(createdItem.toString());
    }
    @Test
    public void getAllItem(){
        ItemDTO itemDTO = new ItemDTO();
        itemDTO.setProductName("Bacardi Carto Blanca 3L");
        itemDTO.setProductNumber(1l);
        itemDTO.setPrice(299.99);
        itemDTO.setStock(3);
        Response r = given().when().get("/item").then()
                .statusCode(200)
                .extract().response();

        List<Item> createdItems  =  r.getBody().jsonPath().getList("items", Item.class);
        for (Item i:createdItems
             ) {
            System.out.println(i.toString());
        }
    }
    @AfterClass
    public static void cleanUp()
    {
       //if (createdItem != null)
       //{
       //    given()
       //            .when().delete("/item/" + createdItem.getId()).then()
       //            .statusCode(200);
       //}
    }
}
