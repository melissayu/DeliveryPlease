package edu.berkeley.cs160.rarererror.proj;

import java.text.DecimalFormat;
import java.util.ArrayList;

import android.os.Parcel;
import android.os.Parcelable;

public class OrderObject implements Parcelable{
	public ArrayList<MenuItem> orderList;
	public String[] orderListStringArray;
	public String[] itemPricesStringArray;
	public String restaurantName;
	public String customOrder;
	
	public double subtotalPrice;
	public double taxPrice;
	public double totalPrice;
	public double deliveryPrice;
	public double totalWithDeliveryPrice;
	
	public String subtotalPriceText;
	public String taxPriceText;
	public String totalPriceText;
	public String deliveryPriceText;
	public String totalWithDeliveryPriceText;
	
	public String addressStreet;
	public String addressCity;
	
	public OrderObject(String rest){
		orderList = new ArrayList<MenuItem>();
		subtotalPrice = 0;
		taxPrice = 0;
		totalPrice = 0;
		deliveryPrice = 0;
		restaurantName=rest;
		addressStreet = "";
		addressCity = "";
		
		customOrder = null;
	}
	
	public void setCustomOrder(String str){
		customOrder = str;
	}
	
	
	public void add(MenuItem mi){
		subtotalPrice += mi.itemPrice;
		// Check if list already has the item
		int i = orderList.indexOf(mi);
		if (i > -1){
			MenuItem old_mi = orderList.get(i);
			old_mi.itemPrice += mi.itemPrice;
			old_mi.itemQty++;
		} else {
			mi.itemQty = 1;	// items on menu have 0 quantity, to distinguish from items on order
			orderList.add(mi);
		}
	}
	
	// add a remove function
	public void remove(MenuItem mi){
		subtotalPrice -= mi.itemPrice/mi.itemQty;
		if (mi.itemQty == 1)
			orderList.remove(mi);
		else { 
			mi.itemPrice -= mi.itemPrice/mi.itemQty;
			mi.itemQty--;
		}
	}
	
	public void updatePrice(){
		taxPrice = subtotalPrice*0.10;
		totalPrice = subtotalPrice + taxPrice;
		totalWithDeliveryPrice = totalPrice + deliveryPrice;
		formatNumberStrings();
		
		orderListStringArray = new String[orderList.size()];
		for (int i = 0; i < orderList.size(); i++){
			orderListStringArray[i] = orderList.get(i).itemName;
		}
		
		itemPricesStringArray = new String[orderList.size()];
		for (int i = 0; i < orderList.size(); i++){
			itemPricesStringArray[i] = Double.toString(orderList.get(i).itemPrice);
		}
	}
	
	public void updateTotalWithDelivery(){
		totalWithDeliveryPrice = totalPrice + deliveryPrice;		
	}
	
	public void formatNumberStrings(){
		DecimalFormat twoDForm = new DecimalFormat("0.00");
        subtotalPriceText = twoDForm.format(subtotalPrice);
        taxPriceText = twoDForm.format(taxPrice);
        totalPriceText = twoDForm.format(totalPrice);
        deliveryPriceText = twoDForm.format(deliveryPrice);
        totalWithDeliveryPriceText = twoDForm.format(totalWithDeliveryPrice);
	}
	
    public String itemList(){
    	String s = "";
    	for (int i = 0; i < orderListStringArray.length; i++){
    		s = s + orderListStringArray[i] + "\n";
    	}
    	s = s.substring(0, s.length()-1);
    	return s;
    }

    public String itemPricesList(){
    	String s = "";
    	DecimalFormat twoDForm = new DecimalFormat("0.00");
    	for (int i = 0; i < itemPricesStringArray.length; i++){
    		s = s + twoDForm.format(Double.valueOf(itemPricesStringArray[i])) + "\n";
    	}
    	s = s.substring(0, s.length()-1);
    	return s;
    }
    
	public int describeContents() {
        return 0;
    }

	public void writeToParcel(Parcel out, int flags) {
		out.writeStringArray(orderListStringArray);
		out.writeStringArray(itemPricesStringArray);
        out.writeDouble(subtotalPrice);
        out.writeDouble(taxPrice);
        out.writeDouble(deliveryPrice);
        out.writeDouble(totalPrice);
        out.writeDouble(totalWithDeliveryPrice);
        out.writeString(restaurantName);
        out.writeString(addressStreet);
        out.writeString(addressCity);
        out.writeString(customOrder);
    }
	
	public static final Parcelable.Creator<OrderObject> CREATOR = new Parcelable.Creator<OrderObject>() {
        public OrderObject createFromParcel(Parcel in) {
            return new OrderObject(in);
        }

        public OrderObject[] newArray(int size) {
            return new OrderObject[size];
        }
    };
    
    private OrderObject(Parcel in) {
    	orderListStringArray = in.createStringArray();
    	itemPricesStringArray = in.createStringArray();
    	subtotalPrice = in.readDouble();
    	taxPrice = in.readDouble();
    	deliveryPrice = in.readDouble();
    	totalPrice = in.readDouble();
    	totalWithDeliveryPrice = in.readDouble();
    	restaurantName=in.readString();
    	addressStreet = in.readString();
    	addressCity = in.readString();
    	customOrder = in.readString();
    }
}
