import java.io.*;

public class Sample {
    public static void main(String... args) {
        File dir = new File("Regions/");
        showFiles(dir.listFiles());
    }

    public static void showFiles(File[] files) {
        for (File file : files) {
            if (file.isDirectory()) {
                System.out.println("Directory: " + file.getName());
                showFiles(file.listFiles());
            } else {
                System.out.println("File: " + file.getName());
            }

        }
    }
}
