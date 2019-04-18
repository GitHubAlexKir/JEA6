package Repository;

import Interceptor.SimpleInterceptor;
import domain.invoice.Invoice;

import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

import static javax.transaction.Transactional.TxType.REQUIRED;

@Stateless
@Interceptors(SimpleInterceptor.class)
public class InvoiceRepository {

   @PersistenceContext(unitName = "myPU")
   private EntityManager em;

   @Transactional(REQUIRED)
   public Invoice save(Invoice invoice) {
       em.persist(invoice);
       em.flush();
       return invoice;
   }

   public List<Invoice> findAll() {
       return em.createQuery("SELECT i FROM Invoice i", Invoice.class)
                      .getResultList();
   }

   public Invoice find(Long id) {
       return em.find(Invoice.class, id);
   }


}