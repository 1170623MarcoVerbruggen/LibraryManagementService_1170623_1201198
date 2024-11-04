package pt.psoft.g1.psoftg1.bookmanagement.model;

import org.hibernate.StaleObjectStateException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import pt.psoft.g1.psoftg1.authormanagement.model.Author;
import pt.psoft.g1.psoftg1.bookmanagement.repositories.BookRepository;
import pt.psoft.g1.psoftg1.bookmanagement.services.*;
import pt.psoft.g1.psoftg1.genremanagement.model.Genre;


import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import java.util.ArrayList;
import java.util.Optional;

class BookTest {
    private final Isbn validIsbn = new Isbn("9782826012092");
    private final Title validTitle = new Title("Encantos de contar");
    private final Author validAuthor1 = new Author("João Alberto", "O João Alberto nasceu em Chaves e foi pedreiro a maior parte da sua vida.", null);
    private final Author validAuthor2 = new Author("Maria José", "A Maria José nasceu em Viseu e só come laranjas às segundas feiras.", null);
    private final Genre validGenre = new Genre("Fantasia");
    private List<Author> authors = new ArrayList<>();
    private Author authorStub;
    private Genre genreStub;
    private Book book;



    @BeforeEach
    void setUp(){
        // Inicializando stubs para Genre e Author
        genreStub = new Genre("Science Fiction");
        authorStub = new Author("Isaac Asimov", "Bio about Isaac Asimov", "photoURI");
        authors = Collections.singletonList(authorStub);

        // Criando uma instância real de Book para os testes
        book = new Book(
                new Isbn("9783161484100"),
                new Title("Foundation"),
                new Description("Description"),
                genreStub,
                authors,
                "photoURI"
        );
    }

    @Test
    void testBookCreation() {
        assertNotNull(book);
        assertEquals("978-3-16-148410-0", book.getIsbn());
        assertEquals("Foundation", book.getTitle().toString());
        assertEquals("Description", book.getDescription());
        assertEquals(genreStub, book.getGenre());
        assertEquals(authors, book.getAuthors());
        assertEquals("photoURI", book.getPhoto());
    }

    @Test
    void ensureIsbnNotNull(){
        authors.add(validAuthor1);
        assertThrows(IllegalArgumentException.class, () -> new Book(null, validTitle, null, validGenre, authors, null));
    }

    @Test
    void ensureTitleNotNull(){
        authors.add(validAuthor1);
        assertThrows(IllegalArgumentException.class, () -> new Book(validIsbn, null, null, validGenre, authors, null));
    }

    @Test
    void ensureGenreNotNull(){
        authors.add(validAuthor1);
        assertThrows(IllegalArgumentException.class, () -> new Book(validIsbn, validTitle, null,null, authors, null));
    }

    @Test
    void ensureAuthorsNotNull(){
        authors.add(validAuthor1);
        assertThrows(IllegalArgumentException.class, () -> new Book(validIsbn, validTitle, null, validGenre, null, null));
    }

    @Test
    void ensureAuthorsNotEmpty(){
        assertThrows(IllegalArgumentException.class, () -> new Book(validIsbn, validTitle, null, validGenre, authors, null));
    }

    @Test
    void ensureBookCreatedWithMultipleAuthors() {
        authors.add(validAuthor1);
        authors.add(validAuthor2);
        assertDoesNotThrow(() -> new Book(validIsbn, validTitle, null, validGenre, authors, null));
    }
}


class BookTestVerify{
    private Genre genreStub;
    private Author authorStub;
    private Book book;
    private List<Author> authors;

    @BeforeEach
    void setUp() {
        // Inicializando stubs para Genre e Author
        genreStub = new Genre("Science Fiction");
        authorStub = new Author("Isaac Asimov", "Bio about Isaac Asimov", "photoURI");
        authors = Collections.singletonList(authorStub);

        // Criando uma instância real de Book para os testes
        book = new Book(
                new Isbn("9783161484100"),
                new Title("Foundation"),
                new Description("Description"),
                genreStub,
                authors,
                "photoURI"
        );
    }

    @Test
    void testSetDescription() {
        // Criando uma instância real de UpdateBookRequest
        UpdateBookRequest updateRequest = new UpdateBookRequest();
        updateRequest.setDescription("Updated description");

        book.applyPatch(book.getVersion(), updateRequest);

        assertEquals("Updated description", book.getDescription());
    }

    @Test
    void testApplyPatch() {
        // Criando uma instância real de UpdateBookRequest
        UpdateBookRequest updateRequest = new UpdateBookRequest();
        updateRequest.setTitle("Updated Foundation");
        updateRequest.setDescription("Updated Description");
        updateRequest.setGenreObj(genreStub);
        updateRequest.setAuthorObjList(authors);

        Long currentVersion = book.getVersion();
        book.applyPatch(currentVersion, updateRequest);

        // Asserts para validar as mudanças
        assertEquals("Updated Foundation", book.getTitle().toString());
        assertEquals("Updated Description", book.getDescription());
        assertEquals(genreStub, book.getGenre());
        assertEquals(authors, book.getAuthors());
    }



    @Nested
    class BookAcceptanceTest {

        @Test
        void testBookAcceptanceCriteria() {
            // Defina as condições de aceitação
            String expectedTitle = "Foundation";
            String expectedDescription = "Description";
            Author validAuthor1 = new Author("João Alberto", "O João Alberto nasceu em Chaves e foi pedreiro a maior parte da sua vida.", null);
            List<Author> l = new ArrayList<>();
            l.add(validAuthor1);
            Book book = new Book(new Isbn("9783161484100"), new Title(expectedTitle), new Description(expectedDescription), new Genre("Science Fiction"), l, "photoURI");

            // Verifique as condições de aceitação
            assertEquals(expectedTitle, book.getTitle().toString());
            assertEquals(expectedDescription, book.getDescription());
        }
    }

    @Nested
    @DataJpaTest
    class BookIntegrationTest {

        @Autowired
        private BookRepository bookRepository;

        private Book book;
        private Genre genre;
        private Author author;

        @BeforeEach
        void setUp() {
            genre = new Genre("Science Fiction");
            author = new Author("Isaac Asimov", "Bio about Isaac Asimov", "photoURI");
            book = new Book(new Isbn("9783161484100"), new Title("Foundation"), new Description("Description"), genre, Collections.singletonList(author), "photoURI");
        }

        @AfterEach
        void tearDown() {
            bookRepository.delete(book);
        }

        @Test
        @Rollback
        void testBookSaveAndFind() {
            bookRepository.save(book);
            Optional<Book> foundBook = bookRepository.findByIsbn(book.getIsbn());
            assertTrue(foundBook.isPresent());
            assertEquals("Foundation", foundBook.get().getTitle().toString());
        }
    }

}