package org.examportal.Helper;

import java.util.Random;

public class CommonHelper {
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int DEFAULT_CODE_LENGTH = 8;

    // ** Generate Code for given length
    public static String generateRandomCode(int length) {
        Random random = new Random();
        StringBuilder code = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            code.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
        }

        return code.toString();
    }

    // ** Generate code for fixed length of 8
    public static String generateRandomCode() {
        return generateRandomCode(DEFAULT_CODE_LENGTH);
    }
}
