package controller;

import Repository.UserRepository;
import config.JwtTokenUtil;
import domain.JsonResponse;
import domain.User;
import domain.UserLogin;
import org.apache.commons.codec.digest.DigestUtils;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import static javax.ws.rs.core.HttpHeaders.AUTHORIZATION;

@Path("jwt")
@Stateless
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
            return Response.status(401).entity(json).build();
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
}
