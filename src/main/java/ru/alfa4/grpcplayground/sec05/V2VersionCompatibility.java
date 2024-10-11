package ru.alfa4.grpcplayground.sec05;

import com.google.protobuf.InvalidProtocolBufferException;
import lombok.extern.slf4j.Slf4j;
import ru.alfa4.grpcplayground.sec05.parser.V1Parser;
import ru.alfa4.grpcplayground.sec05.parser.V2Parser;
import ru.alfa4.grpcplayground.sec05.parser.V3Parser;
import ru.nvn.models.sec05.v2.Television;
import ru.nvn.models.sec05.v2.Type;

@Slf4j
public class V2VersionCompatibility {
  public static void main(String[] args) throws InvalidProtocolBufferException {

    var tv = Television.newBuilder().setBrand("samsung").setModel(2019).setType(Type.OLED).build();

    V1Parser.parse(tv.toByteArray());
    V2Parser.parse(tv.toByteArray());
    V3Parser.parse(tv.toByteArray());
  }
}
