package filter;

import config.JwtTokenUtil;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
/**
 * @author Alex
 * JWTToken filter voor controle op token en geldigheid ervan met extra check op role Worker
 **/
@Provider
@WorkerRoleNeeded
@Priority(Priorities.AUTHENTICATION)
public class WorkerRoleNeededFilter implements ContainerRequestFilter {
    private JwtTokenUtil  jwt = new JwtTokenUtil();
    @Override
    public void filter(ContainerRequestContext requestContext) {
        // Get the HTTP Authorization header from the request
        String authorizationHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
        // Extract the token from the HTTP Authorization header
        try {
            String token = authorizationHeader.substring("Bearer".length()).trim();
            // Validate the token
            if(jwt.validateToken(token)) {
                if (jwt.containsScopeInToken(token,"Worker")){
                }
                else {
                    requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
                }
            }
            else {
                requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
            }
        } catch (Exception e) {
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
        }

    }

}