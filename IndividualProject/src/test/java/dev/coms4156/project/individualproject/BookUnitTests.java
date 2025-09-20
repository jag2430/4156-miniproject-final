package dev.coms4156.project.individualproject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import dev.coms4156.project.individualproject.model.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * This class contains the unit tests for the Book class.
 */
@SpringBootTest
public class BookUnitTests {

  private Book book;

  @BeforeEach
  public void setUpBookForTesting() {
    book = new Book("When Breath Becomes Air", 0);
  }

  @Test
  public void equalsBothAreTheSameTest() {
    Book cmpBook = book;
    assertEquals(cmpBook, book);
  }

  @Test
  public void equals_nullObject_returnsFalse() {
    assertFalse(book.equals(null));
  }

  @Test
  public void equals_differentClass_returnsFalse() {
    Object other = "NotABook";
    assertFalse(book.equals(other));
  }

  @Test
  public void equals_sameId_returnsTrue() {
    Book other = new Book("Same Id Book", 0);
    assertEquals(other, book);
  }

  @Test
  public void equals_differentId_returnsFalse() {
    Book other = new Book("Different Id Book", 5);
    assertNotEquals(other, book);
  }

  @Test
  public void returnCopy_matchingDate_returnsTrue() {
    String dueDate = book.checkoutCopy();
    boolean result = book.returnCopy(dueDate);
    assertTrue(result);
    assertEquals(1, book.getCopiesAvailable());
  }

  @Test
  public void returnCopy_nonMatchingDate_returnsFalse() {
    book.checkoutCopy();
    boolean result = book.returnCopy("2099-12-31");
    assertFalse(result);
  }

  @Test
  public void returnCopy_emptyReturnDate_returnsFalse() {
    boolean result = book.returnCopy("2099-12-31");
    assertFalse(result);
  }

  @Test
  public void deleteCopy_hasCopiesAvailable_returnsTrue() {
    book.addCopy();
    boolean result = book.deleteCopy();
    assertTrue(result);
  }

  @Test
  public void deleteCopy_noCopiesAvailable_returnsFalse() {
    book.deleteCopy();
    boolean result = book.deleteCopy();
    assertFalse(result);
  }

  @Test
  public void deleteCopy_hasCopiesButNoAvailable_returnsFalse() {
    book.deleteCopy();
    book.addCopy();
    book.checkoutCopy();

    boolean result = book.deleteCopy();
    assertFalse(result);
  }
}
