package com.android.studentapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ManagerModules extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.manager_modules);
        Button s_reg=(Button)findViewById(R.id.studentregistration);
        Button e_mar=(Button)findViewById(R.id.entermarks);
        
        s_reg.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			Intent i= new Intent(getApplicationContext(), AddStudent.class);
			startActivity(i);
			}
		});
		
        e_mar.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i=new Intent(getApplicationContext(), MarksList.class);
				startActivity(i);
			}
		});
	}

}
