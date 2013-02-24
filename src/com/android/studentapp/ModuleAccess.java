package com.android.studentapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class ModuleAccess extends Activity{
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.module_access);
        Button m_mod=(Button)findViewById(R.id.manager);
        Button t_mod = (Button)findViewById(R.id.teacher);
        Button s_mod = (Button)findViewById(R.id.student);
        final String user_type = getIntent().getStringExtra("user_type");
        
        
        	m_mod.setOnClickListener(new View.OnClickListener() {
    			
    			@Override
    			public void onClick(View v) {
    				// TODO Auto-generated method stub
				if(user_type.equalsIgnoreCase("manager")) {
					Intent i=new Intent(getApplicationContext(), ManagerModules.class);
	    			startActivity(i);
				} else {
					Toast.makeText(getApplicationContext(),"Sorry..! \n You Dont have permission to access.",1000).show();
				}
    			
    			}
    		});
       
        	t_mod.setOnClickListener(new View.OnClickListener() {
    			
    			@Override
    			public void onClick(View v) {
    				// TODO Auto-generated method stub
    				if(user_type.equalsIgnoreCase("teacher")) {
    					Intent i=new Intent(getApplicationContext(), StudentModule.class);
    	    			startActivity(i);
    				} else {
    					Toast.makeText(getApplicationContext(),"Sorry..!"+"\n You Dont have permission to access.",1000).show();
    				}
    			}
    		});
      
        	s_mod.setOnClickListener(new View.OnClickListener() {
    			
    			@Override
    			public void onClick(View v) {
    				// TODO Auto-generated method stub
    				if(user_type.equalsIgnoreCase("student")) {
    					Intent i=new Intent(getApplicationContext(), StudentModule.class);
    	    			startActivity(i);
    				} else {
    					Toast.makeText(getApplicationContext(),"Sorry..!"+"\n You Dont have permission to access.",1000).show();
    				}
    			}
    		});
        }
	}


