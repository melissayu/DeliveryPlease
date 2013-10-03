package edu.berkeley.cs160.rarererror.proj;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class CustomOrder extends Activity {
	private ImageView homeIcon;
	private AlertDialog.Builder builder;
	private AlertDialog alert;
	
	private Button submitBtn;
	private OrderObject order;
	private String customOrder;
	private TextView restaurantName;
	private String restName;
	private EditText orderText;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customorder);
        
        Bundle b = this.getIntent().getExtras();
        restName = (String) b.get("name");
        restaurantName = (TextView) findViewById(R.id.customorder_restname);
        restaurantName.setText(restName + " Order");
       
        order = new OrderObject(restName);
        orderText = (EditText) findViewById(R.id.customorder_ordertext);
        
        submitBtn = (Button) findViewById(R.id.customorder_submitbutton);
        submitBtn.setOnClickListener(new OnClickListener(){
        	public void onClick(View v){
        		customOrder = orderText.getText().toString();
        		order.setCustomOrder(customOrder);
        		if(customOrder.length()==0){
					Toast toast = Toast.makeText(getApplicationContext(), "You must enter a valid order", Toast.LENGTH_LONG);
					toast.show();
        		}
        		else{
	        		Intent i = new Intent(v.getContext(), EnterLocation.class);
	        		i.putExtra("orderobject", order);
	        		startActivity(i);
        		}
        	}
        });
        
        createDialog();
    	homeIcon = (ImageView) findViewById(R.id.customOrderHomeIcon);
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
			        		Intent i = new Intent(CustomOrder.this, DeliveryPlease.class);
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
