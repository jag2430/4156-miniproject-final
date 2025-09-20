package dev.coms4156.project.individualproject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import dev.coms4156.project.individualproject.controller.RouteController;
import dev.coms4156.project.individualproject.model.Book;
import dev.coms4156.project.individualproject.service.MockApiService;
import java.util.ArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;



/**
 * This class contains the unit tests for the RouteController class.
 */

@SpringBootTest
public class RouteControllerTest {

  private MockApiService mockApiService;
  private RouteController routeController;

  @BeforeEach
  public void setup() {
    mockApiService = Mockito.mock(MockApiService.class);
    routeController = new RouteController(mockApiService);
  }

  @Test
  public void getAvailableBooks_bookHasCopies_returns200() {
    Book book = new Book("Test Book", 1);
    ArrayList<Book> books = new ArrayList<>();
    books.add(book);
    when(mockApiService.getBooks()).thenReturn(books);

    ResponseEntity<?> response = routeController.getAvailableBooks();

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(books, response.getBody());
  }

  @Test
  public void getAvailableBooks_bookNoCopies_returns200() {
    Book book = new Book("Test Book", 1);
    book.deleteCopy();
    ArrayList<Book> books = new ArrayList<>();
    books.add(book);
    when(mockApiService.getBooks()).thenReturn(books);

    ResponseEntity<?> response = routeController.getAvailableBooks();

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(new ArrayList<>(), response.getBody());
  }

  @Test
  public void addCopy_bookFound_returns200() {
    Book book = new Book("Test Book", 1);
    ArrayList<Book> books = new ArrayList<>();
    books.add(book);
    when(mockApiService.getBooks()).thenReturn(books);

    ResponseEntity<?> response = routeController.addCopy(1);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(book, response.getBody());
  }

  @Test
  public void addCopy_bookNotFound_returns404() {
    Book book = new Book("Test Book", 1);
    ArrayList<Book> books = new ArrayList<>();
    books.add(book);
    when(mockApiService.getBooks()).thenReturn(books);

    ResponseEntity<?> response = routeController.addCopy(2);

    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    assertEquals("Book not found.", response.getBody());
  }

  @Test
  public void checkoutBook_bookFoundHasCopies_returns200() {
    Book book = new Book("Test Book", 1);
    ArrayList<Book> books = new ArrayList<>();
    books.add(book);
    when(mockApiService.getBooks()).thenReturn(books);

    ResponseEntity<?> response = routeController.checkoutBook(1);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(book, response.getBody());
  }

  @Test
  public void checkoutBook_bookNotFound_returns404() {
    Book book = new Book("Test Book", 1);
    ArrayList<Book> books = new ArrayList<>();
    books.add(book);
    when(mockApiService.getBooks()).thenReturn(books);

    ResponseEntity<?> response = routeController.checkoutBook(2);

    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    assertEquals("Book not found.", response.getBody());
  }

  @Test
  public void checkoutBook_bookFoundNoCopies_returns409() {
    Book book = new Book("Test Book", 1);
    book.deleteCopy();
    ArrayList<Book> books = new ArrayList<>();
    books.add(book);
    when(mockApiService.getBooks()).thenReturn(books);

    ResponseEntity<?> response = routeController.checkoutBook(1);

    assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
    assertEquals("No copies available for checkout.", response.getBody());
  }
}