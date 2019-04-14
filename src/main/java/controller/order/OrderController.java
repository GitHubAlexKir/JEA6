package controller.order;

import Interceptor.SimpleInterceptor;
import Repository.OrderRepository;
import config.JwtTokenUtil;
import domain.authentication.User;
import domain.dto.OrderDTO;
import domain.order.Order;
import org.json.JSONObject;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import javax.json.JsonObject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Path("/order")
@Stateless
@Interceptors(SimpleInterceptor.class)
public class OrderController {
    @EJB
    OrderRepository repo;
    private JwtTokenUtil jwtTokenUtil = new JwtTokenUtil();
    public OrderController()
    {

    }
    @GET
    @Path("/ping")
    public Response order()
    {

        JSONObject response = new JSONObject();
        response.put("ping","welkom bij orders");
        response.put("_links",getLinks(URI.create("http://localhost:8080/1/api/order")));
        return Response.ok(response.toString(2)).build();
    }
    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOrder(@PathParam("id") long id)
    {
        JSONObject response = new JSONObject();
        Order order = repo.find(id);
        response.put("order",order.toMap());
        response.put("_links",getLinks(URI.create("http://localhost:8080/webshop/api/order/" + order.getId())));
        return Response.ok(response.toString(2)).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOrders(@Context HttpServletRequest req)
    {
        String authorizationHeader = req.getHeader("Authorization");
        // Extract the token from the HTTP Authorization header
        String token = authorizationHeader.substring("Bearer".length()).trim();
        String email = jwtTokenUtil.getUsernameFromToken(token);
        JSONObject response = new JSONObject();
        response.put("orders",repo.findAllWithUserEmail(email));
        response.put("_links",getLinks(URI.create("http://localhost:8080/webshop/api/order")));
        return Response.ok(response.toString(2)).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response save(OrderDTO orderDTO)
    {
        JSONObject response = new JSONObject();
        Order order = new Order(orderDTO);
        order = repo.create(order);
        response.put("order",order.toMap());
        response.put("_links",getLinks(URI.create("http://localhost:8080/webshop/api/order")));
        return Response.ok(response.toString(2)).build();
    }

    @DELETE
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response delete(@PathParam("id") long id)
    {
        JSONObject response = new JSONObject();
        response.put("Order_id deleted",repo.delete(id));
        response.put("_links",getLinks(URI.create("http://localhost:8080/webshop/api/order/" + id)));
        return Response.ok(response.toString(2)).build();
    }

    private Map<String, URI> getLinks(URI self)
    {
        Map<String, URI> links = new HashMap<>();
        String baseUri = "http://localhost:8080/webshop";
        links.put("self",self);
        links.put("save",URI.create(baseUri +"/api/order"));
        links.put("delete",URI.create(baseUri + "/api/order"));
        links.put("get",URI.create(baseUri + "/api/order/order-id"));
        links.put("get all",URI.create(baseUri + "/api/order"));
        return links;
    }
}
