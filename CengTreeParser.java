import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Scanner;

public class CengTreeParser
{
    public static ArrayList<CengBook> parseBooksFromFile(String filename)
    {
        ArrayList<CengBook> bookList = new ArrayList<CengBook>();

        // You need to parse the input file in order to use GUI tables.
        // TODO: Parse the input file, and convert them into CengBooks

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))){
            String line;
            while ((line = reader.readLine()) != null){
                String [] parts = line.split("\\|");
                CengBook book = new CengBook(Integer.parseInt(parts[0]), parts[1], parts[2], parts[3]);
                bookList.add(book);
                //CengBookRunner.addBook(book); // is this necessary ?
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }

        return bookList;
    }

    private static void parseAndProcessCommand(String command){
        String [] parts = command.split("\\|");

        if(parts.length == 5 && parts[0].equalsIgnoreCase("add")){
            //System.out.println("Adding...");
            CengBook book = new CengBook(Integer.parseInt(parts[1]), parts[2], parts[3], parts[4]);
            CengBookRunner.addBook(book);
        }
        else if(parts.length == 2 && parts[0].equalsIgnoreCase("search")){
            //System.out.println("Searching...");
            CengBookRunner.searchBook(Integer.parseInt(parts[1]));
        }
        else{
            System.out.println("Invalid command!");
        }
    }

    public static void startParsingCommandLine() throws IOException
    {
        // TODO: Start listening and parsing command line -System.in-.
        // There are 4 commands:
        // 1) quit : End the app, gracefully. Print nothing, call nothing, just break off your command line loop.
        // 2) add : Parse and create the book, and call CengBookRunner.addBook(newlyCreatedBook).
        // 3) search : Parse the bookID, and call CengBookRunner.searchBook(bookID).
        // 4) print : Print the whole tree, call CengBookRunner.printTree().

        // quit format : quit
        // add format : add|<bookID>|<bookTitle>|<author>|<genre>
        // search format : search|<bookID>
        // print format : print

        // Commands (quit, add, search, print) are case-insensitive.

        Scanner scanner = new Scanner(System.in);
        
        //ArrayList<CengBook> bookList = parseBooksFromFile(CengBookRunner.fileName);

        while (true){
            String command = scanner.nextLine();
            if(command.equalsIgnoreCase("quit")){
                //System.out.println("Exiting...");
                break;
            }
            else if(command.equalsIgnoreCase("print")){
                //System.out.println("Printing Tree...");
                CengBookRunner.printTree();
                //System.out.println("Printing Tree Finished!");
            }
            else{
                parseAndProcessCommand(command);
            }
        }

        scanner.close();
    }
}
