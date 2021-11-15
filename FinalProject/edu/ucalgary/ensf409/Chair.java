/**
@author Ali Kahloon, Yasir Abbas, Mohammed Shaik <a
href= "mailto:ali.kahloon1@ucalgary.ca" >ali.kahloon1@ucalgary.ca
href= "mailto:Yasir.Abbas@ucalgary.ca" >Yasir.Abbas@ucalgary.ca
href= "mailto:Mohammed.Shaik@ucalgary.ca" >Mohammed.Shaik@ucalgary.ca
@version 3.8
@since 1.0
*/

package edu.ucalgary.ensf409;

public class Chair extends Furniture
{
    private char legs;
    private char arms;
    private char seat;
    private char cushion;

    public Chair() {
    }
    public Chair(String id,String type, int price, String manuID,char legs,char arms,char seat,char cushion){
    	this.ID = id;
        this.type = type;
        this.price = price;
        this.manuID = manuID;
        this.legs = legs;
        this.arms = arms;
        this.seat = seat;
        this.cushion = cushion;
    }
    public char getLegs() {
        return legs;
    }
    public char getArms() {
        return arms;
    }
    public char getSeat() {
        return seat;
    }
    public char getCushion() {
        return cushion;
    }
}