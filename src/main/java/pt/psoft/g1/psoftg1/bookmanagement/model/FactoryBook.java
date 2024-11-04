package pt.psoft.g1.psoftg1.bookmanagement.model;
import org.springframework.web.multipart.MultipartFile;
import pt.psoft.g1.psoftg1.authormanagement.model.Author;
import pt.psoft.g1.psoftg1.bookmanagement.services.CreateBookRequest;
import pt.psoft.g1.psoftg1.exceptions.NotFoundException;
import pt.psoft.g1.psoftg1.genremanagement.model.Genre;
import pt.psoft.g1.psoftg1.shared.model.Factory;
import pt.psoft.g1.psoftg1.shared.model.Photo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FactoryBook implements Factory {

    public FactoryBook() {}

    @Override
    public Book create(String isbn, String title, String description, Genre genre,List<Author> authors,String photo) {

        Isbn i = new Isbn(isbn);
        Title t = new Title(title);
        Description d = new Description(description);

        return new Book(i,t,d,genre,authors,photo);

    }
}


