package com.example.vending.vending;

/**
 * Created by Joubert on 06/05/2015.
 */
public class Vending {

    private String vendingName ;
    private String vendingType;
    private String vendingID;

    public String getVendingName() {
        return vendingName;
    }
    public void setVendingName(String vendingName) {
        this.vendingName = vendingName;
    }
    public String getVendingType() {
        return vendingType;
    }
    public void setVendingType(String vendingType) {
        this.vendingType = vendingType;
    }
    public String getVendingID() {
        return vendingID;
    }
    public void setVendingID(String vendingID) {
        this.vendingID = vendingID;
    }

    public Vending(String vendingName, String vendingType, String vendingID){
        this.vendingName = vendingName;
        this.vendingType = vendingType;
        this.vendingID = vendingID;
    }

    public Vending(){

    }
}
