package controller;

import Repository.AccountRepository;
import domain.Account;

import javax.annotation.security.DeclareRoles;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;
import java.util.List;

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

    @GET
    @Path("/get/all")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Account> getAccount()
    {
        return repo.findAll();
    }

    @POST
    @Path("/save")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String save(Account account) {
        return repo.create(account);
    }
}
