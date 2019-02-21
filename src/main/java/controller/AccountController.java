package controller;

import Repository.AccountRepository;
import domain.Account;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

@Path("/account")
public class AccountController {
    @EJB
    AccountRepository repo;

    public AccountController()
    {

    }
    @GET
    public String account()
    {
        return "welkom bij accounts";
    }
    @GET
    @Path("/get/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Account getAccount(@PathParam("id") long id)
    {
        return repo.find(id);
    }
}
