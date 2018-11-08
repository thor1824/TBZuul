
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

    public List<Room> getRoomHistory() {
        return roomHistory;
    }

    public void setRoomHistory(List<Room> roomHistory) {
        this.roomHistory = roomHistory;
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

    /**
     * adds an item to inventory
     *
     * @param i
     */
    public void pickUpItem(Item i) {
        inventory.add(i);
    }

    /**
     * removes an item from inventory
     */
    public void drop(Item i) {
        inventory.remove(i);
    }
}
