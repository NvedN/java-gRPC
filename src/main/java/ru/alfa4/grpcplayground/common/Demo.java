package ru.alfa4.grpcplayground.common;

import ru.alfa4.grpcplayground.sec10.BankService;

public class Demo {

  public static void main(String[] args) {
    GrpcServer.create(new BankService()).start().await();
  }
}
