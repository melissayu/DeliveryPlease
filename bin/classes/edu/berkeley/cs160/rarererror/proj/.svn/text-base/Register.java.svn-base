package edu.berkeley.cs160.rarererror.proj;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Register extends Activity {
	private String username;
	private String email;
	private String password;
	
	private EditText usernameEditText;
	private EditText emailEditText;
	private EditText passwordEditText;

	private Button registerBtn;
	private Button cancelBtn;
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        
        registerBtn = (Button) findViewById(R.id.register_registerBtn);
        cancelBtn = (Button) findViewById(R.id.register_cancelBtn);
        usernameEditText = (EditText) findViewById(R.id.register_username);
        emailEditText = (EditText) findViewById(R.id.register_email);
        passwordEditText = (EditText) findViewById(R.id.register_password);
        
        registerBtn.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				username = usernameEditText.getText().toString();
				email = emailEditText.getText().toString();
				password = passwordEditText.getText().toString();
				
		        //TODO ADD NEW USER INFO TO DB, need to check if username/email already used
				if(username.length() > 0 && password.length() > 0 && email.length() > 0){
					SharedPreferences settings = getSharedPreferences("UserPreferences", MODE_PRIVATE);  
			        SharedPreferences.Editor prefEditor = settings.edit(); 
			        prefEditor.putString("UserName", username);
			        prefEditor.commit();
			        
					Intent myintent = new Intent(v.getContext(), DeliveryPlease.class);
					myintent.putExtra("username", username);
					startActivity(myintent);
				}
				else if (username.length()==0){
					Toast toast = Toast.makeText(getApplicationContext(), "Sorry, invalid username", Toast.LENGTH_LONG);
					toast.show();
				}
				else if (email.length()==0){
					Toast toast = Toast.makeText(getApplicationContext(), "Sorry, invalid email", Toast.LENGTH_LONG);
					toast.show();
				}
				else if (password.length()==0){
					Toast toast = Toast.makeText(getApplicationContext(), "Sorry, invalid password", Toast.LENGTH_LONG);
					toast.show();
				}
			}       	
        });
        
        cancelBtn.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				finish();
			}
        });
    }
}
