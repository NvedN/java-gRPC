package ru.alfa4.grpcplayground.sec01;

import lombok.extern.slf4j.Slf4j;
import ru.nvn.models.sec01.PersonOuterClass;

@Slf4j
public class SimpleProtoDemo {

  public static void main(String[] args) {
    var person = PersonOuterClass.Person.newBuilder().setName("sam").setAge(12).build();

    log.info("{}",person);
  }
}
