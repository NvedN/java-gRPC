package ru.alfa4.grpcplayground.sec06;

import io.grpc.stub.StreamObserver;
import ru.alfa4.grpcplayground.sec06.requestHandlers.TransferRequestHandler;
import ru.nvn.models.sec06.TransferRequest;
import ru.nvn.models.sec06.TransferResponse;
import ru.nvn.models.sec06.TransferServiceGrpc;

public class TransferService extends TransferServiceGrpc.TransferServiceImplBase {
  @Override
  public StreamObserver<TransferRequest> transfer(
      StreamObserver<TransferResponse> responseObserver) {

    return new TransferRequestHandler(responseObserver);
  }
}
