import java.io.*;

import utility.*;
import utility.parsing.FileManager;

/**
 * The main class of the program.
 *
 * @author Anvar Gazizov.
 */
public class Main {
    public static void main(String[] args) {
        try {
            if (args.length == 0 || args[0].matches("(/dev/)\\w*")) {
                throw new IllegalArgumentException();
            }
            CollectionManager collectionManager = new CollectionManager();
            FileManager fileManager = new FileManager(new File(args[0]));
            fileManager.manageXML(collectionManager);
            CommandManager commandManager = new CommandManager(collectionManager);
            commandManager.readInput();
        } catch (IllegalArgumentException e) {
            System.out.println("There is no file pathname in the command argument or entered pathname is incorrect.");
        }
    }
}
