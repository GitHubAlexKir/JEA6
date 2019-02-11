package controller;

import domain.Person;
import test.PersonRepository;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

@WebServlet(name = "TestServlet", urlPatterns = {"/test"})
public class TestServlet extends HttpServlet {
    @EJB
    PersonRepository repo;
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
                RequestDispatcher view = request.getRequestDispatcher("index.jsp");
                view.forward(request, response);
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<String> woorden = Arrays.asList(new String[]{"test", "test2", "test3"});
        //PersonRepository repo = new PersonRepository();
        Person person = new Person();
        person.setAddress("straat 40");
        person.setName("Alex");
        //repo.create(person);
        repo.create(person);
        request.setAttribute("woorden", woorden);
        RequestDispatcher view = request.getRequestDispatcher("view/woorden.jsp");
        view.forward(request, response);

    }
}
