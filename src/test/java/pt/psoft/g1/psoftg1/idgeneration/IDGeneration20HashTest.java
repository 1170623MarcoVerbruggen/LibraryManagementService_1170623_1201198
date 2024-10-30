package pt.psoft.g1.psoftg1.idgeneration;

import org.junit.jupiter.api.Test;
import pt.psoft.g1.psoftg1.idggeneration.IDGeneration;
import pt.psoft.g1.psoftg1.idggeneration.IDGeneration20Hash;

import static org.junit.jupiter.api.Assertions.*;

public class IDGeneration20HashTest {

    private static final int CHAR_LIMIT = 20;

    @Test
    void testGenerateIDConformsToFormat() {

        //arrange
        IDGeneration gen = new IDGeneration20Hash();
        String testISBN0 = "978-3-16-148410-0";

        //act
        String id0 = gen.generateID(testISBN0);

        //assert
        assertTrue(id0.matches("^[a-zA-Z0-9]{"+CHAR_LIMIT+"}$"));

    }

    @Test
    void testGenerateIDDifferentResultsFromDifferentBusinessID() {

        //arrange
        IDGeneration gen = new IDGeneration20Hash();
        String testISBN0 = "978-3-16-148410-0";
        String testISBN1 = "978-3-16-148410-1";

        //act
        String id0 = gen.generateID(testISBN0);
        String id1 = gen.generateID(testISBN1);

        //assert
        assertNotEquals(id0, id1);

    }

    @Test
    void testGenerateIDSameResultsFromSameBusinessID() {

        //arrange
        IDGeneration gen = new IDGeneration20Hash();
        String testISBN0 = "978-3-16-148410-0";
        String testISBN1 = "978-3-16-148410-0";

        //act
        String id0 = gen.generateID(testISBN0);
        String id1 = gen.generateID(testISBN1);

        //assert
        assertNotEquals(id0, id1);

    }

}
