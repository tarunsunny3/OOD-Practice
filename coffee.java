/**
 * Vending Machine
 * Requirements:
 * Item, Size(quantity, price), Inventory,
 * VendingMachine
 * 
 * 
 * 
 * 
 * 
 */
import java.util.*;

 class Item{
    // Drink has name
    private String name;
    private double price; 

    public double getPrice(){
        return this.price;
    }

    Item(String name, double price){
        this.name = name;
        this.price = price;
    }
 }
 class Inventory{
    // Map<Drink, Size> drinkToSize;
    Map<Item, Integer> itemToQuantity;


    Inventory(){

    }
    public boolean checkIfItemExists(Item item){
        return itemToQuantity.containsKey(item) && itemToQuantity.get(item) > 0;
    }
    public void updateItem(Item item, int quantity){
        if(itemToQuantity.containsKey(item)){
            itemToQuantity.put(item, itemToQuantity.get(item) + quantity);
        }else{
            itemToQuantity.put(item, quantity);
        }
    }
    
 }
 class VendingMachine{
    Inventory inventory;
    double balance;
    // Scanner sc = new Scanner(System.in);
    public double getTotalPrice(List<Item> items){
        double totalPrice = 0.0;
        for(Item i: items){
            totalPrice += i.getPrice();
        }
        return totalPrice;
    }

    getREfund()
 }