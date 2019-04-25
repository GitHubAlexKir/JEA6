import domain.authentication.User;
import domain.dto.AddressInformationDTO;
import domain.dto.ItemDTO;
import domain.dto.OrderDTO;
import io.restassured.response.Response;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;

class OrderSetup {
    static OrderDTO createOrderDTO(){
        User user = getUser();
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setUserEmail(user.getEmail());
        orderDTO.setAddressInformationDTO(getUserAdressInformationDTO(user));
        orderDTO.setItems(getItemsDTO());
        orderDTO.setDispatched(false);
        orderDTO.setPaid(false);
        return orderDTO;
    }


    private static User getUser(){
        return given()
                .when().get("/jwt/user").then()
                .extract().response().as(User.class);
    }

    private static AddressInformationDTO getUserAdressInformationDTO(User user){
        AddressInformationDTO addressInformationDTO = new AddressInformationDTO();
        addressInformationDTO.setAddress(user.getAddressInformation().getAddress());
        addressInformationDTO.setZip(user.getAddressInformation().getZip());
        addressInformationDTO.setCity(user.getAddressInformation().getCity());
        addressInformationDTO.setAddressee(user.getAddressInformation().getAddressee());
        return addressInformationDTO;
    }

    private static List<ItemDTO> getItemsDTO() {
        List<ItemDTO> availableItems = new ArrayList<>();
        Response r = given().when().get("/item").then()
                .statusCode(200)
                .extract().response();
        List<ItemDTO> itemDTOs = r.getBody().jsonPath().getList("items", ItemDTO.class);
        for (ItemDTO item:itemDTOs) {
            if (item.getStock() > 0)
                availableItems.add(item);
        }
        return availableItems;
    }
}
