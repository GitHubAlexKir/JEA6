package Repository;

import Interceptor.SimpleInterceptor;
import domain.order.Order;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

import static javax.transaction.Transactional.TxType.REQUIRED;

@Stateless
@Interceptors(SimpleInterceptor.class)
public class OrderRepository {

   @PersistenceContext(unitName = "myPU")
   private EntityManager em;

    public OrderRepository() {
    }

    @Transactional(REQUIRED)
   public Order create(Order order) {
        System.out.println("CREATING " +order.toString());
       em.persist(order);
       em.flush();
       return order;
   }

    public List<Order> findAll() {
       return em.createQuery("SELECT i FROM Order i", Order.class)
                      .getResultList();
   }

   public Order find(Long id) {
       return em.find(Order.class, id);
   }

    public boolean delete(long id) {
       Order orderToRemove = em.find(Order.class,id);
       orderToRemove.setItems(null);
       em.remove(orderToRemove);
       return true;
    }

    public List<Order> findAllWithUserEmail(String email) {
        return em.createQuery("SELECT o FROM Order o where o.userEmail = :userEmail", Order.class)
                .setParameter("userEmail", email)
                .getResultList();
    }

    public Order update(Order order) {
        em.merge(order);
        em.flush();
        return order;
    }
}