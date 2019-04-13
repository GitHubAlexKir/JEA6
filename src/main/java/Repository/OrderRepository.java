package Repository;

import Interceptor.SimpleInterceptor;
import com.google.gson.Gson;
import domain.order.Order;
import org.json.JSONObject;
import service.Sender;

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
   private Sender sender = new Sender("Shipment");

   @Transactional(REQUIRED)
   public Order create(Order order) {
       em.persist(order);
       em.flush();
       JSONObject jsonObject = new JSONObject();
       jsonObject.put("order",order.toMap());
       sender.send(jsonObject.toString(2));
       return order;
   }

    public List<Order> findOrdersWithUserEmail(String userEmail) {
        return em.createQuery("SELECT o FROM Order o where o.userEmail = :userEmail", Order.class)
                .setParameter("userEmail", userEmail)
                .getResultList();
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
}