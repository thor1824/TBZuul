/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author thor1
 */
public class Item 
{
    private String name, itemDesciption;
    private double weight;

    public Item(String name, String itemDesciption, double weight) {
        this.name = name;
        this.itemDesciption = itemDesciption;
        this.weight = weight;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getItemDesciption() {
        return itemDesciption;
    }

    public void setItemDesciption(String itemDesciption) {
        this.itemDesciption = itemDesciption;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }
    
}
