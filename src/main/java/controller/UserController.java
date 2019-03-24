package controller;

import Repository.AccountRepository;
import Repository.UserRepository;
import domain.User;

import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.io.IOException;
import java.net.URI;
import java.util.List;
import org.glassfish.jersey.server.mvc.Viewable;

@Path("/admin")
public class UserController {
    @EJB
    private UserRepository repo;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<User> getUsers()
    {
        return repo.findAll();
    }
    @POST
    @Path("/update")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public User update(User user) {
        repo.update(user);
        return repo.find(user.getEmail());
    }
}
