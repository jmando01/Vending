package com.example.vending.vending;

/**
 * Created by Joubert on 03/05/2015.
 */

public class VendingItem {

    public String getItemName() {
        return itemName;
    }
    public void setItemName(String itemName) {
        this.itemName = itemName;
    }
    public String getItemPrice() {
        return itemPrice;
    }
    public void setItemPrice(String itemPrice) {
        this.itemPrice = itemPrice;
    }
    public int getItemImage() {
        return itemImage;
    }
    public void setItemImage(int itemImage) {
        this.itemImage = itemImage;
    }
    public String getItemCounter(){
        return itemCounter;
    }
    public void setItemCounter(String itemCounter){
        this.itemCounter = itemCounter;
    }

    public VendingItem(String itemName, String itemPrice, int itemImage, String itemCounter){
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.itemImage = itemImage;
        this.itemCounter = itemCounter;
    }

    public VendingItem(){

    }

    private String itemName ;
    private String itemPrice;
    private int itemImage;
    private String itemCounter;
}
