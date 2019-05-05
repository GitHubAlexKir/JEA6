package repository;

import Interceptor.SimpleInterceptor;
import domain.order.Order;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

import static javax.transaction.Transactional.TxType.REQUIRED;
/**
 * @author Alex
 * OrderBean
 **/
@Stateless
@Interceptors(SimpleInterceptor.class)
public class OrderRepository {

   @PersistenceContext
   private EntityManager em;
    //Order aanmaken en terugsturen
    @Transactional(REQUIRED)
   public Order create(Order order) {
        System.out.println("CREATING " +order.toString());
       em.persist(order);
       em.flush();
       return order;
   }
    //Alle orders ophalen
    public List<Order> findAll() {
       return em.createQuery("SELECT i FROM Order i", Order.class)
                      .getResultList();
   }
    //Oder ophalen
   public Order find(Long id) {
       return em.find(Order.class, id);
   }
    //Order verwijderen
    public boolean delete(long id) {
       Order orderToRemove = em.find(Order.class,id);
       orderToRemove.setItems(null);
       em.remove(orderToRemove);
       return true;
    }
    //Orders ophalen met email
    public List<Order> findAllWithUserEmail(String email) {
        return em.createQuery("SELECT o FROM Order o where o.userEmail = :userEmail", Order.class)
                .setParameter("userEmail", email)
                .getResultList();
    }
    //Opder updaten en terugsturen
    public Order update(Order order) {
        em.merge(order);
        em.flush();
        return order;
    }
    //Order op betaald zetten
    public Order paid(long id) {
        Order orderPaid = em.find(Order.class,id);
        orderPaid.setPaid(true);
        update(orderPaid);
        em.flush();
        return orderPaid;
    }
}