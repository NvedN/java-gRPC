package ru.alfa4.grpcplayground.sec04;

import lombok.extern.slf4j.Slf4j;
import ru.nvn.models.sec04.Person;
import ru.nvn.models.sec04.common.Address;
import ru.nvn.models.sec04.common.BodyStyle;
import ru.nvn.models.sec04.common.Car;

@Slf4j
public class Lec01Import {
  public static void main(String[] args) {
    //


      var address = Address.newBuilder().setCity("atlanta").build();
      var car = Car.newBuilder().setBodyStyle(BodyStyle.COUPE).build();

      var person = Person.newBuilder().setName("sam").setAge(12).setAddress(address).setCar(car).build();
      log.info("{}",person);
  }
}
