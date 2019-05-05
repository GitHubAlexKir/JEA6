package repository;
import domain.authentication.User;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import static javax.transaction.Transactional.TxType.REQUIRED;

/**
 * @author Alex
 * UserBean
 **/
@Stateless
public class UserRepository {
  
    @PersistenceContext
    private EntityManager em;

    //User opslaan en terugsturen
    @Transactional(REQUIRED)
    public User save(User user) {
        em.persist(user);
        em.flush();
        return user;
    }
    //User updaten
    public void update(User user) {
        em.merge(user);
    }
    //User met email verwijderen(als die bestaat)
    public void remove(String email) {
        User user = find(email);
        if (user != null) {
            em.remove(user);
        }
    }
    //User verwijderen met Object(als die bestaat)
    public void remove(User user) {
        if (user != null && user.getEmail()!=null && em.contains(user)) {
            em.remove(user);
        }
    }
    //User ophalen
    public User find(String email) {
        return em.find(User.class, email);
    }
    //User loskoppelen van EntityManager
    public void detach(User user) {
        em.detach(user);
    }
    //User login
    public boolean login(String email, String password) {
        System.out.println(find(email).getPassword().equals(password));
        return find(email).getPassword().equals(password);
    }
}