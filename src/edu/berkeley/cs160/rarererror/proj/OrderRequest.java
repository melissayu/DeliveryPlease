package edu.berkeley.cs160.rarererror.proj;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class OrderRequest extends Activity {
	private OrderObject order;
	private ImageView homeIcon;
	private AlertDialog.Builder builder;
	private AlertDialog alert;
	
	private ArrayList<UserObject> Requests = new ArrayList<UserObject>();
	private OrderUserListAdapter myAdapter;
	//private ArrayList<String> Requests = new ArrayList<String>();
	//private ArrayAdapter<String> myAdapter;
	private ListView requestList;
	private JSONArray rows;
	private String order_id;
	private String order_rev;
	
	final Handler handler = new Handler(){
		 public void handleMessage(Message msg){
			 //update UI
			 UserObject user = (UserObject) msg.obj;
			 Requests.add(user);
			 //String name = (String) msg.obj;    		
   			 //Requests.add(name);
			 myAdapter.notifyDataSetChanged();
			 //myAdapter.add(name);   		
		 }
	 };
	 
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.orderrequests);
             
		Bundle b = this.getIntent().getExtras();
		order = (OrderObject) b.get("orderobject");
		order_id = b.getString("couchdb_id");
		order_rev = b.getString("rev");
		
        /*Requests.add("berkeleydude1 - $2 - 15min");
        Requests.add("gobears - $5 - 5min");
        Requests.add("mrhelpful - $1 - 1hr");
        Requests.add("sketchymcsketcherson - $10 - 1min");*/
    
    	requestList = (ListView) findViewById(R.id.RequestList);
    	myAdapter = new OrderUserListAdapter(this,R.layout.orderuserlistitem, Requests);
    	//myAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1 , Requests);
		requestList.setAdapter(myAdapter);
		
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				while(true){
					WebService webService = new WebService("http://deliveryplease.iriscouch.com/orderoffers/_design/list/_view/all");
					//Pass the parameters if needed , if not then pass dummy one as follows      
					JSONObject jsonresponse = webService.webGet();
					
					try {
						rows = jsonresponse.getJSONArray("rows");
						for(int i = 0; i < rows.length(); i++){
							JSONObject json = rows.getJSONObject(i).getJSONObject("value");
							//String name = json.getString("name") + " - $" + json.getInt("fee") + " - " + json.getInt("eta") + "minutes";
							Gson gson = new Gson();
							UserObject user = gson.fromJson(json.getString("userobj"), UserObject.class);
							if(!Requests.contains(user)){
								Message msg = new Message();
								msg.obj = user;
								handler.sendMessage(msg);
							}							
						}		
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
        }).start();

		
		requestList.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
				Bundle b = new Bundle();
				
				try{
					for(int i = 0; i < rows.length(); i++){
						JSONObject json = rows.getJSONObject(i).getJSONObject("value");
						Gson gson = new Gson();
						UserObject user = gson.fromJson(json.getString("userobj"), UserObject.class);
						//String name = json.getString("name") + " - $" + json.getInt("fee") + " - " + json.getInt("eta") + "minutes";
						
						if(user.equals(Requests.get(position))){
							b.putString("User Name", json.getString("name"));
							//b.putParcelable("UserObject", user);
							
							WebService web = new WebService("http://deliveryplease.iriscouch.com/orderoffers/" + 
									json.getString("_id") + 
									"?rev=" + json.getString("_rev"));
							String response = web.webDelete();
							Log.d("deliveryplease", "HTTP DELETE " + response);
							
							WebService web2 = new WebService("http://deliveryplease.iriscouch.com/orderrequests/" + 
									order_id + 
									"?rev=" + order_rev);
							String response2 = web2.webDelete();
							break;
						}							
					}
				} catch (JSONException e){
					
				}
				
				Intent myintent = new Intent(v.getContext(), OrderSummary.class);
				myintent.putExtra("orderobject", order);
				myintent.putExtras(b);
        		
				startActivity(myintent);
			}
		});
		
		createDialog();
		homeIcon = (ImageView) findViewById(R.id.availablerequestsHomeIcon);
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
		        		Intent i = new Intent(OrderRequest.this, DeliveryPlease.class);
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
