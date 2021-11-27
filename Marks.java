/*
* This is a program that randomly gives marks
* to assignments in an array.
*
* @author  Matthew Sanii
* @version 1.0
* @since   2021-26-11
*/

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
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
    * Creates the marks for the generate program to use.
    *
    * @return the mark generated
    */
    public static int markCreate() {

        final Random random = new Random();
        final int average = 75;
        final int deviate = 10;
        final int mark = (int) Math.floor(random.nextGaussian() * deviate + average);
        return mark;
    }

    /**
    * The generateMarks function.
    *
    * @param arrayOfStudents the list for students
    * @param arrayOfAssignments the list of assignments
    * @param name the name of the file you want to create
    * @return the combined array of the two lists
    * @throws IOException when error occurs
    *
    */
    public static String[][] generateMarks(final String[] arrayOfStudents,
                                       final String[] arrayOfAssignments,
                                       final String name) throws IOException {

        final int numStudents = arrayOfStudents.length;
        final int numAssignments = arrayOfAssignments.length;
        final String[][] markArray = new String[numStudents + 1][numAssignments + 1];
        markArray[0][0] = "  ";
        for (int row = 1; row <= numStudents; row++) {
            markArray[row][0] = arrayOfStudents[row - 1];
            markArray[0][row] = arrayOfAssignments[row - 1];
        }
        for (int vertical = 1; vertical <= numStudents; vertical++) {
            for (int horizontal = 1; horizontal <= numStudents; horizontal++) {
                final int grade = markCreate();
                markArray[vertical][horizontal] = String.valueOf(grade);
            }
        }
        final String fileName = name;
        try {
            final File myObj = new File(fileName);
            if (myObj.createNewFile()) {
                System.out.println("File created: " + myObj.getName());
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException errorCode) {
            System.out.println("Error creating file.");
        }
        final FileWriter write = new FileWriter(fileName);
        final BufferedWriter buffWrite = new BufferedWriter(write);

        for (int y = 0; y <= numStudents; y++) {
            String fileline = "";
            for (int x = 0; x <= numAssignments; x++) {
                if (x == 0 && y == 0) {
                    fileline += "  , ";
                } else {
                    if (x == numAssignments) {
                        fileline += String.valueOf(markArray[y][x]);
                    } else {
                        fileline += String.valueOf(markArray[y][x]) + ", ";
                    }
                }
            }
            buffWrite.write(fileline);
            buffWrite.newLine();
        }
        buffWrite.close();
        write.close();

        return markArray;
    }

    /**
    * The fileCheck function.
    *
    * @param value1 int value of first file size
    * @param value2 int value of second file size
    */

    public static void fileCheck(long value1, long value2) {
        if (value1 == 0 || value2 == 0) {
            System.out.println("One of the input files is empty. Both files must contain data.");
            System.out.println("Please check files.");
            System.exit(0);
        }
    }

    /**
    * The starting main() function.
    *
    * @param args Name of file containing a string of numbers
    * @throws IOException when error occurs
    */

    public static void main(final String[] args) throws IOException {
        final ArrayList<String> listOfStudents = new ArrayList<String>();
        final ArrayList<String> listOfAssignments = new ArrayList<String>();
        final Charset charset = Charset.forName("UTF-8");
        final String namedFile = "marks.csv";
        if (!Files.exists(Paths.get(args[0])) || !Files.exists(Paths.get(args[1]))) {
            System.err.println("Exiting as the necessary input files do not exist: "
                + args[0] + " or " + args[1]);
            System.exit(0);
        }
        final Path studentFilePath = Paths.get(args[0]);
        final Path assignmentFilePath = Paths.get(args[1]);
        final File file1 = new File(args[0]);
        final File file2 = new File(args[1]);
        final long fileSize1 = file1.length();
        final long fileSize2 = file2.length();
        fileCheck(fileSize1, fileSize2);
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
        generateMarks(arrayOfStudents, arrayOfAssignments, namedFile);
    }
}
