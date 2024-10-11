package sec06;

import common.ResponseObserver;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.nvn.models.sec06.AccountBalance;
import ru.nvn.models.sec06.Money;
import ru.nvn.models.sec06.WithDrawRequest;

@Slf4j
public class Lec03ServerStreamingClientTest extends AbstractTest {
  @Test
  public void blockingClientWithdrawTest() {
    var request = WithDrawRequest.newBuilder().setAccountNumber(2).setAmount(20).build();
    var iterator = this.blockingStub.withdraw(request);
    int count = 0;
    while (iterator.hasNext()) {
      log.info("received money : {}", iterator.next());
    }
    Assertions.assertEquals(2, count);
  }


  @Test
  public void asyncClientWithdrawTest(){
    var request = WithDrawRequest.newBuilder().setAccountNumber(2).setAmount(20).build();

    var observer = ResponseObserver.<Money>create();
    this.stub.withdraw(request,observer);
    observer.await();
    Assertions.assertEquals(2, observer.getItems().size());
    Assertions.assertEquals(10, observer.getItems().get(0).getAmount());
    Assertions.assertNull(observer.getThrowable());
  }
}
