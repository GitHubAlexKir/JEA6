package controller.invoice;

import Interceptor.SimpleInterceptor;
import repository.InvoiceRepository;
import domain.invoice.Invoice;
import filter.JWTTokenNeeded;
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
/**
 * @author Alex
 * InvoiceController
 **/
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
    //ping test
    @GET
    @Path("/ping")
    public Response invoice()
    {
        JSONObject response = new JSONObject();
        response.put("ping","welkom bij invoices");
        return Response.ok(response.toString(2)).build();
    }
    //Inovice ophalen met OrderId
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getInvoice(@PathParam("id") long id)
    {
        return Response.ok(repo.findWithOrderId(id)).build();
    }
    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteInvoice(@PathParam("id") long id)
    {
        return Response.ok(repo.delete(id)).build();
    }
    //Invoice downloaden(//TODO: PDF teruggeven)
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

    //Alle invoices ophalen
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getInvoices()
    {
        return Response.ok(repo.findAll()).build();
    }
}
