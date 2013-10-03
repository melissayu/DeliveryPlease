package edu.berkeley.cs160.rarererror.proj;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EnterLocation extends Activity {
	private Button cancelBtn;
	private Button doneBtn;
	private OrderObject order;
	private EditText streetText;
	private EditText cityText;
	
	 public void onCreate(Bundle savedInstanceState) {
		 	requestWindowFeature(Window.FEATURE_NO_TITLE);
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.enterlocation);
	        Bundle b = this.getIntent().getExtras();
	        order = (OrderObject) b.get("orderobject");
	        
	        streetText = (EditText) findViewById(R.id.street);
	        cityText = (EditText) findViewById(R.id.city);
	        
	        cancelBtn = (Button) findViewById(R.id.enterLocationCancel);
	        cancelBtn.setOnClickListener(new OnClickListener(){
	        	public void onClick(View v){
	        		finish();
	        	}
	        });
	        
	        doneBtn = (Button) findViewById(R.id.enterLocationDone);
	        doneBtn.setOnClickListener(new OnClickListener(){
	        	public void onClick(View v){
	        		String street = streetText.getText().toString();
	        		String city = cityText.getText().toString();
	        		
	        		if(street.length()==0 || city.length()==0){
	        			Toast toast = Toast.makeText(getApplicationContext(), "Please enter your address", Toast.LENGTH_LONG);
						toast.show();
	        		}
	        		else {
		        		order.addressStreet = street;
		        		order.addressCity = city;
				        
		        		Intent i = new Intent(EnterLocation.this, OrderPending.class);
		        		i.putExtra("orderobject", order);
		        		Bundle b = new Bundle();
		        		
		        		
		        		
		        		WebService webService = new WebService("http://deliveryplease.iriscouch.com/orderrequests/");
				        JSONObject jsonobj = new JSONObject();
				        SharedPreferences settings = getSharedPreferences("UserPreferences", MODE_PRIVATE);
				        
				        UserObject user = new UserObject();
		        		user.username = settings.getString("UserName", "username");
		        		user.address = street + "," + city;
		        		user.orderUser = true;
		        		user.phonenumber = "5101234567";
		        		
		        		Gson gson = new Gson();
		        		String userjson = gson.toJson(user);
				        
				        try {
				        	jsonobj.put("userobj", userjson);
							jsonobj.put("name", settings.getString("UserName", "username"));
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
				        
				        //Get JSON response from server the "" are where the method name would normally go if needed example
				        // webService.webGet("getMoreAllerts", params);
				        String response = webService.webPost(jsonobj);
				        String couchdb_id = null;
				        try {
							JSONObject json = new JSONObject(response);
							couchdb_id = json.getString("id");
							b.putString("couchdb_id", couchdb_id);
					        b.putString("rev", json.getString("rev"));
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
				        
		        		i.putExtras(b);
		        		startActivity(i);
	        		}
	        	}
	        });
	 }
}
 