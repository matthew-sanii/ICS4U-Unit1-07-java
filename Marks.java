/*
* This is a program that calculates the mean and median
* after reading in a text file into an array.
*
* @author  Matthew Sanii
* @version 1.0
* @since   2021-26-11
*/

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
* This is the marks program.
*/
final class Marks {

    /**
    * Prevent instantiation
    * Throw an exception IllegalStateException.
    * if this ever is called
    *
    * @throws IllegalStateException
    *
    */
    private Marks() {
        throw new IllegalStateException("Cannot be instantiated");
    }

    /**
    * The mean() function.
    *
    * @param arrayOfIntegers the collection of integers
    * @param totalNumbers the size of the array of numbers
    * @return the mean of the integers
    */
    public static String[][] generateMarks(final Integer[] arrayOfStudents,
                                       final Integer[] arrayOfAssignments) {

        String[][] markArray = new String[3][];
        return markArray;
    }

    /**
    * The starting main() function.
    *
    * @param args Name of file containing a string of numbers
    */
    public static void main(final String[] args) {
        final ArrayList<Integer> listOfStudents = new ArrayList<Integer>();
        final ArrayList<String> listOfAssingments = new ArrayList<String>();
        final Charset charset = Charset.forName("UTF-8");

        if (!Files.exists(Paths.get(args[0]))) {
            System.err.println("Exiting as file does not exist: " + args[0]);
            System.exit(0);
        }
        final Path studentFilePath = Paths.get(args[0]);
        final Path assignmentFilePath = Paths.get(args[0]);
        try (BufferedReader readerStudent = Files.newBufferedReader(
                                     studentFilePath, charset)) {
            String lineStudent = "hi";
            while ((lineStudent = readerStudent.readLine()) != null) {
                try {
                    listOfStudents.add(lineStudent);
                    System.out.println(lineStudent);
                } catch (ArrayIndexOutOfBoundsException errorCode) {
                    lineStudent = null;
                }
            }
        } catch (IOException errorCode) {
            System.err.println(errorCode);
        }
        try (BufferedReader readerAssignment = Files.newBufferedReader(
                                     assignmentFilePath, charset)) {
            String lineAssignment = "hello";
            while ((lineAssignment = readerAssignment.readLine()) != null) {
                listOfAssignments.add(lineAssignment);
                System.out.println(lineAssignment);
            }

        Random random = new Random();

        for (int loopCounter = 0; loopCounter < 5; loopCounter++) {
            int mark = (int)Math.floor(random.nextGaussian()*10+75);
            System.out.println(mark);
        }
    }
}
}
