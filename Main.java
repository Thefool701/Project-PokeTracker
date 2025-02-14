import java.util.Scanner;
import java.util.LinkedList;
import java.io.*;
import java.io.FileWriter;

// TODO: Add option to delete contents of the file and add 2nd layer of security
// when deleting the contents of file. 
//
// TODO: Add the clear screen method 
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
        // TODO: Add option to edit route
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
                    duplicatePokemonCheck(outfile);
                    break;
                case 3:
                    routeStatusCheck(outfile);
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
        in.close();
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
     * @param infile The file that contains the names of the routes region
     * @param routes A list of all the routes of the region
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
     * @param infile The file that contains the regions routes
     * @return If the file exists
     *
     */

    public static boolean readOutfile(File infile) {
        try {
            @SuppressWarnings("resource")
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
     * @param outfile The file that contains the database of the region
     * @param routes  List of the routes of the region
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
     * @param outfile The file that contains the regions routes
     * @param routes  List of all the routes in the region
     *
     */
    public static void markRoute(File outfile, LinkedList<String> routes) {
        Scanner in = new Scanner(System.in);
        String pokemon = "";
        String pokemonTyping = "";
        String route = "";
        String routeStatus = "Unavailable";
        System.out.printf("%nEnter Route: ");
        route = in.nextLine();
        System.out.printf("%nEnter Pokemon: ");
        pokemon = in.nextLine();
        System.out.printf("%nEnter Pokemon Typing(Primary/Secondary): ");
        pokemonTyping = in.nextLine();

        try {
            // FIX: Fix alignment of the routeInfo in file
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
            br.close();
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
     * @param outfile File that contains the region database
     *
     * 
     */

    public static int searchRoute(File outfile) {
        int filePos = 0;
        try {
            @SuppressWarnings("resource")
            Scanner on = new Scanner(outfile);
            @SuppressWarnings("resource")
            Scanner in = new Scanner(System.in);

            System.out.printf("%nEnter Route to Search: ");
            String findRoute = in.nextLine();
            while (on.hasNext()) {
                // TODO: Also do this
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
     * @param outfile       File that contains the region database
     * @param route         Route of the region
     * @param pokemon       Pokemon of the Region
     * @param pokemonTyping The Pokemons Typing
     * @param routeStatus   The availability of the route
     *
     */
    // TODO: Remove params and input routeInfo in the method itself
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
            br.close();
        } catch (Exception e) {
            System.out.println("error in editing route");
            e.printStackTrace();
        }
    }

    /**
     * Checks to see if the pokemon has already been caught or not
     *
     * @param outfile File that contains the region database
     *
     */

    public static void duplicatePokemonCheck(File outfile) {
        try {
            FileInputStream outStream = new FileInputStream(outfile);
            BufferedReader br = new BufferedReader(new InputStreamReader(outStream));
            String line = "";
            Scanner in = new Scanner(System.in);
            String pokemon = "";

            System.out.printf("%nEnter Pokemon to Check: ");
            pokemon = in.nextLine();
            boolean routeStatus = false;
            while ((line = br.readLine()) != null) {
                String[] tokens = line.split(" ");
                if (tokens.length > 0) {
                    for (int j = 0; j < tokens.length; j++) {
                        if (tokens[j].contains(pokemon)) {
                            routeStatus = true;
                            break;
                        }
                    }
                }

            }
            if (routeStatus == false) {
                System.out.println("No such Pokemon.");
            } else {
                System.out.println("Pokemon already exists.");
            }
        } catch (Exception e) {
            System.out.println("error in checking route");
            e.printStackTrace();
        }
    }

    /**
     * Checks the status of the route and shows if it is available or not.
     *
     * @param outfile The file that contains the info of the route.
     */

    public static void routeStatusCheck(File outfile) {
        try {
            FileInputStream outStream = new FileInputStream(outfile);
            BufferedReader br = new BufferedReader(new InputStreamReader(outStream));
            String line = "";
            Scanner in = new Scanner(System.in);

            String route = "";

            System.out.printf("%nEnter Route to Check: ");
            route = in.nextLine();

            while ((line = br.readLine()) != null) {
                String[] tokens = line.split(" ");
                System.out.println(tokens[0]);
                if (tokens.length > 0) {
                    // FIX: Also only reads the first line of the file
                    if (tokens[0].equals(route)) {
                        if (tokens[3].equals("Available")) {
                            System.out.println("Route is Unavailable.");
                        } else if (tokens[3].equals("")) {
                            System.out.println("Route is Available.");
                        } else {
                            System.out.println("Route is Available.");
                        }
                        break;
                    } else {
                        System.out.println("No such Route.");
                        break;
                    }
                }
                in.close();
                br.close();
            }
        } catch (Exception e) {
            System.out.println("route status checker failed...");
            e.printStackTrace();
        }
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
            in.close();

        } catch (Exception e) {
            System.out.println("Error printing table.");
            e.printStackTrace();
        }
    }

}
