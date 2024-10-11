package ru.alfa4.grpcplayground.sec05.parser;

import com.google.protobuf.InvalidProtocolBufferException;
import lombok.extern.slf4j.Slf4j;
import ru.nvn.models.sec05.v2.Television;

@Slf4j
public class V3Parser {
  public static void parse(byte[] bytes) throws InvalidProtocolBufferException {
    var tv = Television.parseFrom(bytes);
    log.info("brand: {}", tv.getBrand());
    log.info("type: {}", tv.getType());

  }
}
