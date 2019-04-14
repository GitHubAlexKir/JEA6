package authentication;

import Interceptor.SimpleInterceptor;
import Repository.UserRepository;
import config.JwtTokenUtil;
import domain.authentication.Privilege;
import domain.dto.UserDTO;
import domain.response.JsonResponse;
import domain.authentication.User;
import domain.authentication.UserLogin;
import org.apache.commons.codec.digest.DigestUtils;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

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
        try{
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
        }catch (Exception e){
            json.setStatus("FAILED");
            json.setErrorMsg("Authentication failed");
            return Response.status(401).entity(json).build();
        }

    }
    @POST
    @Path("/register")
    public Response registerUser(UserDTO newUser,
                                 @Context HttpServletRequest req) {
        JsonResponse json = new JsonResponse();
        json.setData(newUser);
        if (newUser.getPassword1().length() == 0 || !newUser.getPassword1().equals(newUser.getPassword2())) {
            json.setErrorMsg("Beide wachtwoorden moeten hetzelfde zijn");
            json.setStatus("Mislukt");
            return Response.status(409).entity(json).build();
        }
        try {
            if (repo.find(newUser.getEmail()) != null) {
                json.setErrorMsg("Email Is al geregistreerd.");
                json.setStatus("Mislukt");
                return Response.status(409).entity(json).build();
            }
        }catch (NullPointerException e){
            //not found
        }
        json = isValid(newUser.getPassword1(),json);
        if (json.getErrorMsg() != ""){
            return Response.status(409).entity(json).build();
        }
        User user = new User(newUser);
        List<Privilege> privileges = new ArrayList<Privilege>();
        privileges.add(Privilege.Customer);
        user.setPrivileges(privileges);
        try{
            repo.save(user);
            json.setData(user);
            return Response.ok().entity(json).build();
        }catch (Exception e){
            json.setStatus("FAILED");
            json.setErrorMsg("Creating user failed");
            return Response.status(500).entity(json).build();
        }

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
    private JsonResponse isValid(String passwordhere,  JsonResponse jsonResponse) {

        Pattern UpperCasePatten = Pattern.compile("[A-Z ]");
        Pattern lowerCasePatten = Pattern.compile("[a-z ]");
        Pattern digitCasePatten = Pattern.compile("[0-9 ]");
        jsonResponse.setErrorMsg("");
        if (passwordhere.length() < 12) {
            jsonResponse.setErrorMsg(jsonResponse.getErrorMsg() + "Wachtwoordlengte moet minimaal 12 tekens lang zijn\n");
        }
        if (!UpperCasePatten.matcher(passwordhere).find()) {
            jsonResponse.setErrorMsg(jsonResponse.getErrorMsg() + "Wachtwoord moet minimaal één hoofdletter bevatten\n");
        }
        if (!lowerCasePatten.matcher(passwordhere).find()) {
            jsonResponse.setErrorMsg(jsonResponse.getErrorMsg() + "Wachtwoord moet minimaal één kleine letter bevatten\n");
        }
        if (!digitCasePatten.matcher(passwordhere).find()) {
            jsonResponse.setErrorMsg(jsonResponse.getErrorMsg() + "Wachtwoord moet minimaal één cijfer hebben\n");
        }

        return jsonResponse;

    }

}
