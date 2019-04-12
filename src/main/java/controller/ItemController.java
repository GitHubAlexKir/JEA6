package controller;

import Interceptor.SimpleInterceptor;
import Repository.ItemRepository;
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
    public Response item()
    {

        JSONObject response = new JSONObject();
        response.put("ping","welkom bij items");
        response.put("_links",getLinks(URI.create("http://localhost:8080/1/api/item")));
        return Response.ok(response.toString(2)).build();
    }
    @GET
    @Path("/get/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getItem(@PathParam("id") long id)
    {
        JSONObject response = new JSONObject();
        Item item = repo.find(id);
        response.put("item",item.toMap());
        response.put("_links",getLinks(URI.create("http://localhost:8080/1/api/item")));
        return Response.ok(response.toString(2)).build();
    }

    @GET
    @Path("/get/all")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getItems()
    {
        JSONObject response = new JSONObject();
        response.put("items",repo.findAll());
        response.put("_links",getLinks(URI.create("http://localhost:8080/1/api/item")));
        return Response.ok(response.toString(2)).build();
    }

    @POST
    @Path("/save")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response save(Item item)
    {
        JSONObject response = new JSONObject();
        response.put("item",repo.create(item).toMap());
        response.put("_links",getLinks(URI.create("http://localhost:8080/1/api/item")));
        return Response.ok(response.toString(2)).build();
    }

    @POST
    @Path("/delete")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response delete(Item item)
    {
        JSONObject response = new JSONObject();
        response.put("Item deleted",repo.delete(item.getId()));
        response.put("_links",getLinks(URI.create("http://localhost:8080/1/api/item")));
        return Response.ok(response.toString(2)).build();
    }

    private Map<String, URI> getLinks(URI self)
    {
        Map<String, URI> links = new HashMap<>();
        links.put("self",self);
        links.put("save",URI.create("http://localhost:8080/1/api/item/save"));
        links.put("delete",URI.create("http://localhost:8080/1/api/item/delete"));
        links.put("get",URI.create("http://localhost:8080/1/api/item/get/item-id"));
        links.put("get all",URI.create("http://localhost:8080/1/api/item/get/all"));
        return links;
    }
}
