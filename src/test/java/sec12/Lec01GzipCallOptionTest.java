package sec12;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import ru.nvn.models.sec12.BalanceCheckRequest;

@Slf4j
public class Lec01GzipCallOptionTest extends AbstractTest {

  @Test
  public void gzipDemo() {
    var request = BalanceCheckRequest.newBuilder().setAccountNumber(1).build();
    var response = this.bankBlockingStub.withCompression("gzip").getAccountBalance(request);

    log.info("{}", response);
  }
}
