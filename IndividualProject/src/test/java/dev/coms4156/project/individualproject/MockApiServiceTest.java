package dev.coms4156.project.individualproject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import dev.coms4156.project.individualproject.model.Book;
import dev.coms4156.project.individualproject.service.MockApiService;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * This class contains the unit tests for the MockApiService class.
 */
@SpringBootTest
public class MockApiServiceTest {

  private MockApiService service;

  @BeforeEach
  public void setUp() {
    service = new MockApiService();
  }

  @Test
  public void updateBook_withExistingBook_replacesBook() {
    List<Book> books = service.getBooks();
    Book original = books.get(0);

    Book updated = new Book(original.getTitle(), original.getId());
    updated.addCopy();

    service.updateBook(updated);

    Book result = null;
    for (Book b : service.getBooks()) {
      if (b.getId() == original.getId()) {
        result = b;
        break;
      }
    }

    assertNotNull(result);
    assertEquals(updated.getCopiesAvailable(), result.getCopiesAvailable());
  }
}