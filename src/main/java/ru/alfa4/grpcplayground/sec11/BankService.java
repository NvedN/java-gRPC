package ru.alfa4.grpcplayground.sec11;

import com.google.common.util.concurrent.Uninterruptibles;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import ru.alfa4.grpcplayground.sec11.repository.AccountRepository;
import ru.nvn.models.sec11.*;

import java.util.concurrent.TimeUnit;

@Slf4j
public class BankService extends BankServiceGrpc.BankServiceImplBase {

  @Override
  public void getAccountBalance(
      BalanceCheckRequest request, StreamObserver<AccountBalance> responseObserver) {
    var accountNumber = request.getAccountNumber();

    var balance = AccountRepository.getBalance(accountNumber);
    var accountBalance =
        AccountBalance.newBuilder().setAccountNumber(accountNumber).setBalance(balance).build();


    Uninterruptibles.sleepUninterruptibly(3, TimeUnit.SECONDS);

    responseObserver.onNext(accountBalance);
    responseObserver.onCompleted();
  }

  @Override
  public void withdraw(WithDrawRequest request, StreamObserver<Money> responseObserver) {

    var accountNumber = request.getAccountNumber();
    var requestedAmount = request.getAmount();
    var accountBalance = AccountRepository.getBalance(accountNumber);


    if (requestedAmount > accountBalance) {
      responseObserver.onError(Status.FAILED_PRECONDITION.asRuntimeException());
      return;
    }

    for (int i = 0; i < (requestedAmount / 10); i++) {
      //
      var money = Money.newBuilder().setAmount(10).build();
      responseObserver.onNext(money);
      log.info("money sent {}", money);
      AccountRepository.deductAmount(accountNumber, 10);
      Uninterruptibles.sleepUninterruptibly(1, TimeUnit.SECONDS);
    }

    responseObserver.onCompleted();
  }
}
