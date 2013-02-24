package com.android.studentapp;



import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.graphics.Color;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.PopupWindow;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;
public class ExamsList extends ListActivity {
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
	String student_number;
	String exam_type;
	String selNumber;
	Cursor cur = null;
	Cursor listCursor = null;
	String number;
	String name;
	private SQLiteDatabase newDB;
	SimpleCursorAdapter adapter = null;

	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
       // setContentView(R.layout.flist);
        this.setTitleColor(Color.BLUE);
        dbHelper = new DBHelper(this.getApplicationContext());
        newDB = dbHelper.getWritableDatabase();
        Integer i=getIntent().getExtras().getInt("id");
        student_number=i.toString();//"511";//(getIntent().getExtras().getInt("id"));
        System.out.println(getIntent().getExtras().getInt("id")+"-----------------");
        listCursor=this.displayData();
        this.startManagingCursor(listCursor);
        setListAdapter(new MyListAdapter(this,R.layout.examslist,listCursor,
		        	   new String[]{dbHelper.MKEY_ID,dbHelper.MKEY_TELUGU,dbHelper.MKEY_HINDI,dbHelper.MKEY_ENGLISH,
        		                    dbHelper.MKEY_MATHS,dbHelper.MKEY_SCIECNCE,dbHelper.MKEY_SOCIAL,dbHelper.MKEY_EXAMTYPE,dbHelper.MKEY_STUDENT},
		        	   new int[]{R.id.exams_entry}));
        registerForContextMenu(getListView());
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
        menu.add(0, v.getId(), 0, "Edit");
        menu.add(0, v.getId(), 0, "Delete");
        menu.add(0, v.getId(), 0, "Graph");
    }  
  
    @Override  
    public boolean onContextItemSelected(MenuItem item) {  
        if(item.getTitle()=="View"){function1(listCursor.getString(0));}  
        else if(item.getTitle()=="Edit"){function2(listCursor.getString(0));}  
        else if(item.getTitle()=="Delete"){function3(listCursor.getString(0));}
        else if(item.getTitle()=="Graph"){function4(listCursor.getString(0));}
        else {return false;}  
    return true;  
    }  
  
    public void function1(final String id){  
        Intent intent = new Intent(getApplicationContext(),MarksView.class);
		intent.putExtra("id", id);
    	startActivity(intent);
    	Toast.makeText(this, "view called"+id, Toast.LENGTH_SHORT).show();  
        
    }  
    public void function4(final String id){  
        Intent intent = new Intent(getApplicationContext(),MarksGraph.class);
		intent.putExtra("id", id);
    	startActivity(intent);
    	Toast.makeText(this, "graph called "+id, Toast.LENGTH_SHORT).show();  
    } 
    public void function2(final String id){  
        Intent intent = new Intent(getApplicationContext(),MarksEdit.class);
		intent.putExtra("id", id);
    	startActivity(intent);
    	Toast.makeText(this, "edit called"+id, Toast.LENGTH_SHORT).show();
        
    }  
    public void function3(final String id){  
        //Toast.makeText(this, "delete"+id, Toast.LENGTH_SHORT).show();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
    	builder.setTitle("Delete")
    	.setMessage("This marks will be deleted?")
    	.setIcon(android.R.drawable.ic_dialog_alert)
    	.setNegativeButton("Cancel", null)						//Do nothing on no
    	.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
    	    public void onClick(DialogInterface dialog, int which) {			      	
    	    	//Yes button clicked, do something
    	    	dbHelper.deleteUser(id);
    	    	Toast.makeText(ExamsList.this, "deleted",Toast.LENGTH_SHORT).show();
    	    	Intent intent = new Intent(ExamsList.this,ExamsList.class);
        		startActivity(intent);
    	    }
    	})
    	.show();
    }
    
          class MyListAdapter extends SimpleCursorAdapter implements OnClickListener{
            public MyListAdapter(Context context,int layuot,Cursor cursor,String[] from,int to[]) {
            super(context,layuot,cursor,from,to);
            }
            
            public void bindView(View view, Context context, Cursor cursor) 
           {       
            marksval = (TextView)view.findViewById(R.id.exams_entry);
            //student_number = cursor.getString(cursor.getColumnIndex("student_no"));
		    exam_type=cursor.getString(cursor.getColumnIndex("exam_type"));
            /*Cursor selCur=newDB.rawQuery("select * from student_details where number"+"=?",new String[] {student_number});

            if(selCur.moveToFirst()){
		          do{
		        	  number = selCur.getString(selCur.getColumnIndex("number"));
		        	  name = selCur.getString(selCur.getColumnIndex("name"));
		        	 
		        	 // selCur.moveToLast();
		          } while(selCur.moveToNext());
	          }*/
            marksval.setText(exam_type);
			/*marksval.setOnLongClickListener(new OnLongClickListener() {
				
				@Override
				public boolean onLongClick(View v) {
					// TODO Auto-generated method stub
				Intent intent = new Intent(MarksList.this,MarksView.class);
				intent.putExtra("id", marks);
            	startActivity(intent);
            		
                    return true;
				}
			});*/
        	  
           }
            
	        public void onClick(View v) {
		    CheckBox cb= (CheckBox) v;
	        Integer id = (Integer) cb.getTag();
	        
	        //for updating checked values
        	ContentValues values = new ContentValues();
            values.put(" selected", cb.isChecked() ? 1 : 0);
            newDB.update(tableName, values, dbHelper.MKEY_ID+"=?",new String[]{String.valueOf(id.intValue())});
            //for selecting only checked values
	        String args[]=new String[]{String.valueOf(1)};
            Cursor selCur=newDB.rawQuery("select * from markstable where selected"+"=?",
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
            }
               
       		
	       	 
	         public Cursor displayData() throws SQLiteException
			 {
			    cur= newDB.query(
						tableName,
						new String[] {dbHelper.MKEY_ID,dbHelper.MKEY_TELUGU,dbHelper.MKEY_HINDI,
								dbHelper.MKEY_ENGLISH,dbHelper.MKEY_MATHS,dbHelper.MKEY_SCIECNCE,
    		                    dbHelper.MKEY_SOCIAL,dbHelper.MKEY_EXAMTYPE,dbHelper.MKEY_STUDENT},
    		                    dbHelper.MKEY_STUDENT+"=?", new String[] {student_number},null, null, null);
								if(cur!=null)
								{
									System.out.println("Have Some Values.....................");
									cur.moveToFirst();
								}
			     return cur;
		     }
		
	         
	     	 public boolean onKeyDown(int keyCode, KeyEvent event) {
	     		// TODO Auto-generated method stub
	     		if(keyCode==KeyEvent.KEYCODE_BACK)
	     		{
	     			Intent back=new Intent(this,MarksEntry.class);
	     			startActivity(back);
	     		}
	     		return super.onKeyDown(keyCode, event);
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
	 			  menu.add(Menu.NONE, Menu.FIRST+1, Menu.NONE, "Enter Marks   ");
	 			  menu.add(Menu.NONE, Menu.FIRST+2, Menu.NONE, "Revert");
	 			  
	 		    }
	 			boolean applyMenuChoice(MenuItem item)
	 			{
	 				switch (item.getItemId()) {
	 				  case Menu.FIRST+1:
	 				   {
	 					   Toast.makeText(getApplicationContext(),"Enter Marks", Toast.LENGTH_SHORT).show();  
	 					   Intent i=new Intent(getApplicationContext(), MarksEntry.class);
	 					   startActivity(i);
	 				   }
	 				   break;
	 				  case Menu.FIRST+2:
	 				   {
	 					  Toast.makeText(getApplicationContext(),"Revert", Toast.LENGTH_SHORT).show();
	 					  Intent i=new Intent(getApplicationContext(), ManagerModules.class);
	 					  startActivity(i);
	 				   }
	 				  break;
	 				}
	 				return false;
	 			}   	 
	  
}
			                
     
  

    











