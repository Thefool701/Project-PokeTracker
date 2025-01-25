import java.util.Scanner;
import java.util.LinkedList;
import java.io.*;
import java.io.FileWriter;

public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        LinkedList<String> row = new LinkedList<String>();
        File infile = new File("routes.in");
        LinkedList<String> routes = new LinkedList<String>();
        int choice = 0;
        createOutfile();
        File outfile = new File("kalos.in");
        if (readOutfile(outfile) == false) {
            readInputFile(infile, routes);
            writeOutfile(outfile, routes);
        }
        while (choice != 1) {
            System.out.println("<<< Nuzlocke Routes Manager >>>");
            System.out.println("1. Mark Route");
            System.out.println("2. Duplicate Pokemon Check");
            System.out.println("3. Route Status");
            System.out.println("4. Print Table");
            System.out.println("5. Exit");
            System.out.printf("%nEnter Choice: ");
            choice = in.nextInt();

            switch (choice) {
                case 1:
                    markRoute(infile, routes);
                    break;
                case 2:
                    duplicatePokemonCheck(outfile, row);
                    break;
                case 3:
                    routeStatusCheck(outfile, row);
                    break;
                case 4:
                    printTable(outfile, row);
                    break;
                case 5:
                    System.exit(1);
                    break;
                default:
                    System.out.println("Invalid Choice. Try Again.");
            }
        }
    }

    public static void createOutfile() {
        try {
            File outfile = new File("kalos.in");
            if (outfile.createNewFile()) {
                System.out.println("File Created: " + outfile.getName());
            } else {
                System.out.println("File already exists.");
            }

        } catch (Exception e) {
            System.out.println("Error occured while creating file...");
            e.printStackTrace();
        }

    }

    /**
     * Reads from an Input File
     *
     * @param takes infile as file input
     * @param takes a linedList named routes
     */

    public static void readInputFile(File infile, LinkedList<String> routes) {
        try {
            @SuppressWarnings("resource")
            Scanner in = new Scanner(infile);
            while (in.hasNext()) {
                String temp = in.nextLine();
                routes.add(temp);
            }
        } catch (Exception e) {
            System.out.println("Error reading the file...");
            e.printStackTrace();
        }
    }

    /**
     * Reads an outfile and determines whether file is empty or not
     *
     * @param takes an input file as parameter
     * @return returns either true or false if file is empty
     *
     */

    public static boolean readOutfile(File infile) {
        try {
            Scanner in = new Scanner(infile);
            if (in.hasNext()) {
                return true;
            }
        } catch (Exception e) {
            System.out.println("error reading outfile...");
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Creates an outfile that contains a table of route information
     *
     * @param takes a output file as input
     * @param takes a Linked List of routes as input
     */

    public static void writeOutfile(File outfile, LinkedList<String> routes) {
        try {
            FileWriter fr = new FileWriter(outfile);
            BufferedWriter writer = new BufferedWriter(fr);
            writer.write("Route         Pokemon         Typing          Status");
            for (int i = 0; i < routes.size(); i++) {
                writer.newLine();
                writer.write(routes.get(i));
            }
            writer.close();
        } catch (Exception e) {
            System.out.println("Error in writing to file.");
            e.printStackTrace();
        }
    }

    /**
     * Marks a route and inputs the necessary data for the route
     *
     * @param takes an output file as input
     * @param takes a linked list which contains the routes
     */
    public static void markRoute(File outfile, LinkedList<String> routes) {
        Scanner in = new Scanner(System.in);
        String pokemon = "";
        String pokemonTyping = "";
        String route = "";
        boolean routeStatus = true;

        System.out.printf("%nEnter Route: ");
        route = in.nextLine();
        int filePos = searchRoute(outfile, route);

        if (filePos == 1) {
            System.out.println("Route has already been marked.");
            markRoute(outfile, routes);
        } else if (filePos == 2) {
            System.out.println("There is no such route.");
            markRoute(outfile, routes);
        }

        System.out.println("Route Line:" + filePos);

        in.close();
    }

    /**
     * It Searches the specific position where the route is and returns
     * an int indicating where the route is
     *
     * @param takes an outfile as input
     * @param takes the String route
     *
     * @return an int which indicates where specifically the route is
     */

    public static int searchRoute(File outfile, String route) {
        int filePos = 0;
        try {
            @SuppressWarnings("resource")
            Scanner on = new Scanner(outfile);
            int count = 0;
            while (on.hasNext()) {
                String temp = on.nextLine();
                int spaceCount = temp.replaceAll("[^ ]", "").length();

                if (temp.equals(route) == true) {
                    System.out.println("found...");
                    return filePos;
                }

                // TODO: Also check if ther oute has been types correctly
                ++filePos;
            }

        } catch (Exception e) {
            System.out.println("error in accessing outfile...");
            e.printStackTrace();
        }

        return 2;
    }

    public static void duplicatePokemonCheck(File outfile, LinkedList<String> row) {

    }

    public static void routeStatusCheck(File outfile, LinkedList<String> row) {

    }

    /**
     * Simply prints the table of Kalos Routes and their corresponding info
     *
     * @param takes an outfile as input
     * @param takes a Linked List that contains the row
     */
    public static void printTable(File outfile, LinkedList<String> row) {
        try {
            // TODO: This needs improvement and changes.
            // Make it print a table with the corresponding fields
            Scanner in = new Scanner(outfile);
            while (in.hasNext()) {
                String temp = in.nextLine();
                System.out.println(temp);
            }

        } catch (Exception e) {
            System.out.println("Error printing table.");
            e.printStackTrace();
        }
    }

}
