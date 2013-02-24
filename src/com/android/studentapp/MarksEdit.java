package com.android.studentapp;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MarksEdit extends Activity {
	SQLiteDatabase db;
	EditText telugu_marks,hindi_marks,english_marks,maths_marks,science_marks,social_marks;
	com.android.studentapp.DBHelper dbHelper;
	Cursor cur = null;
	String val;
	 public static final int ONE_ID = Menu.FIRST+1;
		public static final int TWO_ID = Menu.FIRST+2;
	   
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.marksedit);
		val=getIntent().getExtras().getString("id");
		Integer id_val=Integer.parseInt(val);
		  telugu_marks=(EditText)findViewById(R.id.teval);
          hindi_marks=(EditText)findViewById(R.id.heval);
          english_marks=(EditText)findViewById(R.id.eeval);
          maths_marks=(EditText)findViewById(R.id.meval);
          science_marks=(EditText)findViewById(R.id.sceval);
          social_marks=(EditText)findViewById(R.id.seval);
          telugu_marks.setText(getIntent().getExtras().getString("id"));
          Button b1=(Button)findViewById(R.id.update);
          Button b2=(Button)findViewById(R.id.revert);
          String myPath = dbHelper.DB_Path + dbHelper.DB_Name;
          db=SQLiteDatabase.openDatabase(myPath, null,SQLiteDatabase.OPEN_READWRITE);
          cur=db.rawQuery("select * from marks_details where _id=?",new String[] {val});
        		  
  			/*	tableName,
  				new String[] {dbHelper.KEY_ID,dbHelper.KEY_TELUGU,dbHelper.KEY_HINDI,
  						      dbHelper.KEY_ENGLISH,dbHelper.KEY_MATHS,dbHelper.COLUMN_SELECTED,
  						      dbHelper.KEY_DATE,dbHelper.KEY_TIME},
  						    dbHelper.KEY_ID+"=?",new String[]{String.valueOf(marks_id.intValue())},null, null, null);*/
  						if(cur!=null)
  						{
  							 cur.moveToFirst();
	  				         while(!cur.isAfterLast())
	  	  			          {
	  	  			        	  String tel_mark=cur.getString(1);
	  	  			        	  telugu_marks.setText(tel_mark);
	  	  			        	  hindi_marks.setText(cur.getString(2));
	  	  			        	  english_marks.setText(cur.getString(3));
	  	  			        	  maths_marks.setText(cur.getString(4));
	  	  			        	  science_marks.setText(cur.getString(5));
	  	  			        	  social_marks.setText(cur.getString(6));
	  	  			        	  cur.moveToNext();
	  	  			          }
  						}
  						else {
  							
  						}
  						 b1.setOnClickListener(new View.OnClickListener() {
  				        	@Override
  							public void onClick(View v) {
  				        		// TODO Auto-generated method stub
  				        		Integer total_mark=Integer.parseInt(telugu_marks.getText().toString())+
  				        				Integer.parseInt(english_marks.getText().toString())+
  				        				Integer.parseInt(hindi_marks.getText().toString())+
  				        				Integer.parseInt(maths_marks.getText().toString())+
  				        				Integer.parseInt(science_marks.getText().toString())+
  				        				Integer.parseInt(social_marks.getText().toString());
  				        				
  				        		  DBHelper dbHelper=new DBHelper(getApplicationContext());
  				    			dbHelper.updateMarks(val,telugu_marks.getText().toString(), hindi_marks.getText().toString(), english_marks.getText().toString(), maths_marks.getText().toString(), science_marks.getText().toString(), social_marks.getText().toString(), total_mark); 
  				                dbHelper.close();
  				    			Toast.makeText(getApplicationContext(),
  				 						"Sucessfully Updated" ,
  				 						Toast.LENGTH_LONG).show();
  				    			Intent intent = new Intent(MarksEdit.this,MarksList.class);
  				        		startActivity(intent);

  							}
  						});
  				       
  				        b2.setOnClickListener(new View.OnClickListener() {
  				        	@Override
  				        	public void onClick(View arg0) {
  				        		// TODO Auto-generated method stub
  				        		Intent intent = new Intent(MarksEdit.this,MarksList.class);
  				        		startActivity(intent);
  				        	}
  				        });
	}
	
}
