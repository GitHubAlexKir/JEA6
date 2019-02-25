package Repository;

import domain.Account;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.List;
import java.util.Set;

import static javax.transaction.Transactional.TxType.REQUIRED;

@Stateless
public class AccountRepository {

   @PersistenceContext(unitName = "myPU")
   private EntityManager em;
   private Validator validator;

    public AccountRepository() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        this.validator = factory.getValidator();
    }


   @Transactional(REQUIRED)
   public String create(Account account)
   {
       if (validateAccount(account).isEmpty())
       {
           em.persist(account);
           return account.toString();
       }
       else
       {
           return validateAccount(account);
       }
   }

   public List<Account> findAll() {
       return em.createQuery("SELECT p FROM Account p", Account.class)
                      .getResultList();
   }

   public Account find(Long id) {
       return em.find(Account.class, id);
   }

   private String validateAccount(Account account)
   {
       StringBuilder errorMessage = new StringBuilder();
       Set<ConstraintViolation<Account>> violations = validator.validate(account);
       for (ConstraintViolation<Account> violation : violations) {
           errorMessage.append(violation.getMessage() + "\n");
       }
       return errorMessage.toString();
   }
}