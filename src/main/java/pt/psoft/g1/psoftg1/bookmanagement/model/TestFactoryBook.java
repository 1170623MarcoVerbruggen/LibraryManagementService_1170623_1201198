package pt.psoft.g1.psoftg1.bookmanagement.model;

import pt.psoft.g1.psoftg1.authormanagement.model.Author;
import pt.psoft.g1.psoftg1.genremanagement.model.Genre;

import java.util.List;

public class TestFactoryBook extends FactoryBook {
    @Override
    public Book create(String isbn, String title, String description, Genre genre, List<Author> authors, String photo) {
        return new Book(
                new Isbn(isbn),
                new Title(title),
                new Description(description),
                genre,
                authors,
                photo
        );
    }
}
