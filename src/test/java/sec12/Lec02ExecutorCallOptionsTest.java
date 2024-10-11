package sec12;

import common.ResponseObserver;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import ru.nvn.models.sec12.WithDrawRequest;
import ru.nvn.models.sec12.Money;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Slf4j
public class Lec02ExecutorCallOptionsTest extends AbstractTest{

  @Test
  public void executorDemo() {
    var observer = ResponseObserver.<Money>create();
    var request = WithDrawRequest.newBuilder().setAccountNumber(1).setAmount(30).build();

    this.bankStub
            .withExecutor(Executors.newVirtualThreadPerTaskExecutor())
            .withdraw(request,observer);
  }
}
