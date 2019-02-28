package Repository;

import Interceptor.SimpleInterceptor;
import domain.Item;
import domain.Person;

import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

import static javax.transaction.Transactional.TxType.REQUIRED;

@Stateless

@Interceptors(SimpleInterceptor.class)
public class ItemRepository {

   @PersistenceContext(unitName = "myPU")
   private EntityManager em;

   @Transactional(REQUIRED)
   public Item create(Item item) {
       System.out.println(item.toString());
       em.persist(item);
       item.setId(1);
       return item;
   }

   public List<Item> findAll() {
       return em.createQuery("SELECT i FROM item i", Item.class)
                      .getResultList();
   }

   public Item find(Long id) {
       return em.find(Item.class, id);
   }

}