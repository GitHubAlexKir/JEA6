package service;

import domain.Person;

import javax.annotation.Resource;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;
import javax.transaction.UserTransaction;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class PersonRepository  {

    public String test() throws NamingException, SQLException {
        InitialContext ctx = new InitialContext();
        DataSource ds = (DataSource)ctx.lookup("jdbc/mysqlpool");
        Connection conn = ds.getConnection();
        PreparedStatement prep = conn.prepareStatement("Select name from Person where ID = 1");
        ResultSet rs = prep.executeQuery();
        String name = "fout";
        if (rs.next()) {
            name = rs.getString("name");
        }
        return name;

    }
    public String savePerson(Person person)
    {
        System.out.println("saving user");
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("myPU");
        EntityManager em = entityManagerFactory.createEntityManager();
        em.persist(person);

        return "Gelukt";

    }


}
