
import java.util.ArrayList;
import java.util.List;

/**
 * This class is the main class of the "World of Zuul" application. "World of
 * Zuul" is a very simple, text based adventure game. Users can walk around some
 * scenery. That's all. It should really be extended to make it more
 * interesting!
 *
 * To play this game, create an instance of this class and call the "play"
 * method.
 *
 * This main class creates and initialises all the others: it creates all rooms,
 * creates the parser and starts the game. It also evaluates and executes the
 * commands that the parser returns.
 *
 * @author Michael Kölling and David J. Barnes
 * @version 2011.07.31
 */
public class Game {

    private Parser parser;
    private Player player1, currentplayer;
    public final String seprationLine = "--------------------------------------------------------------------";

    /**
     * Create the game and initialise its internal map.
     */
    public Game() {
        player1 = new Player();
        currentplayer = player1;
        createRooms();
        parser = new Parser();
    }

    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms() {
        Room entry, freskoChamber, aetherOrgan, downTheHole, relicOfTheFallen, poolRoom, storeRoom, eggRoom, statueRoom;

        // Initialise
        RoomDesciption roomDesciptions = new RoomDesciption();
        ItemDesciption itemDesriptions = new ItemDesciption();

        // creates the room 
        // adding new rooms should be acompanied by a "Room Desciption Method" in the RoomDesciption Class 
        entry = new Room(roomDesciptions.roomDesiptionEntry());
        freskoChamber = new Room(roomDesciptions.roomDesiptionFresko());
        downTheHole = new Room(roomDesciptions.roomDesiptionHole());
        aetherOrgan = new Room(roomDesciptions.roomDesiptionAether());
        relicOfTheFallen = new Room(roomDesciptions.roomDesiptionRelic());
        poolRoom = new Room(roomDesciptions.roomDesiptionPool());
        storeRoom = new Room(roomDesciptions.roomDesiptionStore());
        eggRoom = new Room(roomDesciptions.roomDesiptionEgg());
        statueRoom = new Room(roomDesciptions.roomDesiptionStatue());

        // creates Items 
        // adding new item should be acompanied by a "Item Desciption Method" in the RoomDesciption Class
        Item dustBucket = new Item("Dust Bucket", "Its a Bucket, just a bucket", 1, true);

        // adds items to rooms
        entry.addItem(dustBucket);

        // initialise room exits
        entry.setExit("decend", freskoChamber);
        freskoChamber.setExit("north", aetherOrgan);
        freskoChamber.setExit("west", relicOfTheFallen);
        aetherOrgan.setExit("down", downTheHole);
        aetherOrgan.setExit("south", freskoChamber);
        downTheHole.setExit("up", aetherOrgan);
        relicOfTheFallen.setExit("north", poolRoom);
        relicOfTheFallen.setExit("east", freskoChamber);
        poolRoom.setExit("north", eggRoom);
        poolRoom.setExit("west", storeRoom);
        poolRoom.setExit("south", relicOfTheFallen);
        storeRoom.setExit("north", poolRoom);
        eggRoom.setExit("west", statueRoom);
        eggRoom.setExit("south", poolRoom);

        currentplayer.setCurrentRoom(entry);// starting room

    }

    /**
     * Main play routine. Loops until end of play.
     */
    public void play() {
        printWelcome();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.
        boolean finished = false;
        while (!finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println("Thank you for playing.  Good bye.");
    }

    /**
     * Print out the opening message for the player1.
     */
    private void printWelcome() {
        System.out.println();
        System.out.println("Welcome to the World of Zuul!");
        System.out.println("World of Zuul is a new, incredibly boring adventure game.");
        System.out.println("Type 'help' if you need help.");
        System.out.println(seprationLine);
        System.out.println(currentplayer.getCurrentRoom().getDescription());
        System.out.println("");
        System.out.println(currentplayer.getCurrentRoom().getExitString());
    }

    /**
     * Given a command, process (that is: execute) the command.
     *
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    private boolean processCommand(Command command) {
        boolean wantToQuit = false;

        if (command.isUnknown()) {
            System.out.println("I don't know what you mean...");
            return false;
        }

        String commandWord = command.getCommandWord();
        if (commandWord.equals("help")) {
            printHelp();
        } else if (commandWord.equals("go")) {
            goRoom(command);
        } else if (commandWord.equals("look")) {
            look();
        } else if (commandWord.equals("eat")) {
            System.out.println("You have eaten, and are no longer hungry");
        } else if (commandWord.equals("back")) {
            currentplayer.back();
        } else if (commandWord.equals("retrace")) {
            currentplayer.retrace();
        } else if (commandWord.equals("pickup")) {
            pickUp(command);
        } else if (commandWord.equals("drop")) {
            drop(command);
        } else if (commandWord.equals("inventory")) {
            showInventory();
        } else if (commandWord.equals("quit")) {
            wantToQuit = quit(command);
        }
        return wantToQuit;
    }

    // implementations of user commands:
    /**
     * Print out some help information. Here we print some stupid, cryptic
     * message and a list of the command words.
     */
    private void printHelp() {
        System.out.println();
        System.out.println("Your command words are:");
        System.out.println(parser.showCommands());
    }

    /**
     * Try to go in one direction. If there is an exit, enter the new room,
     * otherwise print an error message.
     */
    private void goRoom(Command command) {
        if (!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return;
        }

        String direction = command.getSecondWord();

        // Try to leave current room.
        Room nextRoom = currentplayer.getCurrentRoom().getExit(direction);
        System.out.println("");
        if (nextRoom == null) {
            System.out.println("There is no door!");
        } else {
            currentplayer.changeRoom(nextRoom);
            currentplayer.addToRoomHistory(currentplayer.getPreviusRoom());
            currentplayer.countRoomsEntertUp();
        }
    }

    /**
     * you get the description
     */
    private void look() {
        System.out.println(seprationLine);
        System.out.println(currentplayer.getCurrentRoom().getLongDescription());
    }

    /**
     * picks up item and removes it from the "room item list"
     *
     * @param command
     */
    private void pickUp(Command command) {

        if (!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Pickup What?");
            return;
        }
        List<Item> items = new ArrayList<>();
        items = currentplayer.getCurrentRoom().getAllItems();
        String itemName = command.getSecondWord().toLowerCase();
        for (Item item : items) {
            if (item.getName().toLowerCase().contains(itemName) && item.canBePickedUp()) {
                player1.pickUpItem(item);
                currentplayer.getCurrentRoom().getAllItems().remove(item);
                System.out.println("");
                System.out.println("You picked up " + item.getName());
                return;
            } else if (item.getName().contains(itemName) && !item.canBePickedUp()) {
                System.out.println("");
                System.out.println("Too large to pick up");
                return;
            }
        }
        System.out.println("you cannot pick that up");
    }

    /**
     * drops specific item into currentRoom and there by store it in the
     * itemsInRoom list
     *
     * @param command
     */
    private void drop(Command command) {
        if (!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Drop what?");
            return;
        }
        List<Item> items = new ArrayList<>();
        items = player1.getInventory();
        String itemName = command.getSecondWord().toLowerCase();
        for (Item item : items) {
            if (item.getName().toLowerCase().contains(itemName) && item.canBePickedUp()) {
                player1.drop(item);
                currentplayer.getCurrentRoom().getAllItems().add(item);
                System.out.println("");
                System.out.println("You dropped " + item.getName());
                return;
            }
        }
        System.out.println("You do not have that item");

    }

    /**
     * tries to prints out inventory if it contains any thing
     */
    private void showInventory() {
        if (player1.getInventory() != null) {
            System.out.println("");
            System.out.println("your inventory contain:");
            for (Item item : player1.getInventory()) {
                System.out.println(item.getName());
            }
        } else {
            System.out.println("");
            System.out.println("you a not carring any thing");
        }
    }

    /**
     * "Quit" was entered. Check the rest of the command to see whether we
     * really quit the game.
     *
     * @return true, if this command quits the game, false otherwise.
     */
    private boolean quit(Command command) {
        if (command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        } else {
            return true;  // signal that we want to quit
        }
    }

}
