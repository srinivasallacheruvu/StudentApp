package com.android.studentapp;


import android.os.Bundle;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class MarksEntry extends Activity {
	SQLiteDatabase db;
	EditText telugu_marks,hindi_marks,english_marks,maths_marks,science_marks,social_marks,s_no;
	Spinner e_type;
	String exam_type;
	String snum;
	//int snum;
	DBHelper dbHelper;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.marksentry);
          telugu_marks=(EditText)findViewById(R.id.tmarks);
          hindi_marks=(EditText)findViewById(R.id.hmarks);
          english_marks=(EditText)findViewById(R.id.emarks);
          maths_marks=(EditText)findViewById(R.id.mmarks);
          science_marks=(EditText)findViewById(R.id.scmarks);
          social_marks=(EditText)findViewById(R.id.smarks);
          s_no=(EditText)findViewById(R.id.sno);
          e_type = (Spinner) findViewById(R.id.etype);
          e_type.setOnItemSelectedListener(new CustomOnItemSelectedListener());
          
          //e_type.getSelectedItem();
        Button b1=(Button)findViewById(R.id.submit);
        Button b2=(Button)findViewById(R.id.cancel);
        exam_type=String.valueOf(e_type.getSelectedItem());
        //snum=s_no.getText().toString();
        //sno = Integer.parseInt(snum);
        //final Integer snum=Integer.parseInt(s_no.getText().toString());
        dbHelper=new DBHelper(this.getApplicationContext());
        db = dbHelper.getWritableDatabase();
        //final TextView display_text=(TextView)findViewById(R.id.displayText);
        b1.setOnClickListener(new View.OnClickListener() {
        	@Override
			public void onClick(View v) {
        		// TODO Auto-generated method stub
        		snum=s_no.getText().toString();
        		String sql =  "select * from marks_details where exam_type='"+exam_type+"' and student_no="+snum;
        		System.out.println(sql);
        		Cursor cur=db.rawQuery(sql, null);
        		if(cur.getCount()!=0) {
        			Toast.makeText(getApplicationContext(), "Already Entered Exam Marks", Toast.LENGTH_SHORT).show();
        		} else {
        			checkValidation();
        		}
			}
		});
       
        b2.setOnClickListener(new View.OnClickListener() {
        	@Override
        	public void onClick(View arg0) {
        		// TODO Auto-generated method stub
        		 telugu_marks.setText("");
  				 hindi_marks.setText("");
  				 english_marks.setText("");
  				 maths_marks.setText("");
  				 science_marks.setText("");
  				 social_marks.setText("");
  				Intent intent = new Intent(MarksEntry.this,MarksList.class);
        		startActivity(intent);
        	}
        });
	}
	
	private void checkValidation() {
		System.out.println(String.valueOf(e_type.getSelectedItemPosition())+"--------------");
        if (s_no.getText().toString().equals("")) {
			Toast.makeText(getApplicationContext(),
					"Any one of the ," + "\n fileds can't be left blank",
					Toast.LENGTH_LONG).show();
        }
        else if (exam_type.equals("Select Exam Type")) {
			Toast.makeText(getApplicationContext(),
					"Select exam type ," + "\n fileds can't be left blank",
					Toast.LENGTH_LONG).show();
        }
        else if (telugu_marks.getText().toString().equals("")) {
			Toast.makeText(getApplicationContext(),
					"Any one of the ," + "\n fileds can't be left blank",
					Toast.LENGTH_LONG).show();
        }
        else if (hindi_marks.getText().toString().equals("")) {
			Toast.makeText(getApplicationContext(),
					"Any one of the ," + "\n fileds can't be left blank",
					Toast.LENGTH_LONG).show();
        }
        else if (english_marks.getText().toString().equals("")) {
			Toast.makeText(getApplicationContext(),
					"Any one of the ," + "\n fileds can't be left blank",
					Toast.LENGTH_LONG).show();
        }
        else if (maths_marks.getText().toString().equals("")) {
			Toast.makeText(getApplicationContext(),
					"Any one of the ," + "\n fileds can't be left blank",
					Toast.LENGTH_LONG).show();
        }
        else  if (science_marks.getText().toString().equals("")) {
			Toast.makeText(getApplicationContext(),
					"Any one of the ," + "\n fileds can't be left blank",
					Toast.LENGTH_LONG).show();
        }
        else if (social_marks.getText().toString().equals("")) {
			Toast.makeText(getApplicationContext(),
					"Any one of the ," + "\n fileds can't be left blank",
					Toast.LENGTH_LONG).show();
        }
        else{
        	Integer totm;
    		
            Integer tm=Integer.parseInt(telugu_marks.getText().toString());
    		Integer hm=Integer.parseInt(hindi_marks.getText().toString());
    		Integer em=Integer.parseInt(english_marks.getText().toString());
    		Integer mm=Integer.parseInt(maths_marks.getText().toString());
    		Integer sm=Integer.parseInt(science_marks.getText().toString());
    		Integer som=Integer.parseInt(social_marks.getText().toString());
    		totm=tm+hm+em+mm+sm+som;
    	    
    		
    		dbHelper.insertMarksDetails(telugu_marks.getText().toString(), hindi_marks.getText().toString(), english_marks.getText().toString(), maths_marks.getText().toString(), science_marks.getText().toString(), social_marks.getText().toString(), totm, exam_type, s_no.getText().toString()); 
    		dbHelper.close();
    		telugu_marks.setText("");
    		hindi_marks.setText("");
    		english_marks.setText("");
    		maths_marks.setText("");
    		science_marks.setText("");
    		social_marks.setText("");
    		e_type.setNextFocusUpId(0);
    		s_no.setText("");
    		Toast.makeText(getApplicationContext(),"Sucessfully Inserted",Toast.LENGTH_LONG).show();
        }
	}
	
	public class CustomOnItemSelectedListener implements OnItemSelectedListener {
		 
		  public void onItemSelected(AdapterView<?> parent, View view, int pos,long id) {
			/*Toast.makeText(parent.getContext(), 
				"OnItemSelectedListener : " + parent.getItemAtPosition(pos).toString(),
				Toast.LENGTH_SHORT).show();*/
			exam_type=parent.getItemAtPosition(pos).toString();
		  }
		 
		  @Override
		  public void onNothingSelected(AdapterView<?> arg0) {
			// TODO Auto-generated method stub
		  }
	}
}


