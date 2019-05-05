package repository;

import Interceptor.SimpleInterceptor;
import domain.invoice.Invoice;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

import static javax.transaction.Transactional.TxType.REQUIRED;
/**
 * @author Alex
 * InvoiceBean
 **/
@Stateless
@Interceptors(SimpleInterceptor.class)
public class InvoiceRepository {

   @PersistenceContext
   private EntityManager em;
    //Invoice aanmaken
   @Transactional(REQUIRED)
   public Invoice save(Invoice invoice) {
       em.persist(invoice);
       em.flush();
       return invoice;
   }
    //Invoices ophalen
   public List<Invoice> findAll() {
       return em.createQuery("SELECT i FROM Invoice i", Invoice.class)
                      .getResultList();
   }
    //Invoice ophalen
   public Invoice find(Long id) {
       return em.find(Invoice.class, id);
   }

    //Invoice vinden met Order Id
    public Invoice findWithOrderId(long id) {
        return em.createQuery("SELECT i FROM Invoice i where i.orderId = :orderId", Invoice.class)
                .setParameter("orderId", id)
                .getSingleResult();
    }
}