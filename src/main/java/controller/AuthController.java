package controller;

import Repository.UserRepository;
import domain.Group;
import domain.JsonResponse;
import domain.User;
import domain.UserLogin;
import domain.dto.UserDTO;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/auth")
@Produces(MediaType.TEXT_PLAIN)
@Stateless
public class AuthController {
 
    @EJB
    private UserRepository userRepository;
 
    @GET
    public String ping() {
        return "Welkom bij login";
    }
 
    @POST
    @Path("login")
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(UserLogin userlogin,
                          @Context HttpServletRequest req) {
        JsonResponse json = new JsonResponse();
        if(req.getUserPrincipal() == null){
            try {
                req.login(userlogin.getEmail(), userlogin.getPassword());
            } catch (ServletException e) {
                e.printStackTrace();
                json.setStatus("FAILED");
                json.setErrorMsg("Authentication failed");
                return Response.ok().entity(json).build();
            }
        }
        json.setStatus("SUCCESS");
        User user = userRepository.find(userlogin.getEmail());
        json.setData(user);
        userRepository.detach(user);
        user.setPassword(null);
        user.setGroups(null);
        return Response.ok().entity(json).build();
    }
 
    @GET
    @Path("logout")
    @Produces(MediaType.APPLICATION_JSON)
    public Response logout(@Context HttpServletRequest req) {
        JsonResponse json = new JsonResponse();
        try {
            req.logout();
            json.setStatus("SUCCESS");
            req.getSession().invalidate();
        } catch (ServletException e) {
            e.printStackTrace();
            json.setStatus("FAILED");
            json.setErrorMsg("Logout failed on backend");
        }
        return Response.ok().entity(json).build();
    }
 
    @POST
    @Path("register")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response register(UserDTO newUser, @Context HttpServletRequest req) {
        JsonResponse json = new JsonResponse();
        json.setData(newUser);
        if (newUser.getPassword1().length() == 0 || !newUser.getPassword1().equals(newUser.getPassword2())) {
            json.setErrorMsg("Both passwords have to be the same");
            json.setStatus("FAILED");
            return Response.ok().entity(json).build();
        }
        User user = new User(newUser);
        List<Group> groups = new ArrayList<Group>();
        //groups.add(Group.ADMINISTRATOR);
        groups.add(Group.USER);
        groups.add(Group.DEFAULT);
        user.setGroups(groups);
        userRepository.save(user);
        json.setStatus("SUCCESS");
        return Response.ok().entity(json).build();
    }
 
}