
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
    private Room currentRoom, previusRoom;
    private int roomsEntert;
    private List<Room> roomHistory;
    private Player player;
    private final String seprationLine = "--------------------------------------------------------------------";

    /**
     * Create the game and initialise its internal map.
     */
    public Game() {
        roomHistory = new ArrayList<>();
        createRooms();
        parser = new Parser();
        roomsEntert = 0;
        player = new Player();
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

//        changeRoom(entry);
        currentRoom = entry; // starting room
        roomHistory.add(currentRoom);
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
     * Print out the opening message for the player.
     */
    private void printWelcome() {
        System.out.println();
        System.out.println("Welcome to the World of Zuul!");
        System.out.println("World of Zuul is a new, incredibly boring adventure game.");
        System.out.println("Type 'help' if you need help.");
        System.out.println(seprationLine);
        System.out.println(currentRoom.getDescription());
        System.out.println("");
        System.out.println(currentRoom.getExitString());
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
            back();
        } else if (commandWord.equals("retrace")) {
            retrace();
        } else if (commandWord.equals("pickup")) {
            pickUp(command);
        } else if (commandWord.equals("inventory")) {
            showInventory();
        } else if (commandWord.equals("quit")) {
            wantToQuit = quit(command);
        }
        return wantToQuit;
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
        items = currentRoom.getAllItems();
        String itemName = command.getSecondWord().toLowerCase();
        for (Item item : items) {
            if (item.getName().toLowerCase().contains(itemName) && item.canBePickedUp()) {
                player.pickUpItem(item);
                currentRoom.getAllItems().remove(item);
                System.out.println("You picked up " + item.getName());
                return;
            } else if (item.getName().contains(itemName) && !item.canBePickedUp()) {
                System.out.println("Too large to pick up");
                return;
            }
        }
        System.out.println("you cannot pick that up");
    }

    private void showInventory() {
        if (player.getInventory() != null) {
            System.out.println("");
            System.out.println("your inventory contain:");
            for (Item item : player.getInventory()) {
                System.out.println(item.getName());
            }
        }
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
        Room nextRoom = currentRoom.getExit(direction);

        System.out.println("");
        if (nextRoom == null) {
            System.out.println("There is no door!");
        } else {
            changeRoom(nextRoom);
        }
    }

    /**
     * changes the room and handels alle the process that are desessery for
     * every thing run properbly sets the room at the start;
     *
     * @param nextRoom
     */
    private void changeRoom(Room nextRoom) {
        previusRoom = currentRoom;
        currentRoom = nextRoom;
        countRooms();
        roomHistory.add(nextRoom);
        System.out.println(seprationLine);
        System.out.println(currentRoom.getDescription());
        System.out.println("");
        System.out.println(currentRoom.getExitString());
    }

    /**
     * you get the description
     */
    private void look() {
        System.out.println(seprationLine);
        System.out.println(currentRoom.getLongDescription());
    }

    /**
     * you enter the previous room
     */
    private void back() {
        System.out.println();
        if (previusRoom == null) {
            System.out.println("You cannot go back");
        } else {
            System.out.println("You go back");
            changeRoom(previusRoom);
        }
    }

    /**
     * retraces you steps through the rooms
     */
    private void retrace() {
        if (roomsEntert == 0) {
            System.out.println("You are at the Start of you journy");
        } else {
            changeRoom(roomHistory.get(roomsEntert - 1));
            roomHistory.remove(roomHistory.get(roomsEntert));
            roomsEntert = roomsEntert - 2; // -2 because changeroom ads 1 to roomsEntert  an we want to counter that when we retrace
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

    /**
     * counts how many rooms you have entert
     */
    private void countRooms() {
        roomsEntert++;
    }
}
