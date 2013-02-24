package com.android.studentapp;



import java.io.BufferedInputStream;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.android.studentapp.DBHelper;
import com.android.studentapp.R;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class AddStudent extends Activity{
	public static final int ONE_ID = Menu.FIRST+1;
	public static final int TWO_ID = Menu.FIRST+2;
	private static final int SELECT_PICTURE = 1;
	DBHelper dbHelper;
    private String selectedImagePath;
	private String studentTable = DBHelper.studentTable;
    private SQLiteDatabase newDB;
	InputStream is;
	Cursor cursor;
	Uri selectedImageUri;
	BufferedInputStream bis;
	EditText name;
	EditText marks;
	Button browse;
	ImageView photo;
	int column_index;
	String filePath;	
	Bitmap myBitmap;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_student);
		setTitle("Add Student");
        this.setTitleColor(Color.BLUE); 
        dbHelper = new DBHelper(this.getApplicationContext());
        name=(EditText)findViewById(R.id.name);
    	marks=(EditText)findViewById(R.id.marks);
    	photo=(ImageView)findViewById(R.id.photo);
      }
       public void browseImage(View view)
	    {
	        Intent intent = new Intent();
            intent.setType("video/*, image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent,""), SELECT_PICTURE);
           
	    }
	    public void onActivityResult(int requestCode, int resultCode, Intent data) {
	    	if (resultCode == RESULT_OK) {
	        	if (requestCode == SELECT_PICTURE) {
	                selectedImageUri = data.getData();
	                selectedImagePath = getPath(selectedImageUri);
	                System.out.println("Image uri : " + selectedImageUri);
	                System.out.println("Image Path : " + selectedImagePath);
	                photo.setImageURI(selectedImageUri);
	            }
	        }
	    }
	    public String getPath(Uri uri) {
	         String[] projection = { MediaStore.Images.Media.DATA };
	         cursor = managedQuery(uri, null, null, null, null);
	         column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
	         cursor.moveToFirst();
	         return cursor.getString(column_index);
	    }
	    private void insertvalues()
		  {
		      String na=name.getText().toString();
		   	  String number=marks.getText().toString();
		   	  newDB = dbHelper.getWritableDatabase();
			  photo.setDrawingCacheEnabled(true);
			  ByteArrayOutputStream bs=new ByteArrayOutputStream();
			  Bitmap bitmap=Bitmap.createBitmap(photo.getDrawingCache(true));
			  bitmap.compress(Bitmap.CompressFormat.JPEG,100, bs);
			  
			  SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); 
			  SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss"); 
			  Date date = new Date();
			  Date time = new Date();
              ContentValues cv = new ContentValues();
		      cv.put(dbHelper.SKEY_NAME,na);
		      cv.put(dbHelper.SKEY_NUM,number);
		      cv.put(dbHelper.SKEY_IMG, bs.toByteArray());
		      cv.put(dbHelper.SKEY_DATE,dateFormat.format(date));
		      cv.put(dbHelper.SKEY_TIME,timeFormat.format(time));
		      newDB.insert(studentTable, null, cv);
		  } 
	    @Override
	    public void onCreateContextMenu(ContextMenu menu, View v,
		                                    ContextMenu.ContextMenuInfo menuInfo) {
		       populateMenu(menu);
		    }
		   @Override
		    public boolean onCreateOptionsMenu(Menu menu) {
		      populateMenu(menu);
		      return(super.onCreateOptionsMenu(menu));
		    }
		    @Override
		    public boolean onOptionsItemSelected(MenuItem item) {
		      return(applyMenuChoice(item) ||
		              super.onOptionsItemSelected(item));
		    }
		    private void populateMenu(Menu menu) {
				// TODO Auto-generated method stub
			  menu.add(Menu.NONE, ONE_ID, Menu.NONE, "Save");
			  menu.add(Menu.NONE, TWO_ID, Menu.NONE, "Cancel");
			  
		    }
			boolean applyMenuChoice(MenuItem item)
			{
				switch (item.getItemId()) {
				  case ONE_ID:
				   {
					   Toast.makeText(getApplicationContext(),"Save", Toast.LENGTH_SHORT).show();  
					   insertvalues();
				   }
				   break;
				  case TWO_ID:
				   {
					  Toast.makeText(getApplicationContext(),"Cancle", Toast.LENGTH_SHORT).show();
				   }
				  break;
				}
				return false;
			}

}
