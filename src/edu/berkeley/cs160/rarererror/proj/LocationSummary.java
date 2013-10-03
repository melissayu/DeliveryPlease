package edu.berkeley.cs160.rarererror.proj;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class LocationSummary extends Activity{
	private ImageView homeIcon;
	private AlertDialog.Builder builder;
	private AlertDialog alert;

    private String restaurantName;
    private ImageView locationMap;
    private TextView locationName;
    private TextView locationInfo;
    private Button checkInButton;
    private Button cancelButton;
    private String couchdb_id;
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.locationsummary);
        
        Bundle b = this.getIntent().getExtras();
        couchdb_id = b.getString("id");
        restaurantName = b.getString("Restaurant Name");
        locationMap = (ImageView) findViewById(R.id.LocationMap);
        locationName = (TextView) findViewById(R.id.LocationName);
        locationInfo = (TextView) findViewById(R.id.LocationInfo);
        checkInButton = (Button) findViewById(R.id.CheckInButton);
        cancelButton = (Button) findViewById(R.id.CancelButton);
        
        // Determine what shows up on page by the restaurant name
        // Enter a line of code to obtain database entry
        // Set information
        String info = "2519-2521 Durant Ave\nUC Campus Area\nBerkeley, CA 94704";
        locationName.setText(restaurantName);
        locationInfo.setText(info);
        
        checkInButton.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				WebService webService = new WebService("http://deliveryplease.iriscouch.com/restaurants/"+couchdb_id+"/");
				Log.d("deliveryplease", "Checking into " + couchdb_id);
		        JSONObject jsonobj = webService.webGet();
		        try {
					int count = jsonobj.getInt("count");
					jsonobj.put("count", count + 1);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		        String response = webService.webPut(jsonobj);
		        Log.d("deliveryplease", "HTTP PUT response " + response);
		        
				Intent myintent = new Intent(v.getContext(), DeliveryAvailableRequests.class);
				startActivity(myintent);
			}       	
        });
        
        cancelButton.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent myintent = new Intent(v.getContext(), CheckInList.class);
				startActivity(myintent);
			}       	
        });
        
        createDialog();
    	homeIcon = (ImageView) findViewById(R.id.locationsummaryHomeIcon);
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
		        		Intent i = new Intent(LocationSummary.this, DeliveryPlease.class);
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