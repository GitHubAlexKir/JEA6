package authentication;

import Interceptor.SimpleInterceptor;
import Repository.UserRepository;
import config.JwtTokenUtil;
import domain.JsonResponse;
import domain.User;
import domain.UserLogin;
import org.apache.commons.codec.digest.DigestUtils;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;

import java.util.Enumeration;

import static javax.ws.rs.core.HttpHeaders.AUTHORIZATION;

@Path("jwt")
@Stateless
@Interceptors(SimpleInterceptor.class)
public class JwtController {

    private JwtTokenUtil jwtTokenUtil = new JwtTokenUtil();

    @EJB
    private UserRepository repo;

    @POST
    @Path("/login")
    public Response authenticateUser(UserLogin userLogin,
                                     @Context HttpServletRequest req) {
        JsonResponse json = new JsonResponse();
        String token = null;
        json.setData(userLogin);
        if (repo.login(userLogin.getEmail(), DigestUtils.sha512Hex(userLogin.getPassword()))) {
            User user = repo.find(userLogin.getEmail());
            token = jwtTokenUtil.generateToken(user);
            json.setData(token);
            json.setStatus("SUCCESS");
            return Response.ok().header(AUTHORIZATION, "Bearer " + token).entity(json).build();
        }
        else {
            json.setStatus("FAILED");
            json.setErrorMsg("Authentication failed");
            return Response.status(401).entity(json).header("Access-Control-Allow-Origin", "*").build();
        }
    }

    @POST
    @Path("/loginpostman")
    public Response authenticateUserPostMan(@FormParam("email") String email,@FormParam("password") String password,
                                     @Context HttpServletRequest req) {
        JsonResponse json = new JsonResponse();
        String token = null;
        if(req.getUserPrincipal() == null){
            try {
                req.login(email, password);
                User user = repo.find(email);
                token = jwtTokenUtil.generateToken(user);
                json.setData(token);
            } catch (ServletException e) {
                e.printStackTrace();
                json.setStatus("FAILED");
                json.setErrorMsg("Authentication failed");
                return Response.ok().entity(json).build();
            }
        }
        json.setStatus("SUCCESS");
        return Response.ok().header(AUTHORIZATION, "Bearer " + token).entity(json).build();
    }

    @GET
    @Path("user")
    public Response getUser(@Context HttpServletRequest req)
    {
        String authorizationHeader = req.getHeader("Authorization");
        // Extract the token from the HTTP Authorization header
        String token = authorizationHeader.substring("Bearer".length()).trim();
        User user = repo.find(jwtTokenUtil.getUsernameFromToken(token));
        repo.detach(user);
        user.setPassword(null);
        return Response.ok().entity(user).build();
    }

}
