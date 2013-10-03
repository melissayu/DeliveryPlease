package edu.berkeley.cs160.rarererror.proj;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

public class CheckInList extends Activity {
	private ImageView homeIcon;
	private AlertDialog.Builder builder;
	private AlertDialog alert;
	
	private ArrayList<String> Restaurants = new ArrayList<String>();
	private ListView restaurantList;
	private EditText SearchFieldEditText;
	private Button SearchButton;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.checkinlist);
        
        Restaurants.add("Asian Ghetto");
        Restaurants.add("Thai Basil");
        Restaurants.add("Gypsy's");
        Restaurants.add("King Pin");
    
        SearchFieldEditText = (EditText) findViewById(R.id.SearchField);
        SearchFieldEditText.setImeOptions(EditorInfo.IME_FLAG_NO_EXTRACT_UI);
        SearchButton = (Button) findViewById(R.id.SearchButton);
        
        SearchFieldEditText.setOnKeyListener(new OnKeyListener(){
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                    (keyCode == KeyEvent.KEYCODE_ENTER)) {
                  // Perform action on key press
                	InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            		imm.hideSoftInputFromWindow(SearchFieldEditText.getWindowToken(), 0);
                  return true;
                }
                return false;
            }        	
        });
        
        SearchButton.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
			}
        });
        
        LocationManager mlocManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
       	LocationListener mlocListener = new MyLocationListener();
       	mlocManager.requestLocationUpdates( LocationManager.GPS_PROVIDER, 0, 0, mlocListener);
       	
    	restaurantList = (ListView) findViewById(R.id.RestaurantList);
		restaurantList.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1 , Restaurants));
		
		restaurantList.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
				Intent myintent = new Intent(v.getContext(), LocationSummary.class);
				Bundle b = new Bundle();
                b.putString("Restaurant Name", Restaurants.get(position));
                myintent.putExtras(b);
				startActivity(myintent);
			}
		});
		
		builder = new AlertDialog.Builder(this);
		builder.setMessage("Are you sure you want to cancel this \norder and go back to the main menu?")
		       .setCancelable(false)
		       .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
		           public void onClick(DialogInterface dialog, int id) {
		        		Intent i = new Intent(CheckInList.this, DeliveryPlease.class);
		        		startActivity(i);

		           }
		       })
		       .setNegativeButton("No", new DialogInterface.OnClickListener() {
		           public void onClick(DialogInterface dialog, int id) {
		                dialog.cancel();
		           }
		       });
		alert = builder.create();
		homeIcon = (ImageView) findViewById(R.id.checkinlistHomeIcon);
        homeIcon.setOnClickListener(new OnClickListener(){
        	public void onClick (View v){
        		alert.show();
        	}
        });
    }
    public class MyLocationListener implements LocationListener{
    	@Override
    	public void onLocationChanged(Location loc){
    		loc.getLatitude();
    		loc.getLongitude();
    		String Text = 	"My current location is: " +
    						"Latitude = " + loc.getLatitude() +
    						"Longitude = " + loc.getLongitude();
    		
    		Toast.makeText( getApplicationContext(),Text,Toast.LENGTH_SHORT).show();
    	}

    	@Override
    	public void onProviderDisabled(String provider){
    	   	Toast.makeText( getApplicationContext(),"Gps Disabled",Toast.LENGTH_SHORT ).show();
    	}

    	@Override
    	public void onProviderEnabled(String provider){
    		Toast.makeText( getApplicationContext(),"Gps Enabled",Toast.LENGTH_SHORT).show();
    	}

    	@Override
    	public void onStatusChanged(String provider, int status, Bundle extras){
    	}
    }
}
