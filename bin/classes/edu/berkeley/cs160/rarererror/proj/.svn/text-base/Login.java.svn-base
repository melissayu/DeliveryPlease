package edu.berkeley.cs160.rarererror.proj;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends Activity {

	private String username;
	private String password;
	
	private EditText usernameEditText;
	private EditText passwordEditText;
	private Button loginBtn;
	private Button signupBtn;
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
                
        usernameEditText = (EditText) findViewById(R.id.login_username);
        passwordEditText = (EditText) findViewById(R.id.login_password);
        loginBtn = (Button) findViewById(R.id.login_loginBtn);
        signupBtn = (Button) findViewById(R.id.login_signupBtn);
        
        usernameEditText.setImeOptions(EditorInfo.IME_FLAG_NO_EXTRACT_UI);
        passwordEditText.setImeOptions(EditorInfo.IME_FLAG_NO_EXTRACT_UI);
        
        usernameEditText.setOnKeyListener(new OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                    (keyCode == KeyEvent.KEYCODE_ENTER)) {
                	// Perform action on key press
                	InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            		imm.hideSoftInputFromWindow(usernameEditText.getWindowToken(), 0);
                  return true;
                }
                return false;
            }
        });

        passwordEditText.setOnKeyListener(new OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                    (keyCode == KeyEvent.KEYCODE_ENTER)) {
                  // Perform action on key press
                	InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            		imm.hideSoftInputFromWindow(usernameEditText.getWindowToken(), 0);
                  return true;
                }
                return false;
            }
        });
        
        loginBtn.setOnKeyListener(new OnKeyListener(){
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                    (keyCode == KeyEvent.KEYCODE_ENTER)) {
                  // Perform action on key press
                	InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            		imm.hideSoftInputFromWindow(usernameEditText.getWindowToken(), 0);
                  return true;
                }
                return false;
            }        	
        });
        
        loginBtn.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				username = usernameEditText.getText().toString();
		        password = passwordEditText.getText().toString();
				
		        //TODO ADD CHECK IF USERNAME/PW VALID IN DB
				if(username.length() > 0 && password.length() > 0){
					SharedPreferences settings = getSharedPreferences("UserPreferences", MODE_PRIVATE);  
			        SharedPreferences.Editor prefEditor = settings.edit(); 
			        prefEditor.putString("UserName", username);
			        prefEditor.commit();
			        
					Intent myintent = new Intent(v.getContext(), DeliveryPlease.class);
					myintent.putExtra("username", username);
					startActivity(myintent);
				}
				else {
					Toast toast = Toast.makeText(getApplicationContext(), "Invalid username/password, try again", Toast.LENGTH_LONG);
					toast.show();
				}
			}       	
        });
        
        signupBtn.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				Intent myintent = new Intent(v.getContext(), Register.class);
				startActivity(myintent);
			}
        });
    }
}
