import domain.dto.ItemDTO;
import domain.item.Item;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.equalTo;
import java.util.List;

import static io.restassured.RestAssured.given;

public class ItemTest {
    private static Item createdItem = null;

    @BeforeClass
    public static void setup()
    {
        RestAssured.baseURI = "http://localhost/webshop/api";
        RestAssured.port = 8080;
        String token = UserSetup.getToken();
        RestAssured.requestSpecification = new RequestSpecBuilder().addHeader("Authorization", "Bearer "+token).build();
    }

    @Test
    public void CreateItemWithoutAdminAccount(){
        ItemDTO itemDTO = new ItemDTO();
        itemDTO.setProductName("Bacardi Carto Blanca 3L");
        itemDTO.setProductNumber(1234597038506L);
        itemDTO.setPrice(299.99);
        itemDTO.setStock(3);
        itemDTO.setWarehouseLocation("A3");
        given()
                .contentType("application/json")
                .body(itemDTO)
                .when().post("/item").then()
                .statusCode(401);
    }
    @Test
    public void CreateItemWithAdminAccount(){
            RestAssured.requestSpecification = new RequestSpecBuilder().addHeader("Authorization", "Bearer " + UserSetup.getOwnerToken()).build();
            ItemDTO itemDTO = new ItemDTO();
            itemDTO.setProductName("Test Fles 1L");
            itemDTO.setProductNumber(4827979655038506L);
            itemDTO.setPrice(99.99);
            itemDTO.setStock(50);
            itemDTO.setWarehouseLocation("3C");
            Response r = given()
                    .contentType("application/json")
                    .body(itemDTO)
                    .when().post("/item").then()
                    .statusCode(200)
                    .extract().response();
            createdItem  =  r.getBody().jsonPath().getObject("item", Item.class);
            System.out.println("//created Item");
            System.out.println(createdItem.toString());
            System.out.println();
    }
    @Test
    public void getAllItem(){
        Response r = given().when().get("/item").then()
                .statusCode(200)
                .extract().response();
        System.out.println("//Get all items Results");
        List<Item> createdItems  =  r.getBody().jsonPath().getList("items", Item.class);
        for (Item i:createdItems
             ) {
            System.out.println(i.toString());
        }
        System.out.println();
    }

    @AfterClass
    public static void cleanUp()
    {
       if (createdItem != null)
       {
           System.out.println("//ItemTest cleanup");
           given()
                   .when().delete("/item/" + createdItem.getId()).then()
                   .statusCode(200);
           System.out.println("deleted item with id " + createdItem.getId());
       }
    }
}
