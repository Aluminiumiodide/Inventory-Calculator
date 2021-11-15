/**
@author Ali Kahloon, Yasir Abbas, Mohammed Shaik <a
href= "mailto:ali.kahloon1@ucalgary.ca" >ali.kahloon1@ucalgary.ca
href= "mailto:Yasir.Abbas@ucalgary.ca" >Yasir.Abbas@ucalgary.ca
href= "mailto:Mohammed.Shaik@ucalgary.ca" >Mohammed.Shaik@ucalgary.ca
@version 3.8
@since 1.0
*/

package edu.ucalgary.ensf409;

public class Manufacturer
{
    String manuID;
    String name;
    String phoneNum;
    String province;
    public Manufacturer(String manuID, String name, String phone, String province){
        this.manuID = manuID;
        this.name = name;
        this.phoneNum = phone;
        this.province = province;
    }
    public String getManuID() {
        return manuID;
    }
    public String getName() {
        return name;
    }
    public String getPhoneNum() {
        return phoneNum;
    }
    public String getProvince() {
        return province;
    }
}