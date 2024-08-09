import java.util.*;
import java.io.*;

class FReadCharacters {
    public static void main(String args[]) {
        FileInputStream fin;
        Scanner s = new Scanner(System.in);
        String fname;

        try {
            System.out.println("Enter the file name:");
            fname = s.nextLine();
            fin = new FileInputStream(fname);

            int ch;
            int charCount = 0;
            while ((ch = fin.read()) != -1) {
                System.out.print((char) ch);
                charCount++;
            }

            System.out.println("\nNumber of characters in the file: " + charCount);

            fin.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e);
        } catch (IOException e) {
            System.out.println("Error reading the file: " + e);
        }
    }
}
