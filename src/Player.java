
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
    //private double weight;
    private int hunger;

    public Player(/*double weight*/) {
        inventory = new ArrayList<>();
        //this.weight = weight;
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
    public void drop(Item i)
    {
        inventory.remove(i);
    }
}
