package com.android.studentapp;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UserLogin extends Activity {
	EditText login_user_name;
	EditText login_user_pswd;
	SQLiteDatabase db;
	DBHelper dbHelper;
	Button bu_log;
	private String userTable = DBHelper.userTable;
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_login);
		dbHelper = new DBHelper(this.getApplicationContext());
        bu_log=(Button)findViewById(R.id.login);
        login_user_name = (EditText)findViewById(R.id.login_user_name);
        login_user_pswd = (EditText)findViewById(R.id.login_user_pwd);
        db = dbHelper.getWritableDatabase();
        bu_log.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				checkUser();
			}
		});
	}
	private void checkUser() {
		String user_name = login_user_name.getText().toString();
		String pswd = login_user_pswd.getText().toString();
		
		String sql =  "select * from user_details where user_name='"+user_name+"' and user_unicode='"+pswd+"'"; 
		Cursor cur=db.rawQuery(sql, null);
		if(cur.getCount()!=0) {
			 cur.moveToFirst();
	         while(!cur.isAfterLast())
	          {
        		 String user_type = cur.getString(6);
        		 Intent i=new Intent(getApplicationContext(), ModuleAccess.class);
     			 i.putExtra("user_type", user_type);
        		 startActivity(i);
	        	 cur.moveToNext();
	          }
		} else {
			Toast.makeText(this, "Authentication Failed", Toast.LENGTH_SHORT).show();
		}
    }

}
