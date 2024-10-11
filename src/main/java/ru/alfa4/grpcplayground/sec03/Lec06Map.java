package ru.alfa4.grpcplayground.sec03;

import lombok.extern.slf4j.Slf4j;
import ru.nvn.models.sec03.BodyStyle;
import ru.nvn.models.sec03.Car;
import ru.nvn.models.sec03.Dealer;

@Slf4j
public class Lec06Map {
  public static void main(String[] args) {
    var car1 =
        Car.newBuilder()
            .setMake("honda")
            .setModel("civic")
            .setYear(2000)
            .setBodyStyle(BodyStyle.COUPE)
            .build();
    var car2 =
        Car.newBuilder()
            .setMake("honda")
            .setModel("accord")
            .setYear(2002)
            .setBodyStyle(BodyStyle.SEDAN)
            .build();

    var dealer =
        Dealer.newBuilder()
            .putInventory(car1.getYear(), car1)
            .putInventory(car2.getYear(), car2)
            .build();

    log.info("{}", dealer);

    log.info("2002 ? : {}", dealer.containsInventory(2002));
    log.info("2003 ? : {}", dealer.containsInventory(2003));

    log.info("2002 model : {}", dealer.getInventoryOrThrow(2002).getBodyStyle());
  }
}
