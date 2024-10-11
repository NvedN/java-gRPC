package ru.alfa4.grpcplayground.sec08;

import io.grpc.stub.StreamObserver;
import ru.nvn.models.sec08.GuessNumberGrpc;
import ru.nvn.models.sec08.GuessRequest;
import ru.nvn.models.sec08.GuessResponse;

public class GuessGame extends GuessNumberGrpc.GuessNumberImplBase {

  @Override
  public StreamObserver<GuessRequest> makeGuess(StreamObserver<GuessResponse> responseObserver) {
    return new GuessHandler(responseObserver);
  }
}
