/*
* This is a program that randomly gives marks
* to assignments in an array.
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
    * The generateMarks function.
    *
    * @param arrayOfStudents the list for students
    * @param arrayOfAssignments the list of assignments
    * @return the combined array of the two lists
    */
    public static String[][] generateMarks(final String[] arrayOfStudents,
                                       final String[] arrayOfAssignments) {

        int numStudents = arrayOfStudents.length;
        int numAssignments = arrayOfAssignments.length;
        // add 1 to both values to take into account the student names and assignment names.
        String[][] markArray = new String[numStudents + 1][numAssignments + 1];
        for (int row = 1; row <= numStudents; row++) {
            markArray[row][0] = arrayOfStudents[row - 1];
        }
        for (int collumn = 1; collumn <= numAssignments; collumn++) {
            markArray[0][collumn] = arrayOfAssignments[collumn - 1];
        }
        Random random = new Random();
        for (int vertical = 1; vertical <= numStudents; vertical++) {
            for (int horizontal = 1; horizontal <= numStudents; horizontal++) {
                int mark = (int)Math.floor(random.nextGaussian()*10+75);
                String marks = String.valueOf(mark);
                markArray[vertical][horizontal] = marks;
            }
        }
        markArray[0][0] = "   ";
        printArray(markArray);
        return markArray;
    }

    private static void printArray(String[][] arrayList) {
        for (int roow = 0; roow < arrayList.length; roow++) {
            for (int column = 0; column < arrayList[0].length; column++) {
                System.out.print(arrayList[roow][column] + " ");
            }
            System.out.println(" ");
        }
    }

    /**
    * The starting main() function.
    *
    * @param args Name of file containing a string of numbers
    */
    public static void main(final String[] args) {
        final ArrayList<String> listOfStudents = new ArrayList<String>();
        final ArrayList<String> listOfAssignments = new ArrayList<String>();
        final Charset charset = Charset.forName("UTF-8");

        if (!Files.exists(Paths.get(args[0]))) {
            System.err.println("Exiting as file does not exist: " + args[0]);
            System.exit(0);
        }
        if (!Files.exists(Paths.get(args[1]))) {
            System.err.println("Exiting as file does not exist: " + args[1]);
            System.exit(0);
        }
        final Path studentFilePath = Paths.get(args[0]);
        final Path assignmentFilePath = Paths.get(args[1]);
        try (BufferedReader readerStudent = Files.newBufferedReader(
                                     studentFilePath, charset)) {
            String lineStudent = "hi";
            while ((lineStudent = readerStudent.readLine()) != null) {
                try {
                    listOfStudents.add(lineStudent);
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
            }
            } catch (IOException errorCode) {
                System.err.println(errorCode);
            }
        final int assignmentSize = listOfAssignments.size();
        final int nameSize = listOfStudents.size();
        final String[] arrayOfAssignments = listOfAssignments.toArray(new String[assignmentSize]);
        final String[] arrayOfStudents = listOfStudents.toArray(new String[nameSize]);
        final String[][] marks;
        generateMarks(arrayOfStudents, arrayOfAssignments);
    }
}
