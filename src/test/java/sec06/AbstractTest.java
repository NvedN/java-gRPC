package sec06;

import common.AbstractChannelTest;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import ru.alfa4.grpcplayground.common.GrpcServer;
import ru.alfa4.grpcplayground.sec06.BankService;
import ru.nvn.models.sec06.BankServiceGrpc;

public abstract class AbstractTest extends AbstractChannelTest {

  private final GrpcServer grpcServer = GrpcServer.create(new BankService());
  protected BankServiceGrpc.BankServiceBlockingStub blockingStub;
  protected BankServiceGrpc.BankServiceStub stub;

  @BeforeAll
  public void setup() {
    this.grpcServer.start();
    this.blockingStub = BankServiceGrpc.newBlockingStub(channel);
    this.stub = BankServiceGrpc.newStub(channel);

  }

  @AfterAll
  public void stop() {
    this.grpcServer.stop();
  }
}
