package ru.alfa4.grpcplayground.sec06.requestHandlers;

import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import ru.alfa4.grpcplayground.sec06.repository.AccountRepository;
import ru.nvn.models.sec06.AccountBalance;
import ru.nvn.models.sec06.TransferRequest;
import ru.nvn.models.sec06.TransferResponse;
import ru.nvn.models.sec06.TransferStatus;

@Slf4j
public class TransferRequestHandler implements StreamObserver<TransferRequest> {

  private final StreamObserver<TransferResponse> responseObserver;

  public TransferRequestHandler(StreamObserver<TransferResponse> responseObserver) {
    this.responseObserver = responseObserver;
  }

  @Override
  public void onNext(TransferRequest transferRequest) {
    var status = this.transfer(transferRequest);
    log.info("Status ? : {}",status);
    if(TransferStatus.COMPLETED.equals(status)){
      var response =
              TransferResponse.newBuilder()
                      .setFromAccount(this.toAccountBalance(transferRequest.getFromAccount()))
                      .setToAccount(this.toAccountBalance(transferRequest.getToAccount()))
                      .setStatus(status)
                      .build();
      this.responseObserver.onNext(response);
    }

  }

  @Override
  public void onError(Throwable throwable) {
    log.info("client error: {}", throwable.getMessage());
  }

  @Override
  public void onCompleted() {
    log.info("transfer request stream completed");
    this.responseObserver.onCompleted();
  }

  private TransferStatus transfer(TransferRequest request) {
    var amount = request.getAmount();
    var fromAccount = request.getFromAccount();
    var toAccount = request.getToAccount();
    var status = TransferStatus.REJECTED;
    if (AccountRepository.getBalance(fromAccount) >= amount && (fromAccount != toAccount)) {
      AccountRepository.deductAmount(fromAccount, amount);
      AccountRepository.addAmount(toAccount, amount);
      status = TransferStatus.COMPLETED;
    }

    return status;
  }

  private AccountBalance toAccountBalance(int accountNumber) {
    return AccountBalance.newBuilder()
        .setAccountNumber(accountNumber)
        .setBalance(AccountRepository.getBalance(accountNumber))
        .build();
  }
}
