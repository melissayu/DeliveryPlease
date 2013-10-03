package edu.berkeley.cs160.rarererror.proj;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

public class RateUser extends Activity {
	private Button cancelBtn;
	private Button doneBtn;
	private TextView headerText;
	private RatingBar ratingbar;
	private String username;
	private int curRating;
	
	public void onCreate(Bundle savedInstanceState) {
	 	requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rateuser);
        Bundle b = this.getIntent().getExtras();
        username = b.getString("username");
        
        cancelBtn = (Button) findViewById(R.id.rateuser_cancelBtn);
        doneBtn = (Button) findViewById(R.id.rateuser_doneBtn);
        headerText = (TextView) findViewById(R.id.rateuser_text);
        ratingbar = (RatingBar) findViewById(R.id.ratingbar);
        
        headerText.setText("Please rate your interaction with " + username);
        
        ratingbar.setOnRatingBarChangeListener(new OnRatingBarChangeListener() {
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                //Toast.makeText(RateUser.this, "New Rating: " + rating, Toast.LENGTH_SHORT).show();
                curRating = (int)rating;
            }
        });
        
        cancelBtn.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent myintent = new Intent(v.getContext(), DeliveryPlease.class);
				startActivity(myintent);
			}       	
        });
        
        doneBtn.setOnClickListener(new OnClickListener(){
			@Override
			//TODO need to update user's rating here
			public void onClick(View v) {
                Toast.makeText(RateUser.this, "Rated User: " + curRating, Toast.LENGTH_SHORT).show();
				
				Intent myintent = new Intent(v.getContext(), DeliveryPlease.class);
				startActivity(myintent);
			}       	
        });
	}
}
