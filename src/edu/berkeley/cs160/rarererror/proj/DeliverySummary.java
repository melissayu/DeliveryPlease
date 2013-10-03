package edu.berkeley.cs160.rarererror.proj;

import java.text.DecimalFormat;
import java.util.Iterator;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.LinearLayout;
import android.widget.TextView;

public class DeliverySummary extends Activity {
	private Button donebutton;
	private String user;
	private String userinfo;
	private TextView headerText;
	private TextView userInfo;
	private TextView restaurant;
	private TextView orderInfo;
	private TextView orderCosts;
	//private TextView subtotal;
    //private TextView tax;
    private TextView total;
    //private TextView deliveryCharge;
	private Chronometer timeElapsed; 
	private TextView breadcrumb1;
	private TextView breadcrumb2;
    private DecimalFormat twoDForm;

    private TextView userSummaryTitle;
    private int ETA;
    
    private UserObject fakeUser;
    private OrderObject testorder;
    
    private LinearLayout itemsLayout;
	private View line;
	private LinearLayout costLayout;
	private TextView customOrder;
    
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ordersummary);

        Bundle b = this.getIntent().getExtras();
        user = b.getString("User Name");
        fakeUser = (UserObject) b.get("user");
        testorder = (OrderObject) b.get("orderobject");
        testorder.formatNumberStrings();
        ETA = b.getInt("ETA");
        
        //userinfo = "";
        headerText = (TextView) findViewById(R.id.dsTextView01);
        userInfo = (TextView) findViewById(R.id.ordersummary_userinfo);
        restaurant = (TextView) findViewById(R.id.ordersummary_restaurant);
        orderInfo = (TextView) findViewById(R.id.ordersummary_items);
        orderCosts = (TextView) findViewById(R.id.ordersummary_itemprices);
        //subtotal = (TextView) findViewById(R.id.ordersummary_subtotal);
        //tax = (TextView) findViewById(R.id.ordersummary_tax);
        total = (TextView) findViewById(R.id.ordersummary_total); 
        //deliveryCharge = (TextView) findViewById(R.id.ordersummary_delivery);
        timeElapsed = (Chronometer) findViewById(R.id.ordersummary_chronometer);
        donebutton = (Button) findViewById(R.id.ordersummary_button_received);
        breadcrumb1 = (TextView) findViewById(R.id.summary_breadcrumb1);
        breadcrumb2 = (TextView) findViewById(R.id.summary_breadcrumb2);
        twoDForm = new DecimalFormat("0.00");

        userSummaryTitle = (TextView) findViewById(R.id.UserSummaryTitle);
        
        userSummaryTitle.setText("Order User Summary");
        
        itemsLayout = (LinearLayout) findViewById(R.id.ordersummary_orderitemlayout);
        costLayout = (LinearLayout) findViewById(R.id.ordersummary_ordercostlayout);
        line = (View) findViewById(R.id.ordersummary_separator);
        customOrder = (TextView) findViewById(R.id.ordersummary_customorder);
        if(testorder.customOrder != null){
        	itemsLayout.setVisibility(View.GONE);
        	costLayout.setVisibility(View.GONE);
        	line.setVisibility(View.GONE);
        	customOrder.setText(testorder.customOrder);
        } else {
        	customOrder.setVisibility(View.GONE);
        }
        
        donebutton.setOnClickListener(new OnClickListener(){
        	
			@Override
			public void onClick(View v) {
				// TODO: Do stuff that will close the current order
				//Intent i = new Intent(DeliverySummary.this, DeliveryPlease.class);
        		//startActivity(i);
				Intent i = new Intent(DeliverySummary.this, RateUser.class);
				i.putExtra("username", fakeUser.getUsername());
				startActivity(i);
			}
        	
        });
        
        // create a fake user
        fakeUser = new UserObject();
        fakeUser.setUsername("Mel");
        fakeUser.setAddress("Unit 1, 2650 Durant Ave\nBerkeley, CA 94720");
        fakeUser.setPhonenumber("555-555-5555");
        /*
        // create a fake/testing order
        testorder = new OrderObject();
        MenuItem item1 = new MenuItem("1 Pineapple Fried Rice", 8.50);
        MenuItem item2 = new MenuItem("1 Sprite", 1.75);
        testorder.add(item1);
        testorder.add(item2);
        testorder.updatePrice();
		*/
        restaurant.setText("Thai Basil");
        
        orderInfo.setText(testorder.itemList());
        orderCosts.setText(testorder.itemPricesList());
        
        String e = "";
        if (ETA == -1)
        	e = "N/A";
        else
        	e = Integer.toString(ETA) + " min";
        
        //userinfo = user + "\nLocation:\nUnit 1, 2650 Durant Ave\nBerkeley, CA 94720";
        //userinfo = userinfo + "\nEstimated time for delivery: " + "5 min";
        userInfo.setText(fakeUser.getUsername() + "\nLocation:\n" + fakeUser.getAddress() + 
        				 "\nEstimated time for delivery:\n\t" + e);
        //deliveryCharge.setText(testorder.deliveryPriceText);
        
        // Calculate subtotal
        //subtotal.setText(testorder.subtotalPriceText);
        
        // Calculate tax
        //tax.setText(testorder.taxPriceText);
        
        // Calculate total
        total.setText(testorder.totalWithDeliveryPriceText);
        
        timeElapsed.setBase(SystemClock.elapsedRealtime());
        timeElapsed.start();
        donebutton.setText("Order Delivered");
        breadcrumb1.setText("Check-In -> Select Offer -> Confirm Offer -> ");
        breadcrumb2.setText("Delivery Summary"); 
    }
}
