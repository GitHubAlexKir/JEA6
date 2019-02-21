package domain;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "testing")
public class Person implements Serializable {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)

   private Long id;

   private String name;

   private String address;

   public Long getId() {
      return id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public String getAddress() {
      return address;
   }

   public void setAddress(String address) {
      this.address = address;
   }
}