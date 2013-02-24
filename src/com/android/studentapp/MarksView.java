package com.android.studentapp;

import android.os.Bundle;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.TextView;

public class MarksView extends Activity {
	SQLiteDatabase db;
	TextView telugu_marks,hindi_marks,english_marks,maths_marks,science_marks,social_marks;
	com.android.studentapp.DBHelper dbHelper;
	Cursor cur = null;
	String val;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.marksview);
		val=getIntent().getExtras().getString("id");
		Integer id_val=Integer.parseInt(val);
		  telugu_marks=(TextView)findViewById(R.id.tval);
          hindi_marks=(TextView)findViewById(R.id.hval);
          english_marks=(TextView)findViewById(R.id.eval);
          maths_marks=(TextView)findViewById(R.id.mval);
          science_marks=(TextView)findViewById(R.id.scval);
          social_marks=(TextView)findViewById(R.id.sval);
          telugu_marks.setText(getIntent().getExtras().getString("id"));
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
  		
	}

}
