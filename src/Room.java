
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

    private String description, exitList2; //brug denne sammen med setExit hvis det ikke fungere og ikkke bliver ændret
//    private Room northExit, southExit, eastExit, estExit, westExit, upExit, downExit;
    private HashMap<String, Room> exits;

    /**
     * Create a room described "description". Initially, it has no exits.
     * "description" is something like "a kitchen" or "an open court yard".
     *
     * @param description The room's description.
     */
    public Room(String description) {
        this.description = description;
        exits = new HashMap<String, Room>();
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

}
