package controller;

import Interceptor.SimpleInterceptor;
import Repository.ItemRepository;
import domain.dto.ItemDTO;
import domain.item.Item;
import org.json.JSONObject;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

@Path("/item")
@Stateless
@Interceptors(SimpleInterceptor.class)
public class ItemController {
    @EJB
    ItemRepository repo;

    public ItemController()
    {

    }
    @GET
    @Path("/ping")
    public Response item()
    {

        JSONObject response = new JSONObject();
        response.put("ping","welkom bij items");
        response.put("_links",getLinks(URI.create("http://localhost:8080/1/api/item")));
        return Response.ok(response.toString(2)).build();
    }
    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getItem(@PathParam("id") long id)
    {
        JSONObject response = new JSONObject();
        Item item = repo.find(id);
        response.put("item",item.toMap());
        response.put("_links",getLinks(URI.create("http://localhost:8080/webshop/api/item/" + item.getId())));
        return Response.ok(response.toString(2)).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getItems()
    {
        JSONObject response = new JSONObject();
        response.put("items",repo.findAll());
        response.put("_links",getLinks(URI.create("http://localhost:8080/webshop/api/item")));
        return Response.ok(response.toString(2)).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response save(ItemDTO itemDTO)
    {
        JSONObject response = new JSONObject();
        Item item = new Item(itemDTO);
        item = repo.create(item);
        response.put("item",item.toMap());
        response.put("_links",getLinks(URI.create("http://localhost:8080/webshop/api/item")));
        return Response.ok(response.toString(2)).build();
    }

    @DELETE
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response delete(@PathParam("id") long id)
    {
        JSONObject response = new JSONObject();
        response.put("Item_id deleted",repo.delete(id));
        response.put("_links",getLinks(URI.create("http://localhost:8080/webshop/api/item/" + id)));
        return Response.ok(response.toString(2)).build();
    }

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
