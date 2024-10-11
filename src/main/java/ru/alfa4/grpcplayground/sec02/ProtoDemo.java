package ru.alfa4.grpcplayground.sec02;

import lombok.extern.slf4j.Slf4j;
import ru.nvn.models.sec02.Person;

@Slf4j
public class ProtoDemo {

  public static void main(String[] args) {

    var person1 = createPerson();
    var person2 = createPerson();



    var person3 = person1.toBuilder().setName("mike").build();
    log.info("person3: {}", person3);


    log.info("equals {}", person1.equals(person3));
    log.info(" == {}", (person1 == person3));


    var person4 = person1.toBuilder().clearName().build();
    log.info("person4: {}", person4);
  }

  private static Person createPerson() {
    return Person.newBuilder().setName("sam").setAge(12).build();
  }
}
