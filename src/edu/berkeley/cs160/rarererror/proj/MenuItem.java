package edu.berkeley.cs160.rarererror.proj;

public class MenuItem {
	
	public String itemName;
	public double itemPrice;
	public int itemQty;
	
	public MenuItem(String name, double price, int quantity){
		itemName = name;
		itemPrice = price;
		itemQty = quantity;
	}
	
	public MenuItem clone(){
		return new MenuItem(itemName, itemPrice, itemQty);
	}
	
	@Override
	// Determines if menu item obj is another menu item of the same name
	public boolean equals(Object obj) {
		if (this.toString().equals(obj.toString()))
			return true;
		else
			return false;
	}
	
	@Override
	public String toString(){
		return itemName;
	}
}
