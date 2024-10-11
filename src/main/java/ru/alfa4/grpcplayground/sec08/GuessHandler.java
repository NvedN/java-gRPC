package ru.alfa4.grpcplayground.sec08;

import io.grpc.stub.StreamObserver;
import ru.nvn.models.sec08.GuessRequest;
import ru.nvn.models.sec08.GuessResponse;
import ru.nvn.models.sec08.Result;

import java.util.concurrent.ThreadLocalRandom;

public class GuessHandler implements StreamObserver<GuessRequest> {

  private final StreamObserver<GuessResponse> responseObserver;

  private final int secret;
  private int attempt;

  public GuessHandler(StreamObserver<GuessResponse> responseObserver) {
    this.responseObserver = responseObserver;
    this.attempt = 0;
    this.secret = ThreadLocalRandom.current().nextInt(1, 101);
  }

  @Override
  public void onNext(GuessRequest guessRequest) {
    int guess = guessRequest.getGuess();
    if (guess > secret) {
      this.send(Result.TOO_HIGH);

    } else if (guess < secret) {
      this.send(Result.TOO_LOW);

    } else {
      this.send(Result.CORRECT);
      responseObserver.onCompleted();
    }
  }

  @Override
  public void onError(Throwable throwable) {}

  @Override
  public void onCompleted() {}

  private void send(Result result) {
    attempt++;
    var response = GuessResponse.newBuilder().setAttempt(attempt).setResult(result).build();
    this.responseObserver.onNext(response);
  }
}
