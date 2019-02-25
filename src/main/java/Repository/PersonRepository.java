package Repository;

import Interceptor.SimpleInterceptor;
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
public class PersonRepository {

   @PersistenceContext(unitName = "myPU")
   private EntityManager em;

   @Transactional(REQUIRED)
   public void create(Person person) {
       System.out.println(person.toString());
       em.persist(person);
   }

   public List<Person> findAll() {
       return em.createQuery("SELECT p FROM Person p", Person.class)
                      .getResultList();
   }

   public Person find(Long id) {
       return em.find(Person.class, id);
   }

}