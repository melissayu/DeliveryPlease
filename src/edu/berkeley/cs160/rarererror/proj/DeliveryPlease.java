package edu.berkeley.cs160.rarererror.proj;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class DeliveryPlease extends Activity {
	private String username;
	private TextView welcomeText;
	
	private Button logoutBtn;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        Button butt = (Button) findViewById(R.id.main_button_check_in);
        Button orderButton = (Button) findViewById(R.id.main_button_order);
        Button logoutBtn = (Button) findViewById(R.id.main_logoutBtn);
        //Button testbutton = (Button) findViewById(R.id.testbutton);
        
        SharedPreferences settings = getSharedPreferences("UserPreferences", MODE_PRIVATE);  
        username = settings.getString("UserName", "username");
              
        welcomeText = (TextView) findViewById(R.id.main_welcomeText);
        welcomeText.setText("Welcome back, " + username + "!");
        
        butt.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent myintent = new Intent(v.getContext(), CheckInList.class);
				startActivity(myintent);
			}       	
        });
        
        orderButton.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent myintent = new Intent(v.getContext(), OrderPickRestaurant.class);
				startActivity(myintent);
			}       	
        });     
        
        logoutBtn.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				SharedPreferences settings = getSharedPreferences("UserPreferences", MODE_PRIVATE);  
		        SharedPreferences.Editor prefEditor = settings.edit(); 
		        prefEditor.putString("UserName", "");
		        prefEditor.commit();
		        
				Intent myintent = new Intent(v.getContext(), Login.class);
				startActivity(myintent);
			}       	
        });     
        /*testbutton.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent myintent = new Intent(v.getContext(), DeliveryRequestDetails.class);
				Bundle b = new Bundle();
                b.putString("User Name", "blahblahuser");
                myintent.putExtras(b);
				startActivity(myintent);
			}       	
        });*/
    }
}
