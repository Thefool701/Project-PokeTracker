import java.util.Scanner;
import java.util.LinkedList;
import java.io.*;

public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        File infile = new File("routes.in");
        LinkedList<String> routes = new LinkedList<String>();
        int choice = 0;
        readInputFile(infile, routes);
        File outfile = new File("table.in");
        LinkedList<String> row = new LinkedList<String>();
        readOutfile(outfile, row);

        while (choice != 1) {
            System.out.println("<<< Nuzlocke Routes Manager >>>");
            System.out.println("1. Mark Route");
            System.out.println("2. Duplicate Pokemon Check");
            System.out.println("3. Route Status");
            System.out.println("4. Print Table");
            System.out.println("5. Exit");
            System.out.println("Enter Choice: ");
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
                    printTable(outfile, row);
                    break;
                case 5:
                    System.exit(1);
                    break;
            }
        }
    }

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

    // TODO: Stil not sure if this method is necessary
    public static void readOutfile(File outfile, LinkedList<String> row) {
        try {
            Scanner in = new Scanner(outfile);
            while (in.hasNext()) {
                String temp = in.nextLine();
                String[] input = temp.split(temp);
            }

        } catch (Exception e) {
            System.out.println("error reading outfile...");
            e.printStackTrace();
        }
    }

    public static void markRoute(File outfile, LinkedList<String> routes) {
        Scanner in = new Scanner(System.in);
        String pokemon = "";
        String pokemonTyping = "";
        String route = "";
        boolean routeStatus = true;

        System.out.println("Enter Route: ");
        route = in.nextLine();
        System.out.println("Enter POkemon: ");
        pokemon = in.nextLine();
        System.out.println("Pokemon Typing(Primary/Secondary): ");
        pokemonTyping = in.nextLine();

    }

    public static void duplicatePokemonCheck(File outfile, LinkedList<String> row) {

    }

    public static void routeStatusCheck(File outfile, LinkedList<String> row) {

    }

    public static void printTable(File outfile, LinkedList<String> row) {

    }

}
