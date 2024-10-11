package ru.alfa4.grpcplayground.sec03;

import lombok.extern.slf4j.Slf4j;
import ru.nvn.models.sec03.Credentials;
import ru.nvn.models.sec03.Email;
import ru.nvn.models.sec03.Phone;

@Slf4j
public class Lec08OneOf {

  public static void main(String[] args) {

    var email = Email.newBuilder().setAddress("sam@gmail.com").setPassword("admin").build();
    var phone = Phone.newBuilder().setNumber(12344568).setCode(123).build();

    login(Credentials.newBuilder().setEmail(email).build());
    login(Credentials.newBuilder().setPhone(phone).build());

    login(Credentials.newBuilder().setEmail(email).setPhone(phone).build());

    //
  }

  private static void login(Credentials credentials){
    switch (credentials.getLoginTypeCase()){
      case EMAIL -> log.info("email -> {}", credentials.getEmail());
      case PHONE -> log.info("phone -> {}", credentials.getPhone());

    }
  }
}
