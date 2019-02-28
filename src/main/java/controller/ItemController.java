package controller;

import Repository.ItemRepository;
import domain.Item;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/item")
public class ItemController {
    @EJB
    ItemRepository repo;

    public ItemController()
    {

    }
    @GET
    public String item()
    {
        return "welkom bij items";
    }
    @GET
    @Path("/get/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Item getItem(@PathParam("id") long id)
    {
        return repo.find(id);
    }

    @GET
    @Path("/get/all")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Item> getItems()
    {
        return repo.findAll();
    }

    @POST
    @Path("/save")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Item save(Item item) {
        return repo.create(item);
    }
}
