package ru.alfa4.grpcplayground.sec07;

import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import ru.nvn.models.sec07.FlowControlServiceGrpc;
import ru.nvn.models.sec07.Output;
import ru.nvn.models.sec07.RequestSize;

import java.util.stream.IntStream;

@Slf4j
public class FlowControlService extends FlowControlServiceGrpc.FlowControlServiceImplBase {

  @Override
  public StreamObserver<RequestSize> getMessages(StreamObserver<Output> responseObserver) {
    return new RequestHandler(responseObserver);
  }

  private static class RequestHandler implements StreamObserver<RequestSize> {

    private final StreamObserver<Output> responseObserver;
    private Integer emitted;

    private RequestHandler(StreamObserver<Output> responseObserver) {
      this.responseObserver = responseObserver;
      this.emitted = 0;
    }

    @Override
    public void onNext(RequestSize requestSize) {
      IntStream.rangeClosed((emitted + 1), 100)
          .limit(requestSize.getSize())
          .forEach(
              i -> {
                log.info("emitting {}", i);
                responseObserver.onNext(Output.newBuilder().setValue(i).build());
              });

      emitted += requestSize.getSize();

      if (emitted >= 100) {
        responseObserver.onCompleted();
      }
    }

    @Override
    public void onError(Throwable throwable) {}

    @Override
    public void onCompleted() {
      this.responseObserver.onCompleted();
    }
  }
}
