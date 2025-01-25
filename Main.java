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
            System.out.println("4. Search Route");
            System.out.println("5. Print Table");
            System.out.println("6. Exit");
            System.out.printf("%nEnter Choice: ");
            choice = in.nextInt();

            switch (choice) {
                case 1:
                    markRoute(outfile, routes);
                    break;
                case 2:
                    duplicatePokemonCheck(outfile, row);
                    break;
                case 3:
                    routeStatusCheck(outfile, row);
                    break;
                case 4:
                    searchRoute(outfile);
                    break;
                case 5:
                    printTable(outfile, row);
                    break;
                case 6:
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
     *
     */
    public static void markRoute(File outfile, LinkedList<String> routes) {
        Scanner in = new Scanner(System.in);
        String pokemon = "";
        String pokemonTyping = "";
        String route = "";
        String routeInfo = "";
        String routeStatus = "true";
        System.out.printf("%nEnter Route: ");
        route = in.nextLine();
        System.out.printf("%nEnter Pokemon: ");
        pokemon = in.nextLine();
        System.out.printf("%nEnter Pokemon Typing(Primary/Secondary): ");
        pokemonTyping = in.nextLine();

        try {
            FileInputStream outStream = new FileInputStream(outfile);
            BufferedReader br = new BufferedReader(new InputStreamReader(outStream));
            String line = "";
            StringBuilder fileContent = new StringBuilder();

            while ((line = br.readLine()) != null) {
                String[] tokens = new String[4];
                String spaces = "       ";
                tokens[0] = line;
                if (tokens.length > 0) {
                    if (tokens[0].equals(route)) {
                        tokens[1] = pokemon;
                        tokens[2] = pokemonTyping;
                        tokens[3] = routeStatus;

                        String newRouteInfo = tokens[0] + spaces + tokens[1] + spaces + tokens[2] + spaces + tokens[3];
                        fileContent.append(newRouteInfo);
                        fileContent.append("\n");
                    } else {
                        fileContent.append(line);
                        fileContent.append("\n");
                    }
                }
            }
            FileWriter outStreamWriter = new FileWriter(outfile);
            BufferedWriter out = new BufferedWriter(outStreamWriter);
            out.write(fileContent.toString());
            out.close();
        } catch (Exception e) {
            System.out.println("error in editing route");
            e.printStackTrace();
        }

        in.close();
    }

    /**
     * It Searches the specific position where the route is and returns
     * an int indicating where the route is
     *
     * @param takes an outfile as input
     *
     * 
     */

    public static int searchRoute(File outfile) {
        int filePos = 0;
        try {
            @SuppressWarnings("resource")
            Scanner on = new Scanner(outfile);
            Scanner in = new Scanner(System.in);

            System.out.printf("%nEnter Route to Search: ");
            String findRoute = in.nextLine();
            while (on.hasNext()) {
                // Check how many spoaces there is in the line
                // If more than 1, then split into tokens and compare
                // token[0] with findRoute
                // else, then just compare line with findRoute
                String temp = on.nextLine();
                if (temp.equals(findRoute) == true) {
                    System.out.println("found...");
                    return filePos;
                }
                ++filePos;
            }

        } catch (Exception e) {
            System.out.println("error in accessing outfile...");
            e.printStackTrace();
        }

        return 2;
    }

    /**
     * Edits a route from the specific line in the file
     * 
     * @param outfile
     * @param route
     * @param pokemon
     * @param pokemonTyping
     * @param routeStatus
     *
     */

    public static void editRoute(File outfile, String route, String pokemon, String pokemonTyping, String routeStatus) {
        try {
            FileInputStream outStream = new FileInputStream(outfile);
            BufferedReader br = new BufferedReader(new InputStreamReader(outStream));
            String line = "";
            StringBuilder fileContent = new StringBuilder();

            while ((line = br.readLine()) != null) {
                System.out.println(line);
                String[] tokens = line.split(" ");
                String spaces = "         ";
                if (tokens.length > 0) {
                    if (tokens[0].equals(route)) {
                        tokens[1] = pokemon;
                        tokens[2] = pokemonTyping;
                        tokens[3] = routeStatus;

                        String newRouteInfo = tokens[0] + spaces + tokens[1] + spaces + tokens[2] + spaces + tokens[3];
                        fileContent.append(newRouteInfo);
                        fileContent.append("\n");
                    } else {
                        fileContent.append(line);
                        fileContent.append("\n");
                    }
                }
            }
            FileWriter outStreamWriter = new FileWriter(outfile);
            BufferedWriter out = new BufferedWriter(outStreamWriter);
            out.write(fileContent.toString());
            out.close();
        } catch (Exception e) {
            System.out.println("error in editing route");
            e.printStackTrace();
        }
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
