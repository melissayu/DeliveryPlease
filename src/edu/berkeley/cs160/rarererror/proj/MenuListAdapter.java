package edu.berkeley.cs160.rarererror.proj;

import java.text.DecimalFormat;
import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class MenuListAdapter extends ArrayAdapter<MenuItem>{
	private ArrayList<MenuItem> menulist;
	private DecimalFormat twoDForm;
	private int textViewResource;
	
	public MenuListAdapter(Context context, int textViewResourceId, ArrayList<MenuItem> list) {
        super(context, textViewResourceId, list);
        this.menulist = list;
        twoDForm = new DecimalFormat("0.00");
        textViewResource = textViewResourceId;
	}

	@Override
    public View getView(int position, View convertView, ViewGroup parent) {
            View v = convertView;
            if (v == null) {
                LayoutInflater vi = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                //v = vi.inflate(R.layout.menulistitem, null);
                v =  vi.inflate(textViewResource, null);
            }
            MenuItem item = menulist.get(position);
            if (item != null) {
            		
                    TextView tt = (TextView) v.findViewById(R.id.menuItemName);
                    TextView bt = (TextView) v.findViewById(R.id.menuItemPrice);
                    
                    bt.setGravity(5);	// Set alignment to right
                    
                    if (tt != null){
                    	if (item.itemQty > 0) {
                    		tt.setText(item.itemQty + " " + item.itemName); // Item is listed on order
                    	} else {
                        	tt.setText(item.itemName); // Item is listed on menu	
                    	}
                    }
                    if(bt != null){
                    	bt.setText("$" + twoDForm.format(item.itemPrice));
                    }
            }
            return v;
    }

}
