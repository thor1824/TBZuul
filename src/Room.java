
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * Class Room - a room in an adventure game.
 *
 * This class is part of the "World of Zuul" application. "World of Zuul" is a
 * very simple, text based adventure game.
 *
 * A "Room" represents one location in the scenery of the game. It is connected
 * to other rooms via exits. The exits are labelled north, east, south, west.
 * For each direction, the room stores a reference to the neighboring room, or
 * null if there is no exit in that direction.
 *
 * @author Michael Kölling and David J. Barnes
 * @version 2011.07.31
 */
public class Room {

    private String description;
    private HashMap<String, Room> exits;
    private List<Item> itemsInRoom;
    private Room currentRoom;
    
    /**
     * Create a room described "description". Initially, it has no exits.
     * "description" is something like "a kitchen" or "an open court yard".
     *
     * @param description The room's description.
     */
    public Room(String description) {
        this.description = description;
        exits = new HashMap<String, Room>();
        itemsInRoom = new ArrayList<>();
    }

    /**
     * adds an item to item room list
     * 
     * @param item 
     */
    public void addItem(Item item) {
        itemsInRoom.add(item);
    }

    /**
     * Define the exits of this room. Every direction either leads to another
     * room or is null (no exit there).
     *
     * @param direction
     * @param neighbor
     */
    public void setExit(String direction, Room neighbor) {
        exits.put(direction, neighbor);
    }

    /**
     * get the exit
     *
     * @param direction
     * @return
     */
    public Room getExit(String direction) {
        return exits.get(direction);
    }

    /**
     * Return a description of the room’s exits, for example "Exits: north
     * west".
     *
     * @return A description of the available exits.
     */
    public String getExitString() {
        String returnString = "Exits:";
        Set<String> keys = exits.keySet();
        for (String exit : keys) {
            returnString += " " + exit;
        }
        return returnString;
    }

    /**
     * @return The description of the room.
     */
    public String getDescription() {
        return description;
    }
    
    /**
     * return the list of items in the room
     * 
     * @return 
     */
    public List<Item> getAllItems()
    {
        return itemsInRoom;
    }  

    /**
     * Return a long description of this room, of the form: You are in the
     * kitchen. Exits: north west
     *
     * should be changed to better fit!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
     * 
     * @return A description of the room, including exits.
     */
    public String getLongDescription() {
        String roomItemsDesciption = "";

        for (int i = 0; i < itemsInRoom.size(); i++) {
            if (i >= 0) {
                roomItemsDesciption = "You see " + itemsInRoom.get(i).getItemDesciption() + "\n";
            }
            else {
                roomItemsDesciption = "you also see " + itemsInRoom.get(i).getItemDesciption() + "\n";
            }
        }
        return "You " + description + "´\n" + roomItemsDesciption + "\n" + getExitString();

    }

    

}
