package edu.berkeley.cs160.rarererror.proj;

import java.text.DecimalFormat;

import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.TextView;

public class DeliveryOfferPending extends Activity{
	private ImageView homeIcon;
	private AlertDialog.Builder builder;
	private AlertDialog alert;
	
    private TextView userInfo;
    private TextView restaurant;
    private TextView orderInfo;
    private TextView orderCosts;
    private TextView subtotal;
    private TextView tax;
    private TextView total;
    private TextView deliveryCharge;
    private TextView ETAText;
    private Button deliverButton;
    private Button cancelButton;
    private Chronometer timeElapsed;

    private int ETA;
    private UserObject fakeUser;
    private OrderObject testorder;
    private String couchdb_id;
    
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.offerpending);
        
        Bundle b = this.getIntent().getExtras();
        //fakeUser = (UserObject) b.get("user");
        testorder = (OrderObject) b.get("orderobject");
        testorder.formatNumberStrings();
        ETA = b.getInt("ETA");
        couchdb_id = b.getString("couchdb_id");
        
        userInfo = (TextView) findViewById(R.id.UserInfo);
        restaurant = (TextView) findViewById(R.id.Restaurant);
        orderInfo = (TextView) findViewById(R.id.Items);
        orderCosts = (TextView) findViewById(R.id.ItemPrices);
        //subtotal = (TextView) findViewById(R.id.subtotal);
        //tax = (TextView) findViewById(R.id.tax);
        total = (TextView) findViewById(R.id.total);
        deliveryCharge = (TextView) findViewById(R.id.DeliveryCharge);
        ETAText = (TextView) findViewById(R.id.ETA);
        deliverButton = (Button) findViewById(R.id.DeliverButton);
        cancelButton = (Button) findViewById(R.id.CancelButton);
        timeElapsed = (Chronometer) findViewById(R.id.chronometer);

        deliverButton.setEnabled(false);
        
        new waitforoffer().execute();
        
        // Determine what shows up on page by the username
        // Enter a line of code to obtain database entry
        	// The entry should contain the user info and the order requested 
        // Set user information according to database entry
        //String usersummary = "To:\t" + fakeUser.getUsername() + 
       // 					 "\nAt:" + fakeUser.getAddress();
        String usersummary = "To:\t" + b.getString("User Name") + 
		 "\nAt:" + "2141 Channing";
        userInfo.setText(usersummary);
        // KEEP IN MIND: There's still a bug in how to deal with getaddress (it's missing a tab)

        // Set restaurant to restaurant name, obtained from database entry 
        restaurant.setText("Thai Basil");
        
        // Set order to 
        orderInfo.setText(testorder.itemList());
        orderCosts.setText(testorder.itemPricesList());

        // Calculate subtotal
        //subtotal.setText(testorder.subtotalPriceText);
        
        // Calculate tax
        //tax.setText(testorder.taxPriceText);
        
        // Calculate total
        total.setText(testorder.totalPriceText);
        
        // Set conditions - for now fakes, ideally should take data from user name
        
        String c = "\tDelivery Cost: " + testorder.deliveryPriceText;
        deliveryCharge.setText(c);

        String e = "";
        if (ETA == -1)
        	e = "\tETA: " + "N/A";
        else
        	e = "\tETA: " + Integer.toString(ETA) + " min";
        ETAText.setText(e);

        // Start timer
        timeElapsed.setBase(SystemClock.elapsedRealtime());
        timeElapsed.start();
        
        deliverButton.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent myintent = new Intent(v.getContext(), DeliverySummary.class);
				myintent.putExtra("user", fakeUser);
				myintent.putExtra("orderobject", testorder);
				Bundle b = new Bundle();
                b.putInt("ETA", ETA);
                myintent.putExtras(b);
				startActivity(myintent);
			}       	
        });
        
        cancelButton.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent myintent = new Intent(v.getContext(), DeliveryAvailableRequests.class);
				startActivity(myintent);
			}       	
        });
        
        createDialog();
    	homeIcon = (ImageView) findViewById(R.id.offerpendingHomeIcon);
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
		        		Intent i = new Intent(DeliveryOfferPending.this, DeliveryPlease.class);
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
    
    private class waitforoffer extends AsyncTask<Object, Object, JSONObject> {

		@Override
		protected JSONObject doInBackground(Object... params) {
			// TODO Auto-generated method stub
			while(true){
				WebService webService = new WebService("http://deliveryplease.iriscouch.com/orderoffers/" + couchdb_id);
				JSONObject json = webService.webGet();
				Log.d("deliveryplease", "id=" + couchdb_id + " | response=" + json.toString());
				if(json.has("error")){
					return json;
				} else {
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
		
		protected void onPostExecute(JSONObject json){
			deliverButton.setEnabled(true);
			Log.d("deliveryplease", "DeliverButton enabled!");
		}
    	
    }
}