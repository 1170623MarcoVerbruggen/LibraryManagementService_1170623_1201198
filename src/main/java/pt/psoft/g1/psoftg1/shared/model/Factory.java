package pt.psoft.g1.psoftg1.shared.model;

import pt.psoft.g1.psoftg1.authormanagement.model.Author;
import pt.psoft.g1.psoftg1.genremanagement.model.Genre;

import java.util.List;

public interface Factory {

    public Object create(String isbn, String title, String description, Genre genre, List<Author> authors, String photo);
}
