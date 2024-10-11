package ru.alfa4.grpcplayground.sec03;

import lombok.extern.slf4j.Slf4j;
import ru.nvn.models.sec03.Person;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Slf4j
public class Lec02Serialization {

  private static final Path PATH = Path.of("person.txt");

  public static void main(String[] args) throws IOException {

    var person =
        Person.newBuilder()
//            .setLastName("sam")
//            .setAge(12)
//            .setEmail("sam@gmail.com")
            .setEmployed(false)
//            .setSalary(100.234)
//            .setBankAccountNumber(12345678901L)
//            .setBalance(-10000)
            .build();

    serialize(person);
    log.info("{}", deserialize());
    log.info("equals: {}", person.equals(deserialize()));
    log.info("bytes length: {}", person.toByteArray().length);
  }

  public static void serialize(Person person) throws IOException {
    person.writeTo(Files.newOutputStream(PATH));
  }

  public static Person deserialize() throws IOException {
    return Person.parseFrom(Files.newInputStream(PATH));
  }
}
