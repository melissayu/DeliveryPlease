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
import android.widget.TableLayout;
import android.widget.TextView;

public class OrderPending extends Activity{
	private ImageView homeIcon;
	private AlertDialog.Builder builder;
	private AlertDialog alert;
	
	private OrderObject order;
    private TextView restaurant;
    private TextView orderInfo;
    private TextView orderCosts;
    
    private TableLayout itemsTable;
    private View line;
    private TableLayout costTable;
    
    private TextView customOrder;
    //private TextView subtotal;
    //private TextView tax;
    private TextView total;
    
    private Button seeOffersButton;
    private Button cancelButton;
    
    private Chronometer timeElapsed;
    private DecimalFormat twoDForm;
    
    private Bundle b;
    
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.orderpending);
        
        b = this.getIntent().getExtras();
        order = (OrderObject) b.get("orderobject");
        restaurant = (TextView) findViewById(R.id.Restaurant);
        orderInfo = (TextView) findViewById(R.id.Items);
        orderCosts = (TextView) findViewById(R.id.ItemPrices);
        //subtotal = (TextView) findViewById(R.id.subtotal);
        //tax = (TextView) findViewById(R.id.tax);
        total = (TextView) findViewById(R.id.total);
        seeOffersButton = (Button) findViewById(R.id.SeeOffersButton);
        cancelButton = (Button) findViewById(R.id.CancelButton);
        timeElapsed = (Chronometer) findViewById(R.id.chronometer);
        twoDForm = new DecimalFormat("0.00");
        
        customOrder = (TextView) findViewById(R.id.orderpending_customorder);
        itemsTable = (TableLayout) findViewById(R.id.orderpending_itemstable);
        costTable = (TableLayout) findViewById(R.id.orderpending_costtable);
        line = (View) findViewById(R.id.orderpending_separator);
        
        if(order.customOrder!=null){
        	itemsTable.setVisibility(View.GONE);
        	costTable.setVisibility(View.GONE);
        	line.setVisibility(View.GONE);
        	customOrder.setText(order.customOrder);
        } else {
        	customOrder.setVisibility(View.GONE);
        }
        	
        	
        // Fill in order information
        // Set restaurant to restaurant name, obtained from database entry 
        
        //restaurant.setText("Thai Basil");
        restaurant.setText(order.restaurantName);
        
        // Set order
        if(order.customOrder == null){
	        String ordersummary = order.itemList();
	        if (ordersummary == null){
	        	ordersummary = "1 Pineapple Fried Rice\n1 Sprite";
	        }
	        String costs = order.itemPricesList();
	        if (ordersummary == null){
	        	costs = "This really is not working";
	        }
	        orderInfo.setText(ordersummary);
	        orderCosts.setText(costs);
        }
        /* Debugging Purposes
        String ordersummary = "1 Pineapple Fried Rice\n1 Sprite";
        if (order == null)
        	ordersummary = "this isn't working";
        orderInfo.setText(ordersummary);
        String costs = "8.50\n1.75";
        orderCosts.setText(costs);
		*/
		
        // Calculate subtotal
        //double subtotalCalc = 8.50 + 1.75;
        //subtotal.setText(twoDForm.format(subtotalCalc));
        
        //subtotal.setText(twoDForm.format(order.subtotalPrice));
        
        // Calculate tax
        
        //double taxCalc = subtotalCalc*0.10;
        //int temp = (int)(taxCalc*100);
        //taxCalc = ((double)temp) / 100.0;
        //tax.setText(twoDForm.format(taxCalc));
        
        //tax.setText(twoDForm.format(order.taxPrice));
        
        // Calculate total
        //double totalCalc = subtotalCalc + taxCalc;
        //total.setText(twoDForm.format(totalCalc));
        if(order.customOrder==null){
        	total.setText(twoDForm.format(order.totalPrice));
        }
        // Start timer
        timeElapsed.setBase(SystemClock.elapsedRealtime());
        timeElapsed.start();
        
        seeOffersButton.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent myintent = new Intent(v.getContext(), OrderRequest.class);
        		myintent.putExtra("orderobject", order);
        		myintent.putExtras(b);
				startActivity(myintent);
			}       	
        });
        
        cancelButton.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				finish();
				//Intent myintent = new Intent(v.getContext(), Menu.class);
				//startActivity(myintent);
			}       	
        });
        
        createDialog();
    	homeIcon = (ImageView) findViewById(R.id.orderpendingHomeIcon);
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
		        		Intent i = new Intent(OrderPending.this, DeliveryPlease.class);
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