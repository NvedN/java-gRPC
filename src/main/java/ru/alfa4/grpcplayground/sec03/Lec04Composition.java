package ru.alfa4.grpcplayground.sec03;

import lombok.extern.slf4j.Slf4j;
import ru.nvn.models.sec03.Address;
import ru.nvn.models.sec03.School;
import ru.nvn.models.sec03.Student;

@Slf4j
public class Lec04Composition {
  public static void main(String[] args) {
    var address =
        Address.newBuilder().setStreet("123 main st").setCity("atlanta").setState("GA").build();
    var student = Student.newBuilder().setName("sam").setAddress(address).build();
    var school =
        School.newBuilder()
            .setId(1)
            .setName("High school")
            .setAddress(address.toBuilder().setStreet("234 main st").build())
            .build();

    log.info("school : {} ", school);
    log.info("student : {} ", student.getAddress().getStreet());

    //
  }
}
