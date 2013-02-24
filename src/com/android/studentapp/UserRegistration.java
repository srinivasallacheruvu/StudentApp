package com.android.studentapp;


import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class UserRegistration extends Activity {
	private DBHelper dbHelper;
	private SQLiteDatabase database;
	private String userTable = DBHelper.userTable;
	Cursor cursor;
	
	EditText name;
	EditText email;
	EditText phone;
	EditText address;
	EditText password;
	EditText con_password;
	//EditText user_type;
	Spinner user_type; 
	Button reg_user_btn;
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_registration);
		dbHelper = new DBHelper(this.getApplicationContext());
        name = (EditText)findViewById(R.id.reg_user_name);
        email = (EditText)findViewById(R.id.reg_user_email);
        phone = (EditText)findViewById(R.id.reg_user_phone);
        address = (EditText)findViewById(R.id.reg_user_address);
        password = (EditText)findViewById(R.id.reg_user_pwd);
        con_password = (EditText)findViewById(R.id.reg_user_confirm_pwd);
        //user_type = (EditText)findViewById(R.id.user_type);
        user_type = (Spinner)findViewById(R.id.user_type);
        Button reg_user_btn = (Button)findViewById(R.id.reg_user_btn);
        
        reg_user_btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String pswd = password.getText().toString();
				String con_pswd = con_password.getText().toString();
				String user_user_type = String.valueOf(user_type.getSelectedItem());
				if(pswd.equals(con_pswd)) {
					if (user_user_type.equals("--*Select User Type*--")) {
        				Toast.makeText(getApplicationContext(),
        						"Select user type ," + "\n fileds can't be left blank",
        						Toast.LENGTH_LONG).show();
        	        } else {
        	        	insertvalues();
        	        }
					
				} else {
					Toast.makeText(getApplicationContext(),"Both Password should be same", Toast.LENGTH_SHORT).show();
				}
				
			}
		});
	}
	private void insertvalues()
	  {
	      String user_name = name.getText().toString();
	   	  String user_email = email.getText().toString();
	   	  String user_phone = phone.getText().toString();
	   	  String user_address = address.getText().toString();
	   	  String user_password = password.getText().toString();
	   	  String user_user_type = String.valueOf(user_type.getSelectedItem());
	   	  
	   	  database = dbHelper.getWritableDatabase();
	   	  
		  ContentValues cv = new ContentValues();
	      cv.put("user_name", user_name);
	      cv.put("user_unicode",user_password);
	      cv.put("user_mailid", user_email);
	      cv.put("phone_no",user_phone);
	      cv.put("address",user_address);
	      cv.put("user_type",user_user_type);
	      
	      database.insert(userTable, null, cv);
	      
	      Toast.makeText(getApplicationContext(),"Successfully Inserted", Toast.LENGTH_SHORT).show();
	      
	      Intent intent = new Intent(UserRegistration.this, UserLogin.class);
	      startActivity(intent);
	  } 

}
