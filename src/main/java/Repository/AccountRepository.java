package Repository;

import domain.Account;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

import static javax.transaction.Transactional.TxType.REQUIRED;

@Stateless
public class AccountRepository {

   @PersistenceContext(unitName = "myPU")
   private EntityManager em;

   @Transactional(REQUIRED)
   public void create(Account account) {
       em.persist(account);
   }

   public List<Account> findAll() {
       return em.createQuery("SELECT p FROM Account p", Account.class)
                      .getResultList();
   }

   public Account find(Long id) {
       return em.find(Account.class, id);
   }

}