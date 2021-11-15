/**
@author Ali Kahloon, Yasir Abbas, Mohammed Shaik <a
href= "mailto:ali.kahloon1@ucalgary.ca" >ali.kahloon1@ucalgary.ca
href= "mailto:Yasir.Abbas@ucalgary.ca" >Yasir.Abbas@ucalgary.ca
href= "mailto:Mohammed.Shaik@ucalgary.ca" >Mohammed.Shaik@ucalgary.ca
@version 3.8
@since 1.0
*/

package edu.ucalgary.ensf409;

public class Filing extends Furniture
{
    private char rails;
    private char drawers;
    private char cabinet;

    public Filing() {
    }
    public Filing(String id,String type, int price, String manuID,char rails,char drawers,char cabinet){
        this.ID = id;
        this.type = type;
        this.price = price;
        this.manuID = manuID;
        this.rails = rails;
        this.cabinet = cabinet;
        this.drawers = drawers;
    }
    public char getRails() {
        return rails;
    }
    public char getDrawers() {
        return drawers;
    }
    public char getCabinet() {
        return cabinet;
    }
}