package ru.alfa4.grpcplayground.sec09;

import com.google.common.util.concurrent.Uninterruptibles;
import com.google.protobuf.Empty;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import ru.alfa4.grpcplayground.sec09.repository.AccountRepository;
import ru.alfa4.grpcplayground.sec09.validator.RequestValidator;
import ru.nvn.models.sec09.*;

import java.util.concurrent.TimeUnit;

@Slf4j
public class BankService extends BankServiceGrpc.BankServiceImplBase {

  @Override
  public void getAccountBalance(
      BalanceCheckRequest request, StreamObserver<AccountBalance> responseObserver) {

    RequestValidator.validateAccount(request.getAccountNumber())
        .map(Status::asRuntimeException)
        .ifPresentOrElse(
            responseObserver::onError, () -> sendAccountBalance(request, responseObserver));
  }

  private void sendAccountBalance(
      BalanceCheckRequest request, StreamObserver<AccountBalance> responseObserver) {
    var accountNumber = request.getAccountNumber();
    var balance = AccountRepository.getBalance(accountNumber);
    var accountBalance =
        AccountBalance.newBuilder().setAccountNumber(accountNumber).setBalance(balance).build();
    responseObserver.onNext(accountBalance);
    responseObserver.onCompleted();
  }

  @Override
  public void getAllAccounts(Empty request, StreamObserver<AllAccountsResponse> responseObserver) {

    var accounts =
        AccountRepository.getAllAccounts().entrySet().stream()
            .map(
                e ->
                    AccountBalance.newBuilder()
                        .setAccountNumber(e.getKey())
                        .setBalance(e.getValue())
                        .build())
            .toList();

    var response = AllAccountsResponse.newBuilder().addAllAccounts(accounts).build();

    responseObserver.onNext(response);
    responseObserver.onCompleted();
  }

  @Override
  public void withdraw(WithDrawRequest request, StreamObserver<Money> responseObserver) {
    RequestValidator.validateAccount(request.getAccountNumber())
        .or(() -> RequestValidator.isAmountDivisibleBy10(request.getAmount()))
        .or(
            () ->
                RequestValidator.hasSufficientBalance(
                    request.getAmount(), AccountRepository.getBalance(request.getAccountNumber())))
        .map(Status::asRuntimeException)
        .ifPresentOrElse(responseObserver::onError, () -> sendMoney(request, responseObserver));
  }

  private void sendMoney(WithDrawRequest request, StreamObserver<Money> responseObserver) {

    var accountNumber = request.getAccountNumber();
    var requestedAmount = request.getAmount();
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
