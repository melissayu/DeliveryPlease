package edu.berkeley.cs160.rarererror.proj;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

public class OrderPickRestaurant extends Activity {
	
	private ArrayList<String> Restaurants = new ArrayList<String>();
	private ListView restaurantList;
	
	private ImageView homeIcon;
	private AlertDialog.Builder builder;
	private AlertDialog alert;
	
	private EditText SearchFieldEditText;
	private JSONArray rows;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pickrestaurant);
        
        SearchFieldEditText = (EditText) findViewById(R.id.SearchField);        
        SearchFieldEditText.setImeOptions(EditorInfo.IME_FLAG_NO_EXTRACT_UI);

        SearchFieldEditText.setOnKeyListener(new OnKeyListener() {
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
        
        /*Restaurants.add("Thai Basil");
        Restaurants.add("Gypsy's");
        Restaurants.add("Asian Ghetto");
        Restaurants.add("King Pin");*/
        
        WebService webService = new WebService("http://deliveryplease.iriscouch.com/restaurants/_design/restaurantlist/_view/all");
        //Pass the parameters if needed , if not then pass dummy one as follows      
        JSONObject jsonresponse = webService.webGet();
		try {
			rows = jsonresponse.getJSONArray("rows");
		    for(int i = 0; i < rows.length(); i++){
		    	JSONObject json =rows.getJSONObject(i).getJSONObject("value"); 
	        	Restaurants.add(json.getString("name") + 
	        			"\t\t| " + json.getInt("count") +
	        			" Users Checked-in");
	        }		
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    
    	restaurantList = (ListView) findViewById(R.id.RestaurantList);
		restaurantList.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1 , Restaurants));
		
		restaurantList.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
				// For now it will be enough to click the whole row to access the next activity
				// But to create pop-up function on this, I need to change the layout to FrameLayout
					
				if(position==0){
					Intent myintent = new Intent(v.getContext(), Menu.class);
					myintent.putExtra("name", Restaurants.get(position));
					startActivity(myintent);
				}
				//for testing custom order
				else{
					Intent myintent = new Intent(v.getContext(), CustomOrder.class);
					myintent.putExtra("name", Restaurants.get(position));
					startActivity(myintent);
				}
				// De-comment the line below to get pop-up function
				//showDialog(position);
			}
		});
		
		builder = new AlertDialog.Builder(this);
		builder.setMessage("Are you sure you want to cancel this \norder and go back to the main menu?")
		       .setCancelable(false)
		       .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
		           public void onClick(DialogInterface dialog, int id) {
		                //MyActivity.this.finish();
		        		Intent i = new Intent(OrderPickRestaurant.this, DeliveryPlease.class);
		        		startActivity(i);

		           }
		       })
		       .setNegativeButton("No", new DialogInterface.OnClickListener() {
		           public void onClick(DialogInterface dialog, int id) {
		                dialog.cancel();
		           }
		       });
		alert = builder.create();
		
        homeIcon = (ImageView) findViewById(R.id.pickRestaurantHomeIcon);
        homeIcon.setOnClickListener(new OnClickListener(){
        	public void onClick (View v){
        		alert.show();
        	}
        });

    }

    // This method determines what shows up in the pop-up dialog 
    protected Dialog onCreateDialog(int id){
		
		String selectedRestaurant = Restaurants.get(id);
		
		// Contents of the dialog
		CharSequence[] items = {"berkeleydude1", "gobears", "mrhelpful", "sketchymcsketcherson"};
		
		// What gets pulled up, ideally we will be using a database access instead here
		if (selectedRestaurant.equals("Thai Basil")){
			//items = {"berkeleydude1", "gobears", "mrhelpful", "sketchymcsketcherson"};
		} else {
			//items = {"berkeleydude1", "gobears", "mrhelpful", "sketchymcsketcherson"};
		}
		
		
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Thai Basil Check-ins:");
		builder.setItems(items, new DialogInterface.OnClickListener() {
		    public void onClick(DialogInterface dialog, int item) {
		        return;
		    }
		});
		
		AlertDialog alert = builder.create();
		return alert;
	}
}