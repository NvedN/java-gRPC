package ru.alfa4.grpcplayground.sec03;

import lombok.extern.slf4j.Slf4j;
import ru.nvn.models.sec03.*;

@Slf4j
public class Lec07DefaultValues {
  public static void main(String[] args) {

    var school = School.newBuilder()
            .setAddress(Address.newBuilder().setCity("atlanta").build())
            .build();

    log.info("{}", school.getId());
    log.info("{}", school.getName());
    log.info("{}", school.getAddress().getCity());

    log.info("is default? : {} ", school.getAddress().equals(Address.getDefaultInstance()));



    log.info("has address? {} ", school.hasAddress());


    var lib = Library.newBuilder().build();
    log.info("{}", lib.getBookList());

    var dealer = Dealer.newBuilder().build();

    log.info("{}", dealer.getInventoryMap());




    var car = Car.newBuilder().build();
    log.info("{}", car.getBodyStyle());


  }
}
