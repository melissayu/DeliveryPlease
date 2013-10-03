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
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

public class DeliveryAvailableRequests extends Activity{
	private ImageView homeIcon;
	private AlertDialog.Builder builder;
	private AlertDialog alert;
	
	private ArrayList<UserObject> Requests = new ArrayList<UserObject>();
	private DeliveryUserListAdapter myAdapter;
	//private ArrayList<String> Requests = new ArrayList<String>();
	//private ArrayAdapter<String> myAdapter;
	private ListView requestList;
	
	final Handler handler = new Handler(){
		 public void handleMessage(Message msg){
			 //update UI
			 UserObject user = (UserObject) msg.obj;    		
    		Requests.add(user);
   			// String user = (String) msg.obj;    		
 			//Requests.add(user);
    			myAdapter.notifyDataSetChanged();   		
		 }
	 };
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.availablerequests);
                
    	requestList = (ListView) findViewById(R.id.RequestList);
    	myAdapter = new DeliveryUserListAdapter(this,R.layout.deliveryuserlistitem, Requests);
//    	myAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, Requests);
		requestList.setAdapter(myAdapter);
		
        new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				while(true){
					WebService webService = new WebService("http://deliveryplease.iriscouch.com/orderrequests/_design/list/_view/all");
					//Pass the parameters if needed , if not then pass dummy one as follows      
					JSONObject jsonresponse = webService.webGet();
					JSONArray rows;
					try {
						rows = jsonresponse.getJSONArray("rows");
						for(int i = 0; i < rows.length(); i++){
							JSONObject json = rows.getJSONObject(i).getJSONObject("value"); 
							//String user = json.getString("name");
							Gson gson = new Gson();
							UserObject user = gson.fromJson(json.getString("userobj"), UserObject.class);
							if(!Requests.contains(user)){
								Message msg = new Message();
								msg.obj = user;
								handler.sendMessage(msg);
								Log.d("deliveryplease", user.toString());
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
				Intent myintent = new Intent(v.getContext(), DeliveryRequestDetails.class);
				myintent.putExtra("user", Requests.get(position));
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
		        		Intent i = new Intent(DeliveryAvailableRequests.this, DeliveryPlease.class);
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