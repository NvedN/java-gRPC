package sec06;

import com.google.protobuf.Empty;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.nvn.models.sec06.BalanceCheckRequest;

@Slf4j
public class Lec01UnaryBlockingClientTest extends AbstractTest {

  @Test
  public void getBalanceTest() {
    var request = BalanceCheckRequest.newBuilder().setAccountNumber(1).build();

    var balance = this.blockingStub.getAccountBalance(request);
    log.info("Unary Balance received: {}", balance);
    Assertions.assertEquals(100, balance.getBalance());
  }

  @Test
  public void allAccountsTest() {
    var allAccounts = this.blockingStub.getAllAccounts(Empty.getDefaultInstance());
    log.info("all accounts size: {}", allAccounts.getAccountsCount());
    Assertions.assertEquals(10, allAccounts.getAccountsCount());
  }
}
