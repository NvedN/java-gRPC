package ru.alfa4.grpcplayground.sec03;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.protobuf.InvalidProtocolBufferException;
import lombok.extern.slf4j.Slf4j;
import ru.nvn.models.sec03.Person;

@Slf4j
public class Lec03PerformanceTest {

  private static final ObjectMapper mapper = new ObjectMapper();

  public static void main(String[] args) {
    //
    var protoPerson =
        Person.newBuilder()
            .setLastName("sam")
            .setAge(12)
            .setEmail("sam@gmail.com")
            .setEmployed(true)
            .setSalary(100.234)
            .setBankAccountNumber(12345678901L)
            .setBalance(-10000)
            .build();

    var jsonPerson =
        new JsonPerson("sam", 12, "sam@gmail.com", true, 100.234, 12345678901L, -10000);

    json(jsonPerson);
    proto(protoPerson);
//    for (int i = 0; i < 5; i++) {
//      runTest("json", () -> json(jsonPerson));
//      runTest("proto", () -> proto(protoPerson));
//    }
  }

  private static void proto(Person person) {
    try {
      var bytes = person.toByteArray();
      log.info("proto bytes length : {}", bytes.length);

      Person.parseFrom(bytes);
    } catch (InvalidProtocolBufferException e) {
      throw new RuntimeException(e);
    }
  }

  private static void json(JsonPerson person) {
    try {
      var bytes = mapper.writeValueAsBytes(person);
      log.info("json bytes length : {}", bytes.length);
      mapper.readValue(bytes, JsonPerson.class);

    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  private static void runTest(String testName, Runnable runnable) {
    var start = System.currentTimeMillis();
    for (int i = 0; i < 5_000_000; i++) {
      runnable.run();
    }
    var end = System.currentTimeMillis();
    log.info("time taken for {} - {} ms ", testName, (end - start));
  }
}
