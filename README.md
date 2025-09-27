# COMS 4156 Miniproject
This is the GitHub repository for the Individual Project for COMS 4156 Advanced Software Engineering.

## Building and Running a Local Instance
To build and run the service locally, you will need to install the following dependencies:

1. **Maven 3.9.5**  
   Download Maven from [https://maven.apache.org/download.cgi](https://maven.apache.org/download.cgi).  
   Follow the installation instructions, and make sure to add the `bin` directory to your system PATH variable.

2. **JDK 17 (Temurin Distribution)**  
   This project was developed using [Temurin JDK 17](https://adoptium.net/temurin/releases/). Install JDK 17 and set it as your active Java version.

3. **IntelliJ IDEA (Recommended IDE)**  
   This project used [IntelliJ IDEA](https://www.jetbrains.com/idea/download/) for development, though any IDE that supports Maven and Java 17 will work.

4. **Cloning the Repository**  
   Open IntelliJ and choose the option to clone from GitHub. Copy the HTTPS URL from the green **Code** button in the GitHub repo and provide it to IntelliJ to clone.

5. **Building the Project**  
   To build the project, run the following command from the project root:
   ```
   mvn clean package
   ```  
   This will compile the source code, run all tests, and package the project.


6. **Running the Application**  
   You can run the main application by executing:
      ```
      mvn spring-boot:run
      ```  

7. **Running Code Quality Tools**  
   **Checkstyle**:
      ```
      mvn checkstyle:check
      mvn checkstyle:checkstyle   # Generates a report under target/site/checkstyle.html
      ```  
   **PMD**:
      ```
      mvn pmd:check
      mvn pmd:pmd   # Generates a report under target/site/pmd.html
      ```  
   **JaCoCo (Coverage Report)**:
      ```
      mvn test jacoco:report
      ```  
   Reports will be available under:
      ```
      target/site/jacoco/index.html
      ```  

---

## Running Tests
Unit tests are located under the `src/test/java` directory.

- **Running all tests**:
  ```
  mvn clean test
  ```  

- **From IntelliJ**: Right-click on any class inside `src/test/java` to run tests.

The test suite includes four classes:
- `BookUnitTests.java`
- `RouteControllerTest.java`
- `MockApiServiceTest.java`
- `IndividualProjectApplicationTest.java`


---

## Endpoints
This service exposes REST endpoints defined in `RouteController.java`. Below is a breakdown of supported endpoints:

#### GET `/` or `/index`
* **Description**: Landing page for the service. Provides a welcome message and instructions on how to make API calls.
* **Output**: A plain-text message.
* **Success**:
    - HTTP 200 OK – Returns the welcome message.

#### GET `/book/{id}`
* **Description**: Returns the details of a specified book.
* **Input Parameters**: `id` (int) – the ID of the book.
* **Output**: A `Book` object if found, or an error message indicating that the book was not found.
* **Success**:
    - HTTP 200 OK – Returns the book with the requested ID.
* **Failure**:
    - HTTP 404 Not Found - "Book not found."

#### GET `/books/available`
* **Description**: Returns a list of all the books with available copies.
* **Input Parameters**: None.
* **Output**: A list of `Book` objects that have available copies, or a message indicating an error occurred.
* **Success**:
    - HTTP 200 OK – Returns the list of available books.
* **Failure**:
    - HTTP 500 Internal Server Error - "Error occurred when getting all available books"

#### PATCH `/book/{bookId}/add`
* **Description**: Adds a copy of the specified book if it exists.
* **Input Parameters**: `bookId` (int) – the ID of the book.
* **Output**: The updated `Book` object after a copy has been added, or an appropriate error message.
* **Success**:
    - HTTP 200 OK – Copy successfully added.
* **Failure**:
    - HTTP 404 Not Found – "Book not found."
    - HTTP 500 Internal Server Error – "Error occurred when adding a copy."

#### GET `/books/recommendation`
* **Description**: Returns a list of 10 recommended books, consisting of:
    - 5 most popular books (by number of times checked out)
    - 5 randomly selected books from the remaining pool.
* **Input Parameters**: None
* **Output**: A list of 10 `Book` objects, or a message indicating an error occurred.
* **Success**:
    - HTTP 200 OK – Returns the recommendation list.
* **Failure**:
    - HTTP 500 Internal Server Error – "Error occurred when generating recommendations."

#### PATCH `/checkout`
* **Description**: Checks out a book by its ID if available.
* **Input Parameters**:`bookId` (int) – the ID of the book.
* **Output**: The updated `Book` object if successful, or an error message otherwise.
* **Success**:
    - HTTP 200 OK – Checkout successful, returns updated book details.
* **Failure**:
    - HTTP 404 Not Found – "Book not found."
    - HTTP 409 Conflict – "No copies available for checkout."
    - HTTP 500 Internal Server Error – "Error occurred when checking out book."

---

## Continuous Integration
This repository uses **GitHub Actions** for continuous integration. On every push or pull request to `main`, the following steps are executed:

1. Build the project with Maven
2. Run all tests
3. Run Checkstyle
4. Run PMD
5. Generate JaCoCo coverage reports

The CI workflow file can be found under `.github/workflows/maven.yml`

To view the latest results, visit the **Actions** tab in this repository on GitHub.

---

## Tools Used
- **Maven** – Build automation and dependency management
- **Temurin JDK 17** – Development runtime
- **Spring Boot** – Application framework
- **JUnit & Mockito** – Unit testing and mocking framework
- **Checkstyle** – Code style analysis
- **PMD** – Static analysis
- **JaCoCo** – Code coverage reporting
- **GitHub Actions** – Continuous Integration