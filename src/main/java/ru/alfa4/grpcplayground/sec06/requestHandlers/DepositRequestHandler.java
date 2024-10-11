package ru.alfa4.grpcplayground.sec06.requestHandlers;

import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import ru.alfa4.grpcplayground.sec06.repository.AccountRepository;
import ru.nvn.models.sec06.AccountBalance;
import ru.nvn.models.sec06.DepositRequest;

@Slf4j
public class DepositRequestHandler implements StreamObserver<DepositRequest> {

  private final StreamObserver<AccountBalance> response;
  private int accountNumber;

  public DepositRequestHandler(StreamObserver<AccountBalance> response) {
    this.response = response;
  }

  @Override
  public void onNext(DepositRequest depositRequest) {

    switch (depositRequest.getRequestCase()) {
      case ACCOUNTNUMBER -> this.accountNumber = depositRequest.getAccountNumber();
      case MONEY -> AccountRepository.addAmount(
          this.accountNumber, depositRequest.getMoney().getAmount());
    }
  }

  @Override
  public void onError(Throwable throwable) {
    log.info("client error {}", throwable.getMessage());
  }

  @Override
  public void onCompleted() {
    var accountBalance =
        AccountBalance.newBuilder()
            .setAccountNumber(this.accountNumber)
            .setBalance(AccountRepository.getBalance(accountNumber))
            .build();

    response.onNext(accountBalance);
    response.onCompleted();
  }
}
