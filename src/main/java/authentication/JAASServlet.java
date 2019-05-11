package authentication;

import Interceptor.SimpleInterceptor;
import domain.authentication.User;
import domain.response.JsonResponse;
import repository.UserRepository;

import javax.ejb.EJB;
import javax.interceptor.Interceptors;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * @author Alex
 * JAAS authenticatie servlet voor login pagina
 **/
@WebServlet(name = "JAASServlet", urlPatterns = {"/jaas"})
@Interceptors(SimpleInterceptor.class)
public class JAASServlet extends HttpServlet{
    @EJB
    private UserRepository userRepository;
    //jaas login pagina laden
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher view = request.getRequestDispatcher("JAASLogin.jsp");
        view.forward(request, response);
    }
    //jaas login
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        JsonResponse json = new JsonResponse();
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        if(request.getUserPrincipal() == null){
            try {
                request.login(email, password);
            } catch (ServletException e) {
                e.printStackTrace();
                json.setStatus("FAILED");
                json.setErrorMsg("Authentication failed");
                RequestDispatcher view = request.getRequestDispatcher("login-error.html");
                response.setStatus(401);
                view.forward(request, response);
            }
        }
        System.out.println("JAAS: " +request.getUserPrincipal().getName());
        System.out.println(request.getUserPrincipal());
        json.setStatus("SUCCESS");
        User user = userRepository.find(email);
        json.setData(user);
        userRepository.detach(user);
        user.setPassword(null);
        request.setAttribute("user",user);
        RequestDispatcher view = request.getRequestDispatcher("secure.jsp");
        view.forward(request, response);

    }
}
