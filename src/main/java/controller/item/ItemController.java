package controller.item;

import Interceptor.SimpleInterceptor;
import repository.ItemRepository;
import domain.dto.ItemDTO;
import domain.dto.ReviewDTO;
import domain.item.Item;
import domain.item.Review;
import filter.JWTTokenNeeded;
import filter.OwnerRoleNeeded;
import org.json.JSONObject;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * @author Alex
 * ItemController
 **/
@Path("/item")
@Stateless
@Interceptors(SimpleInterceptor.class)
@JWTTokenNeeded
public class ItemController {
    @EJB
    ItemRepository repo;

    public ItemController()
    {

    }
    //ping test
    @GET
    @Path("/ping")
    public Response item()
    {

        JSONObject response = new JSONObject();
        response.put("ping","welkom bij items");
        response.put("_links",getLinks(URI.create("http://localhost:8080/webshop/api/item")));
        return Response.ok(response.toString(2)).build();
    }
    //Item ophalen met Id
    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getItem(@PathParam("id") long id)
    {
        JSONObject response = new JSONObject();
        Item item = repo.find(id);
        response.put("item",item.toJSONObject());
        response.put("_links",getLinks(URI.create("http://localhost:8080/webshop/api/item/" + item.getId())));
        return Response.ok(response.toString(2)).build();
    }
    //Items ophalen
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getItems()
    {
        JSONObject response = new JSONObject();
        response.put("items",repo.findAll());
        response.put("_links",getLinks(URI.create("http://localhost:8080/webshop/api/item")));
        return Response.ok(response.toString(2)).build();
    }
    //Review aanmaken met ItemId
    @POST
    @Path("/review/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response saveReview(@PathParam("id") long id, ReviewDTO reviewDTO)
    {
        JSONObject response = new JSONObject();
       Review review = new Review(reviewDTO);
        Item item = repo.find(id);
        item.getReviews().add(review);
        item = repo.save(item);
        response.put("item",item.toJSONObject());
        response.put("_links",getLinks(URI.create("http://localhost:8080/webshop/api/item")));
        return Response.ok(response.toString(2)).build();
    }
    //Review verwijderen met ReviewId
    @POST
    @Path("/review/remove/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @OwnerRoleNeeded
    public Response deleteReview(@PathParam("id") long id, ReviewDTO reviewDTO)
    {
        JSONObject response = new JSONObject();
        Item item = repo.find(id);
        List<Review> reviews = new ArrayList<>();
        //Review om te verwijderen eruit halen
        for (Review rev:item.getReviews()
             ) {
            if (rev.getId() != reviewDTO.getId()){
                reviews.add(rev);
            }
        }
        item.setReviews(reviews);
        item = repo.update(item);
        response.put("item",item.toJSONObject());
        response.put("_links",getLinks(URI.create("http://localhost:8080/webshop/api/item")));
        return Response.ok(response.toString(2)).build();
    }
    //Item aanmaken met Owner check
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @OwnerRoleNeeded
    public Response save(ItemDTO itemDTO)
    {
        JSONObject response = new JSONObject();
        Item item = new Item(itemDTO);
        item = repo.save(item);
        response.put("item",item.toJSONObject());
        response.put("_links",getLinks(URI.create("http://localhost:8080/webshop/api/item")));
        return Response.ok(response.toString(2)).build();
    }
    //Item updaten
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @OwnerRoleNeeded
    public Response update(Item item)
    {
        JSONObject response = new JSONObject();
        item = repo.update(item);
        response.put("item",item.toJSONObject());
        response.put("_links",getLinks(URI.create("http://localhost:8080/webshop/api/item")));
        return Response.ok(response.toString(2)).build();
    }
    //Item verwijderen
    @DELETE
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @OwnerRoleNeeded
    public Response delete(@PathParam("id") long id)
    {
        JSONObject response = new JSONObject();
        response.put("Item_id deleted",repo.delete(id));
        response.put("_links",getLinks(URI.create("http://localhost:8080/webshop/api/item/" + id)));
        return Response.ok(response.toString(2)).build();
    }
    //Links meegeven bij json response
    private Map<String, URI> getLinks(URI self)
    {
        Map<String, URI> links = new HashMap<>();
        String baseUri = "http://localhost:8080/webshop";
        links.put("self",self);
        links.put("save",URI.create(baseUri +"/api/item"));
        links.put("delete",URI.create(baseUri + "/api/item"));
        links.put("get",URI.create(baseUri + "/api/item/item-id"));
        links.put("get all",URI.create(baseUri + "/api/item"));
        return links;
    }
}
