package sec06;

import common.ResponseObserver;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.nvn.models.sec06.AccountBalance;
import ru.nvn.models.sec06.BalanceCheckRequest;

@Slf4j
public class Lec02unaryAsyncClientTest extends AbstractTest {

  @Test
  public void getBalanceTest() {
    var request = BalanceCheckRequest.newBuilder().setAccountNumber(1).build();
    var observer = ResponseObserver.<AccountBalance>create();
    this.stub.getAccountBalance(request, observer);
    observer.await();
    Assertions.assertEquals(1, observer.getItems().size());
    Assertions.assertEquals(100, observer.getItems().get(0).getBalance());
    Assertions.assertNull(observer.getThrowable());
  }

}
