package Repository;
import domain.authentication.User;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class UserRepository {
  
    @PersistenceContext
    private EntityManager em;
     
    public List<User> findAll() {
        return em.createQuery("SELECT u FROM User u", User.class)
                .getResultList();
    }
  
    public void save(User user) {
        em.persist(user);
    }
  
    public void update(User user) {
        em.merge(user);
    }
  
    public void remove(String email) {
        User user = find(email);
        if (user != null) {
            em.remove(user);
        }
    }
      
    public void remove(User user) {
        if (user != null && user.getEmail()!=null && em.contains(user)) {
            em.remove(user);
        }
    }
  
    public User find(String email) {
        return em.find(User.class, email);
    }
     
    public void detach(User user) {
        em.detach(user);
    }

    public boolean login(String email, String password) {
        System.out.println(find(email).getPassword().equals(password));
        return find(email).getPassword().equals(password);
    }
}