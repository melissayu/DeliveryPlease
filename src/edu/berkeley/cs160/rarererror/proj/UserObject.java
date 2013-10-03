package edu.berkeley.cs160.rarererror.proj;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

import android.os.Parcel;
import android.os.Parcelable;

public class UserObject implements Parcelable{

	public String username;
	public String address;
	public String phonenumber;
	public boolean orderUser;
	
	public double rating;
	public int numRatings;
	
	public UserObject(){
		username = "";
		address = "";
		phonenumber = "";
		orderUser = true;
		rating = 0;
		numRatings = 0;
	}
	
	public void setUsername(String name){
		username = name;
	}
	
	public void setAddress(String addr){
		addr = "\t" + addr;
		address = addr.replace("\n", "\n\t");
	}
	
	public void setPhonenumber(String num){
		phonenumber = num;
	}
	
	public String getUsername(){
		return username;
	}
	
	public String getAddress(){
		return address;
	}
	
	public String getPhonenumber(){
		return phonenumber;
	}
	
	public int describeContents() {
        return 0;
    }

	public void setRating(float i){
		rating = i;
	}
	public void setNumRatings(int i){
		numRatings = i;
	}
	public double getRating(){
		double r = rating * 2.0;
		r += 0.5;
		int temp = (int) r;
		r = (double) temp;
		r /= 2.0;
		return r;
	}
	public int getNumRatings(){
		return numRatings;
	}
	
	public void updateRating(int newRating){
		double n = numRatings * rating + newRating;
		double m = n / (numRatings+1);
		rating = m;
		numRatings = numRatings+1;
	}
	
	public void writeToParcel(Parcel out, int flags) {
        out.writeString(username);
        out.writeString(address);
        out.writeString(phonenumber);
        out.writeDouble(rating);
        out.writeInt(numRatings);
    }
	
	public static final Parcelable.Creator<UserObject> CREATOR = new Parcelable.Creator<UserObject>() {
        public UserObject createFromParcel(Parcel in) {
            return new UserObject(in);
        }

        public UserObject[] newArray(int size) {
            return new UserObject[size];
        }
    };
    
    private UserObject(Parcel in) {
    	username = in.readString();
    	address = in.readString();
    	phonenumber = in.readString();
    	rating = in.readFloat();
    	numRatings = in.readInt();
    }
    
    public String toString(){
		return username + address + phonenumber;
    	
    }
    
	@Override
	// Determines if UserObject contains same information
	public boolean equals(Object obj) {
		if (this.toString().equals(obj.toString()))
			return true;
		else
			return false;
	}
}
