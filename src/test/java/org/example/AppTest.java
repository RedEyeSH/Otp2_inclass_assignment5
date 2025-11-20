package org.example;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AppTest {

    @Test
    void testDisplayNumbers() {
        int count = 3;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        App.displayNumbers(count);

        String expectedOutput = "i = 1" + System.lineSeparator() + "i = 2" + System.lineSeparator() + "i = 3" + System.lineSeparator();
        assertEquals(expectedOutput, outputStream.toString());
    }

    @Test
    void testMain() {
        String input = "3\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        App.main(new String[]{});

        String expectedOutput = "How many numbers to display in the loop? " + "i = 1" + System.lineSeparator() + "i = 2" + System.lineSeparator() + "i = 3" + System.lineSeparator();
        assertEquals(expectedOutput, outputStream.toString());
    }

    @Test
    void testConstructor() {
        new App();
    }
}
