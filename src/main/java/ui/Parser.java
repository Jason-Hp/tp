package ui;

import java.io.IOException;

public class Parser {

    public Parser() {
    }

    public String parse(String input) throws IOException {
        if (input == null || input.isEmpty()) {
            throw new IOException("Input is Empty");
        }
        return input.split(" ")[0];
    }
}
