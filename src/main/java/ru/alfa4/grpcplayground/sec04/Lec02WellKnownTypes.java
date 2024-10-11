package ru.alfa4.grpcplayground.sec04;

import com.google.protobuf.Int32Value;
import com.google.protobuf.Timestamp;
import lombok.extern.slf4j.Slf4j;
import ru.nvn.models.sec04.Sample;

import java.time.Instant;

@Slf4j
public class Lec02WellKnownTypes {

  public static void main(String[] args) {

    var sample =
        Sample.newBuilder()
            .setAge(Int32Value.of(12))
            .setLoginTime(
                Timestamp.newBuilder().setSeconds(Instant.now().getEpochSecond()).build());




    log.info("{}",Instant.ofEpochSecond(sample.getLoginTime().getSeconds()));
  }
}
