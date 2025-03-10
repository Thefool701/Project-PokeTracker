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
                    clearScreen();
                    markRoute(outfile, routes);
                    break;
                case 2:
                    clearScreen();
                    duplicatePokemonCheck(outfile);
                    break;
                case 3:
                    clearScreen();
                    routeStatusCheck(outfile);
                    break;
                case 4:
                    clearScreen();
                    searchRoute(outfile);
                    break;
                case 5:
                    clearScreen();
                    printTable(outfile, row);
                    break;
                case 6:
                    clearScreen();
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

    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
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
            writer.write("Route         Pokemon         Typing         Status");
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
        String routeStatus = "Marked";
        System.out.printf("%nEnter Route: ");
        route = in.nextLine();
        // TODO: Check route if it exists before continuing
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

    public static void searchRoute(File outfile) {
        try {
            FileInputStream outStream = new FileInputStream(outfile);
            BufferedReader br = new BufferedReader(new InputStreamReader(outStream));
            String line = "";
            boolean routeStatus = false;
            Scanner in = new Scanner(System.in);
            String route = "";
            System.out.printf("%nEnter Route to Search: ");
            route = in.nextLine();
            // TODO: Check route if it exists
            while ((line = br.readLine()) != null) {
                line.replaceAll("\\W", "-");
                String[] tokens = line.split("-");
                if (tokens.length > 0) {
                    for (int i = 0; i < tokens.length; i++) {
                        if (tokens[i].contains(route)) {
                            System.out.println(line);
                            routeStatus = true;
                            break;
                        }
                    }
                }
                if (routeStatus) {
                    break;
                }
            }
        } catch (Exception e) {
            System.out.println("searching route has failed...");
            e.printStackTrace();
        }
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
            boolean routeStatus = false;
            @SuppressWarnings("resource")
            Scanner in = new Scanner(System.in);
            String route = "";
            do {
                System.out.printf("%nEnter Route to Check: ");
                route = in.nextLine();
            } while (routeExistenceCheck(outfile, route) == false);

            while ((line = br.readLine()) != null) {
                line.replaceAll("\\W", "-");
                String[] tokens = line.split("-");
                if (tokens.length > 0) {
                    for (int i = 0; i < tokens.length; i++) {
                        if (tokens[i].contains(route)) {
                            if (tokens[i].contains("Marked")) {
                                routeStatus = true;
                                break;
                            }
                        }
                    }
                }
                if (routeStatus) {
                    break;
                }
            }
            if (routeStatus) {
                System.out.println("Route has been Marked!");
            } else {
                System.out.println("Route is Unmarked!");
            }

        } catch (Exception e) {
            System.out.println("route status checker failed...");
            e.printStackTrace();
        }
    }

    /**
     * Scans the table if a certain Route exists.
     *
     * @param Outfile File that contains the regions information
     * @param route   Name of the route to be checked
     *
     * @return routeStatus Returns either true or false if the route exists
     */

    public static boolean routeExistenceCheck(File outfile, String route) {
        try {
            FileInputStream outStream = new FileInputStream(outfile);
            BufferedReader br = new BufferedReader(new InputStreamReader(outStream));
            String line = "";
            while ((line = br.readLine()) != null) {
                String[] tokens = new String[4];
                line = line.replace("\\W", "-");
                tokens = line.split("-");
                if (tokens.length > 0) {
                    if (tokens[0].contains(route)) {
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("route status checker failed...");
            e.printStackTrace();
        }
        return false;
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
