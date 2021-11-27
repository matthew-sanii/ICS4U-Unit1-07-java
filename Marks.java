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
    * The generateMarks function.
    *
    * @param arrayOfStudents the list for students
    * @param arrayOfAssignments the list of assignments
    * @return the combined array of the two lists
    * @throws IoException when error occurs
    */
    public static String[][] generateMarks(final String[] arrayOfStudents,
                                       final String[] arrayOfAssignments) throws IOException {

        final int average = 75;
        final int deviate = 10;
        final int numStudents = arrayOfStudents.length;
        final int numAssignments = arrayOfAssignments.length;
        // add 1 to both values to take into account the student names and assignment names.
        final String[][] markArray = new String[numStudents + 1][numAssignments + 1];
        // put a blank value in the first XX of the array
        markArray[0][0] = "  ";
        for (int row = 1; row <= numStudents; row++) {
            markArray[row][0] = arrayOfStudents[row - 1];
        }
        for (int collumn = 1; collumn <= numAssignments; collumn++) {
            markArray[0][collumn] = arrayOfAssignments[collumn - 1];
        }
        final Random random = new Random();
        for (int vertical = 1; vertical <= numStudents; vertical++) {
            for (int horizontal = 1; horizontal <= numStudents; horizontal++) {
                final int mark = (int) Math.floor(random.nextGaussian() * deviate + average);
                final String marks = String.valueOf(mark);
                markArray[vertical][horizontal] = marks;
            }
        }

        File file = new File(fileName);
        FileWriter fw = new FileWriter(file);
        BufferedWriter bw = new BufferedWriter(fw);

        for (int y = 0; y <= numStudents; y++) {
           String fileline = "";
           for (int x = 0; x <= numAssignments; x++) {
               if (x == 0 && y == 0) {
                  fileline += "  , ";
               }
               else {
                  if (x == numAssignments) {
                      fileline += String.valueOf(markArray[y][x]);
                  }
                  else {
                      fileline += String.valueOf(markArray[y][x]) + ", ";
                  }
               }
           }
           bw.write(fileline);
           bw.newLine();
        }
        //Close the csv file.
        bw.close();
        fw.close();

        return markArray;
    }
    /**
    * The printArray function.
    *
    * @param arrayList 2d array taken from other function.
    *
    */

    public static void printArray(String[][] arrayList) {
        final String blank = " ";
        for (int roow = 0; roow < arrayList.length; roow++) {
            for (int column = 0; column < arrayList[0].length; column++) {
                System.out.print(arrayList[roow][column] + blank);
            }
            System.out.println(blank);
        }
    }

    /**
    * The starting main() function.
    *
    * @param args Name of file containing a string of numbers
    */
    public static void main(final String[] args) throws IOException {
        final ArrayList<String> listOfStudents = new ArrayList<String>();
        final ArrayList<String> listOfAssignments = new ArrayList<String>();
        final Charset charset = Charset.forName("UTF-8");
        final String fileName = "marks.csv";
        try {
            final File myObj = new File(fileName);
            if (myObj.createNewFile()) {
                System.out.println("File created: " + myObj.getName());
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
                System.out.println("An error occurred trying to create the marks.csv file.");
                e.printStackTrace();
        }

        if (!Files.exists(Paths.get(args[0])) || !Files.exists(Paths.get(args[1]))) {
            System.err.println("Exiting as the necessary input files do not exist: " + args[0] + " or " + args[1]);
            System.exit(0);
        }
        final Path studentFilePath = Paths.get(args[0]);
        final Path assignmentFilePath = Paths.get(args[1]);

        File file1 = new File(args[0]);
        File file2 = new File(args[1]);
        if (file1.length() == 0 || file2.length() == 0) {
            System.out.println("One of the input files is empty. Both files must contain data.");
            System.out.println("Please check files.");
            System.exit(0);
        }

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
