package repository;

import Interceptor.SimpleInterceptor;
import domain.item.Item;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

import static javax.transaction.Transactional.TxType.REQUIRED;
/**
 * @author Alex
 * ItemBean
 **/
@Stateless
@Interceptors(SimpleInterceptor.class)
public class ItemRepository {

   @PersistenceContext
   private EntityManager em;
    //Item aanmaken en terugsturen
   @Transactional(REQUIRED)
   public Item save(Item item) {
       em.persist(item);
       em.flush();
       return item;
   }
    //Alle items ophalen
   public List<Item> findAll() {
       return em.createQuery("SELECT i FROM Item i", Item.class)
                      .getResultList();
   }
    //Item ophalen
   public Item find(Long id) {
       return em.find(Item.class, id);
   }
    public Item findWithProductNumber(Long productNumber) {
        return em.createQuery("SELECT i FROM Item i where i.productNumber = :productNumber", Item.class)
                .setParameter("productNumber", productNumber)
                .getSingleResult();
    }
    //Item verwijderen
    public boolean delete(long id) {
       Item itemToRemove = em.find(Item.class,id);
       em.remove(itemToRemove);
       return true;
    }
    //Item updaten en terugsturen
    public Item update(Item item) {
       em.merge(item);
       em.flush();
       return item;
    }
    //Stock van Items verlagen
    public void removeFromStock(List<Item> items) {
        for (Item i:items) {
            Item item = find(i.getId());
            item.setStock(item.getStock() - 1);
            update(item);
        }
    }
}