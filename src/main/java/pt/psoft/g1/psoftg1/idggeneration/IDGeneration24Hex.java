package pt.psoft.g1.psoftg1.idggeneration;

import java.util.UUID;

public class IDGeneration24Hex implements IDGeneration {

    private static final int CHAR_LIMIT = 24;

    @Override
    public String generateID(String businessID) {

        return UUID.fromString(businessID)
                .toString()
                .replace("-","")
                .substring(0, CHAR_LIMIT+1);
    }
}
