package sec12;

import common.AbstractChannelTest;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import ru.alfa4.grpcplayground.common.GrpcServer;
import ru.alfa4.grpcplayground.sec12.BankService;
import ru.nvn.models.sec12.BankServiceGrpc;

public class AbstractTest extends AbstractChannelTest {

  private final GrpcServer grpcServer = GrpcServer.create(new BankService());
  protected BankServiceGrpc.BankServiceStub bankStub;
  protected BankServiceGrpc.BankServiceBlockingStub bankBlockingStub;

  @BeforeAll
  protected void setup() {
    this.grpcServer.start();
    this.bankStub = BankServiceGrpc.newStub(channel);
    this.bankBlockingStub = BankServiceGrpc.newBlockingStub(channel);
  }

  @AfterAll
  public void stop() {
    this.grpcServer.stop();
  }
}
