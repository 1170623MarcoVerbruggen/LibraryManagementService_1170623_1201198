package pt.psoft.g1.psoftg1.idggeneration;

import org.apache.commons.lang3.RandomStringUtils;

public class IDGeneration20Hash implements IDGeneration {

    private static final int CHAR_LIMIT = 20;

    @Override
    public String generateID(String businessID) {

        String id = Integer.toString(businessID.hashCode())
                .replace("-","n"); // hashCode() result can be negative, remove non-alphanumeric char

        return id.concat(RandomStringUtils.randomAlphanumeric(CHAR_LIMIT-id.length()));

    }
}
