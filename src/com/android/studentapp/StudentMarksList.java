package com.android.studentapp;



import android.os.Bundle;
import android.app.ListActivity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.view.ContextMenu;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class StudentMarksList extends ListActivity {

	DBHelper dbHelper;
	TextView numberval;
	TextView marksval;
	Button viewDet;
	String mark;
	String teng;
	String from[];
	int to[];
	int imageBackground;
	private String tableName = DBHelper.tableName;
	String marks;
	String selNumber;
	Cursor cur = null;
	Cursor listCursor = null;
	String number;
	String name;
	private SQLiteDatabase newDB;
	SimpleCursorAdapter adapter = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.student_marks_list);
		
		dbHelper = new DBHelper(this.getApplicationContext());
        newDB = dbHelper.getWritableDatabase();
        
        listCursor=this.displayData();
        this.startManagingCursor(listCursor);
        
        
        
        
        
       // setListAdapter(new MyListAdapter(this, R.layout.markslist, listCursor, from, to));
        
        setListAdapter(new MyListAdapter(this,R.layout.markslist,listCursor,
		        	   new String[]{dbHelper.MKEY_ID,dbHelper.MKEY_TELUGU,dbHelper.MKEY_HINDI,dbHelper.MKEY_ENGLISH,
        		                    dbHelper.MKEY_MATHS,dbHelper.MKEY_SCIECNCE,dbHelper.MKEY_SOCIAL,dbHelper.MKEY_EXAMTYPE,dbHelper.MKEY_STUDENT},
		        	   new int[]{R.id.marks_entry}));
        registerForContextMenu(getListView());
       // registerForContextMenu(getListView());
        getListView().setOnItemClickListener(new OnItemClickListener() {
					
        			@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						// TODO Auto-generated method stub
        				//Cursor cursor = (Cursor) listView.getItemAtPosition(position);
        				 
        				listCursor.moveToPosition(position);
        		          int rowId = listCursor.getInt(listCursor.getColumnIndex("_id"));
        		          //Uri outURI = Uri.parse(data.toString() + rowId);
        		        Intent intent = new Intent(getApplicationContext(),MarksView.class);
        					intent.putExtra("id", listCursor.getString(0));
        	            	startActivity(intent);
        		            
					}
        });
	}

	@Override  
    public void onCreateContextMenu(ContextMenu menu, View v,ContextMenuInfo menuInfo) {  
    super.onCreateContextMenu(menu, v, menuInfo);  
        menu.setHeaderTitle("Options");  
        menu.add(0, v.getId(), 0, "View"); 
        menu.add(0, v.getId(), 0, "Graph");
    }  
  
    @Override  
    public boolean onContextItemSelected(MenuItem item) {  
        if(item.getTitle()=="View"){function1(listCursor.getInt(listCursor.getColumnIndex("student_no")));}  
        
        else if(item.getTitle()=="Graph"){function4(listCursor.getString(0));}
        else {return false;}  
    return true;  
    }  
  
    public void function1(final int id){  
        Intent intent = new Intent(getApplicationContext(),StudentExamList.class);
		//intent.putExtra("id", id);
		intent.putExtra("id", id);
    	startActivity(intent);
    	Toast.makeText(this, "view called"+id, Toast.LENGTH_SHORT).show();  
        
    }
    public void function4(final String id){  
        Intent intent = new Intent(getApplicationContext(),ExamResultGraph.class);
		intent.putExtra("id", id);
    	startActivity(intent);
    	Toast.makeText(this, "graph called "+id, Toast.LENGTH_SHORT).show();  
    }
   
    class MyListAdapter extends SimpleCursorAdapter implements OnClickListener {
    	public MyListAdapter(Context context, int layout, Cursor cursor, String[] from, int[] to) {
    		super(context, layout, cursor, from, to);
    	}

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			CheckBox cb= (CheckBox) v;
	        Integer id = (Integer) cb.getTag();
	        
	        //for updating checked values
        	ContentValues values = new ContentValues();
            values.put(" selected", cb.isChecked() ? 1 : 0);
            newDB.update(tableName, values, dbHelper.MKEY_ID+"=?",new String[]{String.valueOf(id.intValue())});
            //for selecting only checked values
	        String args[]=new String[]{String.valueOf(1)};
            Cursor selCur=newDB.rawQuery("select * from marks_details where selected"+"=?",
            		                      args);
             if(selCur.moveToFirst()){
		          do{
		        	  String  selName = selCur.getString(selCur.getColumnIndex("telugu"));
		        	  selNumber = selCur.getString(selCur.getColumnIndex("hindi"));
		        	  String selEmail = selCur.getString(selCur.getColumnIndex("english"));
		        	  System.out.println("selected names"+selName);
		        	  System.out.println("selected number"+selNumber);
		        	  System.out.println("selected number"+selEmail);
		        	} while(selCur.moveToNext());
		          }
		}
		public void bindView(View view, Context context, Cursor cursor) 
        {       
         marksval = (TextView)view.findViewById(R.id.marks_entry);
         marks = cursor.getString(cursor.getColumnIndex("student_no"));
		    
         Cursor selCur=newDB.rawQuery("select * from student_details where number"+"=?",new String[] {marks});

         if(selCur.moveToFirst()){
		          do{
		        	  number = selCur.getString(selCur.getColumnIndex("number"));
		        	  name = selCur.getString(selCur.getColumnIndex("name"));
		        	  marksval.setText(number+ "     " +name);
		        	  selCur.moveToLast();
		          } while(selCur.moveToNext());
	          }
        }
    }
               
       		
	       	 
	         public Cursor displayData() throws SQLiteException
			 {
			    cur= newDB.query(
						tableName,
						new String[] {dbHelper.MKEY_ID,dbHelper.MKEY_TELUGU,dbHelper.MKEY_HINDI,
								dbHelper.MKEY_ENGLISH,dbHelper.MKEY_MATHS,dbHelper.MKEY_SCIECNCE,
    		                    dbHelper.MKEY_SOCIAL,dbHelper.MKEY_EXAMTYPE,dbHelper.MKEY_STUDENT},
				                      null, null,dbHelper.MKEY_STUDENT, null, null);
								if(cur!=null)
								{
									cur.moveToFirst();
								}
			     return cur;
		     }
		
	         
	     	  
	     	 
	     	 @Override
	     	public boolean onCreateOptionsMenu(Menu menu) {
	     		// TODO Auto-generated method stub
	     		 populateMenu(menu);
	     		return super.onCreateOptionsMenu(menu);
	     	}
	 		    @Override
	 		    public boolean onOptionsItemSelected(MenuItem item) {
	 		      return(applyMenuChoice(item) ||
	 		              super.onOptionsItemSelected(item));
	 		    }
	 		    private void populateMenu(Menu menu) {
	 				// TODO Auto-generated method stub
	 			  menu.add(Menu.NONE, Menu.FIRST+2, Menu.NONE, "Revert");
	 			  
	 		    }
	 			boolean applyMenuChoice(MenuItem item)
	 			{
	 				switch (item.getItemId()) {
	 				  
	 				  case Menu.FIRST+2:
	 				   {
	 					  Toast.makeText(getApplicationContext(),"Revert", Toast.LENGTH_SHORT).show();
	 					  Intent i=new Intent(getApplicationContext(), StudentModule.class);
	 					  startActivity(i);
	 				   }
	 				  break;
	 				}
	 				return false;
	 			}   
	 			
	

}
