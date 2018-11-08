
import java.util.ArrayList;
import java.util.List;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author thor1
 */
public class Player {

    private List<Item> inventory;
    private List<Room> roomHistory;
    private Room currentRoom, previusRoom;
    //private double weight;
    private int hunger, roomsEntert;

    /**
     *
     */
    public Player(/*double weight*/) {
        inventory = new ArrayList<>();
        //this.weight = weight;
        roomsEntert = 0;
        roomHistory = new ArrayList<>();
    }

    // Getter and Setter
    public List<Room> getRoomHistory() {
        return roomHistory;
    }

    public Room getPreviusRoom() {
        return previusRoom;
    }

    public void setPreviusRoom(Room previusRoom) {
        this.previusRoom = previusRoom;
    }

    public int getRoomsEntert() {
        return roomsEntert;
    }

    public void setRoomsEntert(int roomsEntert) {
        this.roomsEntert = roomsEntert;
    }

    /**
     * Get the value of currentRoom
     *
     * @return the value of currentRoom
     */
    public Room getCurrentRoom() {
        return currentRoom;
    }

    /**
     * Set the value of currentRoom
     *
     * @param currentRoom new value of currentRoom
     */
    public void setCurrentRoom(Room currentRoom) {
        this.currentRoom = currentRoom;
    }

    public List<Item> getInventory() {
        return inventory;
    }

    public int getHunger() {
        return hunger;
    }

    // Methods for room change
    public void addToRoomHistory(Room room) {
        roomHistory.add(room);
    }

    /**
     * counts how many rooms you have entert
     */
    public void countRoomsEntertUp() {
        roomsEntert++;

    }

    /**
     * currentplayer.getCurrentRoom() changes the room and handels alle the
     * process that are desessery for every thing run properbly sets the room at
     * the start;
     *
     * @param nextRoom
     */
    public void changeRoom(Room nextRoom) {
        previusRoom = currentRoom;
        currentRoom = nextRoom;
        System.out.println("--------------------------------------------------------------------");
        System.out.println(currentRoom.getDescription());
        System.out.println("");
        System.out.println(currentRoom.getExitString());

    }

    public void retrace() {
        if (roomsEntert == 0) {
            System.out.println("You are at the Start of you journy");
        } else {
            changeRoom(roomHistory.get(roomsEntert - 1));
            roomHistory.remove(roomsEntert - 1);
            roomsEntert = roomsEntert - 1; // -2 because changeroom ads 1 to roomsEntert  an we want to counter that when we retrace
        }
    }

    public void back() {
        System.out.println();
        if (previusRoom == null) {
            System.out.println("You cannot go back");
        } else {
            System.out.println("You go back");
            changeRoom(previusRoom);
            addToRoomHistory(previusRoom);
            countRoomsEntertUp();
        }
    }

    // methods for inventory add and removind
    /**
     * adds an item to inventory
     *
     * @param item
     */
    public void pickUpItem(Item item) {
        inventory.add(item);
    }

    /**
     * removes an item from inventory
     * @param item
     */
    public void drop(Item item) {
        inventory.remove(item);
    }

}
