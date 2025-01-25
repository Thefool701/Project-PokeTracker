import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Sample {
    public static void main(String[] args) {
        String filePath = "kalos.in"; // Update the path to your file
        int lineNumber = 2; // Specify the line number to read (starting from 1)
        String specificLine = readLineFromFile(filePath, lineNumber);

        if (specificLine != null) {
            System.out.println("Line " + lineNumber + ": " + specificLine);
        } else {
            System.out.println("Line number " + lineNumber + " is not found.");
        }
    }

    public static String readLineFromFile(String filePath, int lineNumber) {
        String line = null;
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            for (int i = 1; i <= lineNumber; i++) {
                line = br.readLine();
                if (line == null)
                    return null; // Return null if the line doesn't exist
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return line;
    }
}
