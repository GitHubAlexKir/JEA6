package controller.order;

import Interceptor.SimpleInterceptor;
import repository.InvoiceRepository;
import repository.ItemRepository;
import repository.OrderRepository;
import config.JwtTokenUtil;
import domain.dto.OrderDTO;
import domain.invoice.Invoice;
import domain.order.Order;
import filter.JWTTokenNeeded;
import filter.OwnerRoleNeeded;
import filter.WorkerRoleNeeded;
import org.json.JSONObject;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
/**
 * @author Alex
 * OrderController
 **/
@Path("/order")
@Stateless
@Interceptors(SimpleInterceptor.class)
@JWTTokenNeeded
public class OrderController {
    @EJB
    OrderRepository repo;
    @EJB
    InvoiceRepository invoiceRepo;
    @EJB
    ItemRepository itemRepo;

    private JwtTokenUtil jwtTokenUtil = new JwtTokenUtil();
    public OrderController()
    {

    }
    //Ping test
    @GET
    @Path("/ping")
    public Response order()
    {

        JSONObject response = new JSONObject();
        response.put("ping","welkom bij orders");
        response.put("_links",getLinks(URI.create("http://localhost:8080/1/api/order")));
        return Response.ok(response.toString(2)).build();
    }
    //Order ophalen met Id
    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOrder(@PathParam("id") long id)
    {
        JSONObject response = new JSONObject();
        Order order = repo.find(id);
        response.put("order",order.toJSONObject());
        response.put("_links",getLinks(URI.create("http://localhost:8080/webshop/api/order/" + order.getId())));
        return Response.ok(response.toString(2)).build();
    }
    //Orders voor werkers ophalen
    @WorkerRoleNeeded
    @GET
    @Path("/worker")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllOrders(@Context HttpServletRequest req)
    {
        JSONObject response = new JSONObject();
        response.put("orders",repo.findAll());
        response.put("_links",getLinks(URI.create("http://localhost:8080/webshop/api/order")));
        return Response.ok(response.toString(2)).build();
    }
    //Alle Orders ophalen van ingelogde User
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
    //Order aanmaken
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response save(OrderDTO orderDTO)
    {
        JSONObject response = new JSONObject();
        Order order = new Order(orderDTO);
        order = repo.create(order);
        itemRepo.removeFromStock(order.getItems());
        response.put("order",order.toJSONObject());
        response.put("_links",getLinks(URI.create("http://localhost:8080/webshop/api/order")));
        return Response.ok(response.toString(2)).build();
    }
    //Order op versturen zetten
    @POST
    @Path("/sent")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response sent(OrderDTO orderDTO)
    {
        JSONObject response = new JSONObject();
        Order order = repo.find(orderDTO.getId());
        order.setDispatched(true);
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.DATE, 1);  // number of days to add
        order.setExpectedArrival(dateFormat.format(c.getTime()));
        order = repo.update(order);
        response.put("order",order.toJSONObject());
        response.put("_links",getLinks(URI.create("http://localhost:8080/webshop/api/order")));
        return Response.ok(response.toString(2)).build();
    }
    //Order verwijderen(Alleen Owner mogelijk)
    @OwnerRoleNeeded
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
    //Order op betaald zetten
    @POST
    @Path("/paid/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response paid(@PathParam("id") long id)
    {
        JSONObject response = new JSONObject();
        response.put("order",repo.paid(id).toJSONObject());
        response.put("_links",getLinks(URI.create("http://localhost:8080/webshop/api/order/" + id)));
        Invoice invoice = new Invoice();
        invoice.setOrderId(id);
        invoice.setTotal(repo.find(id).getTotal());
        invoice.setTransaction_time(new Date());
        response.put("invoice",invoiceRepo.save(invoice));
        return Response.ok(response.toString(2)).build();
    }
    //Links meegeven bij json response
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
