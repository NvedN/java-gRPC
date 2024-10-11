package common;

import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@Slf4j
public class ResponseObserver<T> implements StreamObserver<T> {

  private final List<T> list = Collections.synchronizedList(new ArrayList<>());

  private Throwable throwable;
  private final CountDownLatch latch;

  private ResponseObserver(int countDown) {
    this.latch = new CountDownLatch(countDown);
  }

  public static <T> ResponseObserver<T> create() {
    return new ResponseObserver<>(1);
  }

  public static <T> ResponseObserver<T> create(int countDown) {
    return new ResponseObserver<>(countDown);
  }

  @Override
  public void onNext(T t) {
    log.info("received item: {}", t);
    this.list.add(t);
  }

  @Override
  public void onError(Throwable throwable) {
    log.info("received error: {}", throwable.getMessage());
    this.throwable = throwable;
    this.latch.countDown();
  }

  @Override
  public void onCompleted() {
    log.info("completed");
    this.latch.countDown();
  }

  public void await() {
    try {
      this.latch.await(5, TimeUnit.SECONDS);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  public List<T> getItems() {
    return this.list;
  }

  public Throwable getThrowable() {
    return throwable;
  }
}
