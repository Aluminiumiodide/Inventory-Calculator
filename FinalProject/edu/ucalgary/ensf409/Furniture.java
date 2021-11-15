/**
@author Ali Kahloon, Yasir Abbas, Mohammed Shaik <a
href= "mailto:ali.kahloon1@ucalgary.ca" >ali.kahloon1@ucalgary.ca
href= "mailto:Yasir.Abbas@ucalgary.ca" >Yasir.Abbas@ucalgary.ca
href= "mailto:Mohammed.Shaik@ucalgary.ca" >Mohammed.Shaik@ucalgary.ca
@version 3.8
@since 1.0
*/

package edu.ucalgary.ensf409;

public abstract class Furniture
{
	protected String ID;
	protected String type;
	protected int price;
	protected String manuID;
    
    public String getType()
    {
        return type;
    }
    public int getPrice()
    {
        return price;
    }
    public String getID()
    {
        return ID;
    }
    public String getManuID()
    {
        return manuID;
    }
}