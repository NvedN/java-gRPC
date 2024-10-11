package ru.alfa4.grpcplayground.sec03;

import lombok.extern.slf4j.Slf4j;
import ru.nvn.models.sec03.Book;
import ru.nvn.models.sec03.Library;

import java.util.List;
import java.util.Set;

@Slf4j
public class Lec05Collection {
  public static void main(String[] args) {
    var book1 =
        Book.newBuilder()
            .setTitle("Harry Potter - part 1")
            .setAuthor(" jk Rowlling")
            .setPublicationYear(1997)
            .build();

    var book2 =
        book1.toBuilder().setTitle("Harry Potter - part 2").setPublicationYear(1998).build();
    var book3 =
        book1.toBuilder().setTitle("Harry Potter - part 3").setPublicationYear(1990).build();

    var library =
        Library.newBuilder()
            .setName("Fantasy library")
            .addAllBook(Set.of(book1, book2, book3))
            //            .addBook(book1)
            //            .addBook(book2)
            //            .addBook(book3)
            .build();

    log.info("{}", library);

  }
}
