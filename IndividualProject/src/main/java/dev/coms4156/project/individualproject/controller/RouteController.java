package dev.coms4156.project.individualproject.controller;

import dev.coms4156.project.individualproject.model.Book;
import dev.coms4156.project.individualproject.service.MockApiService;
import java.util.ArrayList;
import java.util.Collections;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * This class contains all the API routes for the application.
 */
@RestController
public class RouteController {

  private final MockApiService mockApiService;

  public RouteController(MockApiService mockApiService) {
    this.mockApiService = mockApiService;
  }

  @GetMapping({"/", "/index"})
  public String index() {
    return "Welcome to the home page! In order to make an API call direct your browser "
        + "or Postman to an endpoint.";
  }

  /**
   * Returns the details of the specified book.
   *
   * @param id An {@code int} representing the unique identifier of the book to retrieve.
   *
   * @return A {@code ResponseEntity} containing either the matching {@code Book} object with an
   *         HTTP 200 response, or a message indicating that the book was not
   *         found with an HTTP 404 response.
   */
  @GetMapping({"/book/{id}"})
  public ResponseEntity<?> getBook(@PathVariable int id) {
    for (Book book : mockApiService.getBooks()) {
      if (book.getId() == id) {
        return new ResponseEntity<>(book, HttpStatus.OK);
      }
    }

    return new ResponseEntity<>("Book not found.", HttpStatus.NOT_FOUND);
  }

  /**
   * Returns a list of all the books with available copies.
   *
   * @return A {@code ResponseEntity} containing a list of available {@code Book} objects with an
   *         HTTP 200 response if successful, or a message indicating an error occurred with an
   *         HTTP 500 response.
   */
  @GetMapping({"/books/available"})
  public ResponseEntity<?> getAvailableBooks() {
    try {
      ArrayList<Book> availableBooks = new ArrayList<>();

      for (Book book : mockApiService.getBooks()) {
        if (book.hasCopies()) {
          availableBooks.add(book);
        }
      }

      return new ResponseEntity<>(availableBooks, HttpStatus.OK);
    } catch (Exception e) {
      System.err.println(e);
      return new ResponseEntity<>("Error occurred when getting all available books",
          HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }



  /**
   * Adds a copy to the {@code Book} object if it exists.
   *
   * @param bookId An {@code Integer} representing the unique id of the book.
   * @return A {@code ResponseEntity} containing the updated {@code Book} object with an
   *         HTTP 200 response if successful or HTTP 404 if the book is not found,
   *         or a message indicating an error occurred with an HTTP 500 code.
   */
  @PatchMapping({"/book/{bookId}/add"})
  public ResponseEntity<?> addCopy(@PathVariable Integer bookId) {
    try {
      for (Book book : mockApiService.getBooks()) {
        if (bookId.equals(book.getId())) {
          book.addCopy();
          return new ResponseEntity<>(book, HttpStatus.OK);
        }
      }

      return new ResponseEntity<>("Book not found.", HttpStatus.NOT_FOUND);
    } catch (Exception e) {
      System.err.println(e);
      return new ResponseEntity<>("Error occurred when adding a copy.",
          HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  /**
   * Returns a list of 10 unique recommended books, consisting of 5 most popular and 5 random books.
   *
   * @return A {@code ResponseEntity} containing a list of 10 {@code Book} objects with an
   *         HTTP 200 response if successful, or a message indicating an error occurred with an
   *         HTTP 500 response.
   */
  @GetMapping({"/books/recommendation"})
  public ResponseEntity<?> getRecommendations() {
    try {
      ArrayList<Book> allBooks = mockApiService.getBooks();

      ArrayList<Book> sortedBooks = new ArrayList<>(allBooks);
      sortedBooks.sort((a, b) -> Integer.compare(
          b.getAmountOfTimesCheckedOut(),
          a.getAmountOfTimesCheckedOut()
      ));

      ArrayList<Book> recommendations = new ArrayList<>(sortedBooks.subList(0, 5));

      ArrayList<Book> remainingBooks = new ArrayList<>(allBooks);
      remainingBooks.removeAll(recommendations);

      Collections.shuffle(remainingBooks);
      recommendations.addAll(remainingBooks.subList(0, 5));

      return new ResponseEntity<>(recommendations, HttpStatus.OK);

    } catch (Exception e) {
      System.err.println(e);
      return new ResponseEntity<>("Error occurred when generating recommendations.",
          HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  /**
   * Checks out a book by its ID if available.
   *
   * @param bookId An {@code Integer} representing the unique id of the book.
   * @return A {@code ResponseEntity} containing the updated {@code Book} object with an
   *         HTTP 200 response if successful, an HTTP 404 response if the book is not found,
   *         an HTTP 409 response if no copies are available, or a message indicating an
   *         error occurred with an HTTP 500 code.
   */
  @PatchMapping({"/checkout"})
  public ResponseEntity<?> checkoutBook(@RequestParam Integer bookId) {
    try {
      for (Book book : mockApiService.getBooks()) {
        if (bookId.equals(book.getId())) {
          if (!book.hasCopies()) {
            return new ResponseEntity<>("No copies available for checkout.",
                HttpStatus.CONFLICT);
          }

          book.checkoutCopy();
          return new ResponseEntity<>(book, HttpStatus.OK);
        }
      }

      return new ResponseEntity<>("Book not found.", HttpStatus.NOT_FOUND);
    } catch (Exception e) {
      System.err.println(e);
      return new ResponseEntity<>("Error occurred when checking out book.",
          HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}
