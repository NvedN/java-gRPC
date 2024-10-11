package ru.alfa4.grpcplayground.sec06;

import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import ru.nvn.models.sec06.AccountBalance;
import ru.nvn.models.sec06.BalanceCheckRequest;
import ru.nvn.models.sec06.BankServiceGrpc;

import java.time.Duration;

@Slf4j
public class GrpcClient {

  public static void main(String[] args) throws InterruptedException {
    var channel = ManagedChannelBuilder.forAddress("localhost", 6565).usePlaintext().build();

    var stub = BankServiceGrpc.newStub(channel);

    stub.getAccountBalance(
        BalanceCheckRequest.newBuilder().setAccountNumber(2).build(),
        new StreamObserver<>() {

          @Override
          public void onNext(AccountBalance accountBalance) {
            log.info("{}", accountBalance);
          }

          @Override
          public void onError(Throwable throwable) {}

          @Override
          public void onCompleted() {
            log.info("Completed");
          }
        });

    log.info("done");

    Thread.sleep(1000);
  }
}
