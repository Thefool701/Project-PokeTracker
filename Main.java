import java.util.Scanner;
import java.io.*;

public class Main {
    public static void main(String[] args) {
        File infile = new File("routes.in");

        try {
            Scanner in = new Scanner(infile);

            while (in.hasNext()) {
                String line = in.nextLine();

                System.out.println(line);
            }

        } catch (Exception e) {
            System.out.println("Error reading the file...");
            e.printStackTrace();
        }

    }
}
