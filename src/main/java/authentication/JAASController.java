package authentication;

import domain.authentication.User;
import domain.response.JsonResponse;
import repository.UserRepository;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * @author Alex
 * JAAS authenticatie
 **/
@Path("/jaas")
public class JAASController {
    @EJB
    private UserRepository userRepository;

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
    @GET
    @Path("secure")
    public Response secure(@Context HttpServletRequest req) {
    return Response.ok().entity("secret").build();
    }
}
