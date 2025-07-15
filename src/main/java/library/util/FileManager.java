package library.util;

import java.io.*;
import java.util.*;

/**
 * FileManager provides static utility methods for reading and writing text files.
 * Used for file-based persistence of books, borrowers, and transactions.
 */
public class FileManager {
    /**
     * Reads all lines from a file into a list of strings.
     * @param filename The file to read
     * @return List of lines from the file
     * @throws IOException if file reading fails
     */
    public static List<String> readLines(String filename) throws IOException {
        List<String> lines = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
        }
        return lines;
    }

    /**
     * Writes a list of strings to a file, one per line.
     * @param filename The file to write to
     * @param lines The lines to write
     * @throws IOException if file writing fails
     */
    public static void writeLines(String filename, List<String> lines) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename))) {
            for (String line : lines) {
                bw.write(line);
                bw.newLine();
            }
        }
    }
}
