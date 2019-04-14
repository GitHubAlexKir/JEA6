package filter;

import config.JwtTokenUtil;
import domain.authentication.Privilege;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.util.ArrayList;

@Provider

@OwnerRoleNeeded
@Priority(Priorities.AUTHENTICATION)
public class OwnerRoleNeededFilter implements ContainerRequestFilter {
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
                System.out.println("#### valid token : " + token);
                System.out.println(jwt.getAllClaimsFromToken(token).get("scopes").toString());
                System.out.println("BEFORE CONTAINS IN SCOPE : OWNERROLENEEDEDFILTER");
                if (jwt.containsScopeInToken(token)){
                    System.out.println("#### has Role Owner : " + token);
                }
                else {
                    System.out.println("#### Missing role owner : " + token);
                    requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
                }
            }
            else {
                System.out.println("#### invalid token : " + token);
                requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
            }
        } catch (Exception e) {
            System.out.println("#### no token found" + e.toString());
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
        }

    }

}