package controller;

import Interceptor.SimpleInterceptor;
import Repository.UserRepository;
import domain.Person;
import Repository.PersonRepository;

import javax.ejb.EJB;
import javax.interceptor.Interceptors;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@WebServlet(name = "TestServlet", urlPatterns = {"/admin"})
public class TestServlet extends HttpServlet {
    @EJB
    PersonRepository repo;
    @EJB
    UserRepository userRepo;
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
                RequestDispatcher view = request.getRequestDispatcher("createAccount.jsp");
                view.forward(request, response);
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String address = request.getParameter("address");
        Person person = new Person();
        person.setAddress(address);
        person.setName(name);
        repo.create(person);
        request.setAttribute("users",userRepo.findAll());
        RequestDispatcher view = request.getRequestDispatcher("view/users.jsp");
        view.forward(request, response);

    }
}
