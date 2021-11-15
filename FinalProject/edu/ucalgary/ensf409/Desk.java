package edu.ucalgary.ensf409;

public class Desk extends Furniture
{
    private char legs;
    private char top;
    private char drawers;

    public Desk() {
    }
    public Desk(String id,String type, int price, String manuID,char legs,char top,char drawers){
        this.ID = id;
        this.type = type;
        this.price = price;
        this.manuID = manuID;
        this.legs = legs;
        this.top = top;
        this.drawers = drawers;
    }
    public char getLegs() {
        return legs;
    }
    public char getTop() {
        return top;
    }
    public char getDrawers() {
        return drawers;
    }
}