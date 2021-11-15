/**
@author Ali Kahloon, Yasir Abbas, Mohammed Shaik <a
href= "mailto:ali.kahloon1@ucalgary.ca" >ali.kahloon1@ucalgary.ca
href= "mailto:Yasir.Abbas@ucalgary.ca" >Yasir.Abbas@ucalgary.ca
href= "mailto:Mohammed.Shaik@ucalgary.ca" >Mohammed.Shaik@ucalgary.ca
@version 3.8
@since 1.0
*/

package edu.ucalgary.ensf409;

public class Lamp extends Furniture
{	
    private char base;
    private char bulb;

    public Lamp () {
    }   
    public Lamp(String id,String type, int price, String manuID, char base, char bulb)
    {
        this.ID = id;
        this.type = type;
        this.price = price;
        this.manuID = manuID;
        this.base = base;
        this.bulb = bulb;
    }
    public char getBase()
    {
        return base;
    }
    public char getBulb()
    {
        return bulb;
    }
    public String getType()
    {
        return type;
    }
}