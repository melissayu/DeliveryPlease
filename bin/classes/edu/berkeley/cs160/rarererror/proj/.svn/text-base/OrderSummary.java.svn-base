package edu.berkeley.cs160.rarererror.proj;

import java.text.DecimalFormat;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class OrderSummary extends Activity{
	private ImageView homeIcon;
	private AlertDialog.Builder builder;
	private AlertDialog alert;
	
	private Button donebutton;
	private String user;
	private OrderObject order;
	private TextView userInfo;
	private TextView restaurant;
	private TextView orderInfo;
	private TextView headerText;
	private TextView orderCosts;
	//private TextView subtotal;
	//private TextView tax;
	//private TextView deliveryCharge;
	private LinearLayout itemsLayout;
	private View line;
	private LinearLayout costLayout;
	private TextView customOrder;
	
	private TextView total;
	private Chronometer timeElapsed; 
	private TextView breadcrumb1;
	private TextView breadcrumb2;
    private DecimalFormat twoDForm;
	
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ordersummary);
        Bundle b = this.getIntent().getExtras();
        user = b.getString("User Name");
        order = (OrderObject) b.get("orderobject");
        headerText = (TextView) findViewById(R.id.dsTextView01);
        userInfo = (TextView) findViewById(R.id.ordersummary_userinfo);
        restaurant = (TextView) findViewById(R.id.ordersummary_restaurant);
        orderInfo = (TextView) findViewById(R.id.ordersummary_items);
        orderCosts = (TextView) findViewById(R.id.ordersummary_itemprices);
        //subtotal = (TextView) findViewById(R.id.ordersummary_subtotal);
        //tax = (TextView) findViewById(R.id.ordersummary_tax);
        //deliveryCharge = (TextView) findViewById(R.id.ordersummary_delivery);
        total = (TextView) findViewById(R.id.ordersummary_total);
        timeElapsed = (Chronometer) findViewById(R.id.ordersummary_chronometer);
        donebutton = (Button) findViewById(R.id.ordersummary_button_received);
        breadcrumb1 = (TextView) findViewById(R.id.summary_breadcrumb1);
        breadcrumb2 = (TextView) findViewById(R.id.summary_breadcrumb2);
        twoDForm = new DecimalFormat("0.00");
        
        itemsLayout = (LinearLayout) findViewById(R.id.ordersummary_orderitemlayout);
        costLayout = (LinearLayout) findViewById(R.id.ordersummary_ordercostlayout);
        line = (View) findViewById(R.id.ordersummary_separator);
        customOrder = (TextView) findViewById(R.id.ordersummary_customorder);
        if(order.customOrder != null){
        	itemsLayout.setVisibility(View.GONE);
        	costLayout.setVisibility(View.GONE);
        	line.setVisibility(View.GONE);
        	customOrder.setText(order.customOrder);
        } else {
        	customOrder.setVisibility(View.GONE);
        }
        
        donebutton.setOnClickListener(new OnClickListener(){
        	
			@Override
			public void onClick(View v) {
				// TODO: Do stuff that will close the current order
				//Intent i = new Intent(OrderSummary.this, DeliveryPlease.class);
        		//startActivity(i);
				
				Intent i = new Intent(OrderSummary.this, RateUser.class);
				i.putExtra("username", user);
				startActivity(i);
			}
        	
        });
        
        // create a fake/testing order
        /*
        OrderObject testorder = new OrderObject();
        MenuItem item1 = new MenuItem("Pineapple Fried Rice", 12);
        MenuItem item2 = new MenuItem("Thai Iced Tea", 2);
        testorder.add(item1);
        testorder.add(item2);
        
        userInfo.setText(user);
        restaurant.setText("Thai Basil");
        String sorderinfo = "";
    	String sordercost = "";
    	NumberFormat formater = new DecimalFormat("#0.00");
        Iterator<MenuItem> iter = testorder.orderList.iterator();
        while(iter.hasNext()){
        	MenuItem i = iter.next();
        	sorderinfo = sorderinfo + i.itemName;
        	
        	sordercost = sordercost + formater.format(i.itemPrice);
        	if(iter.hasNext()){
        		sorderinfo += "\n";
        		sordercost += "\n";
        	}
        }
        */
        
        //orderInfo.setText(sorderinfo);
        //orderCosts.setText(sordercost);
        //testorder.updatePrice();
        //subtotal.setText(testorder.subtotalPriceText);
        headerText.setText("Your order is on its way!");
        userInfo.setText(user);
        restaurant.setText("Thai Basil");
        if(order.customOrder==null){
	        orderInfo.setText(order.itemList());
	        orderCosts.setText(order.itemPricesList());
	        //subtotal.setText(twoDForm.format(order.subtotalPrice));
	        //tax.setText(twoDForm.format(order.taxPrice));
	        //deliveryCharge.setText("2.00");
	        total.setText(twoDForm.format(order.totalPrice + 2.00));
        }        
        timeElapsed.setBase(SystemClock.elapsedRealtime());
        timeElapsed.start();
        donebutton.setText("I received my order");
        breadcrumb1.setText("Select Restaurant -> Order From Menu -> Confirm Delivery Offer -> ");
        breadcrumb2.setText("Order Summary");
        
        createDialog();
    	homeIcon = (ImageView) findViewById(R.id.ordersummaryHomeIcon);
            homeIcon.setOnClickListener(new OnClickListener(){
            	public void onClick (View v){
            		alert.show();
            	}
            });
    }
    
    public void createDialog(){
    	builder = new AlertDialog.Builder(this);
		builder.setMessage("Are you sure you want to cancel this \norder and go back to the main menu?")
		       .setCancelable(false)
		       .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
		           public void onClick(DialogInterface dialog, int id) {
		        		Intent i = new Intent(OrderSummary.this, DeliveryPlease.class);
		        		startActivity(i);

		           }
		       })
		       .setNegativeButton("No", new DialogInterface.OnClickListener() {
		           public void onClick(DialogInterface dialog, int id) {
		                dialog.cancel();
		           }
		       });
		alert = builder.create();
    }
}