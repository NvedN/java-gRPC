package ru.alfa4.grpcplayground.sec03;

import lombok.extern.slf4j.Slf4j;
import ru.nvn.models.sec03.Person;

@Slf4j
public class Lec01Scalar {

  public static void main(String[] args) {

    var person =
        Person.newBuilder()
            .setLastName("sam")
            .setAge(12)
            .setEmail("sam@gmail.com")
            .setEmployed(true)
            .setSalary(100.234)
            .setBankAccountNumber(12345678901L)
            .setBalance(-10000)
            .build();

    log.info("{}",person);
  }
}
