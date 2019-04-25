package controller.invoice;

import Interceptor.SimpleInterceptor;
import Repository.InvoiceRepository;
import domain.invoice.Invoice;
import filter.JWTTokenNeeded;
import filter.OwnerRoleNeeded;
import org.json.JSONObject;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.*;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

@Path("/invoice")
@Stateless
@Interceptors(SimpleInterceptor.class)
@JWTTokenNeeded
public class InvoiceController {
    @EJB
    InvoiceRepository repo;

    public InvoiceController()
    {

    }
    @GET
    @Path("/ping")
    public Response invoice()
    {

        JSONObject response = new JSONObject();
        response.put("ping","welkom bij invoices");
        response.put("_links",getLinks(URI.create("http://localhost:8080/webshop/api/invoice")));
        return Response.ok(response.toString(2)).build();
    }
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getInvoice(@PathParam("id") long id)
    {
        JSONObject response = new JSONObject();
        Invoice invoice = repo.findWithOrderId(id);
        response.put("invoice",invoice.toMap());
        response.put("_links",getLinks(URI.create("http://localhost:8080/webshop/api/invoice/" + invoice.getId())));
        return Response.ok(response.toString(2)).build();
    }
    @GET
    @Path("/pdf/{id}")
    @Produces("text/plain")
    public Response getFile(@PathParam("id") long id) {
        String text = repo.findWithOrderId(id).toString();
        BufferedWriter output = null;
        try {
            File file = new File("example.txt");
            output = new BufferedWriter(new FileWriter(file));
            output.write(text);

            Response.ResponseBuilder response = Response.ok((Object) file);
            response.header("Content-Disposition","attachment; filename=test.txt");
            return response.build();
        } catch ( IOException e ) {
            e.printStackTrace();
        } finally {
            if ( output != null ) {
                try {
                    output.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        Response.ResponseBuilder response = Response.ok();
        return response.build();
    }


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getInvoices()
    {
        JSONObject response = new JSONObject();
        response.put("invoices",repo.findAll());
        response.put("_links",getLinks(URI.create("http://localhost:8080/webshop/api/invoice")));
        return Response.ok(response.toString(2)).build();
    }



    private Map<String, URI> getLinks(URI self)
    {
        Map<String, URI> links = new HashMap<>();
        String baseUri = "http://localhost:8080/webshop";
        links.put("self",self);
        links.put("save",URI.create(baseUri +"/api/invoice"));
        links.put("delete",URI.create(baseUri + "/api/invoice"));
        links.put("get",URI.create(baseUri + "/api/invoice/invoice-id"));
        links.put("get all",URI.create(baseUri + "/api/invoice"));
        return links;
    }
}
