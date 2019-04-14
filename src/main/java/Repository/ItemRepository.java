package Repository;

import Interceptor.SimpleInterceptor;
import domain.item.Item;
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
   public Item save(Item item) {
       em.persist(item);
       return findWithProductNumber(item.getProductNumber());
   }

   public List<Item> findAll() {
       return em.createQuery("SELECT i FROM Item i", Item.class)
                      .getResultList();
   }

   public Item find(Long id) {
       return em.find(Item.class, id);
   }
    public Item findWithProductNumber(Long productNumber) {
        return em.createQuery("SELECT i FROM Item i where i.productNumber = :productNumber", Item.class)
                .setParameter("productNumber", productNumber)
                .getSingleResult();
    }

    public boolean delete(long id) {
       Item itemToRemove = em.find(Item.class,id);
       em.remove(itemToRemove);
       return true;
    }

    public Item update(Item item) {
       em.merge(item);
       em.flush();
       return item;
    }
    public void removeFromStock(List<Item> items) {
        for (Item i:items) {
            Item item = find(i.getId());
            item.setStock(item.getStock() - 1);
            update(item);
        }
    }
}