package com.android.studentapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

public class DBHelper extends SQLiteOpenHelper {
	public static final String DB_Name = "StudentAppl.db";
	public static final String DB_Path = "/data/data/com.android.studentapp/databases/";
	public SQLiteDatabase dbHelper;

	public String DBPath;
	public static String DBName = "StudentAppl.db";
	public static final int version = '1';
	public static Context currentContext;
	public static String tableName = "marks_details";
	public final String MKEY_ID = BaseColumns._ID;
	public final String MKEY_TELUGU = "telugu";
	public final String MKEY_HINDI = "hindi";
	public final String MKEY_ENGLISH = "english";
	public final String MKEY_MATHS = "maths";
	public final String MKEY_SCIECNCE= "science";
	public final String MKEY_SOCIAL= "social" ;
	public final String MKEY_TOTAL= "total";
	public final String MKEY_STUDENT="student_no";
	public final String MKEY_EXAMTYPE="exam_type";
	
	public static String studentTable = "student_details";
	public final String SKEY_ID = BaseColumns._ID;
	public final String SKEY_NAME = "name";
	public final String SKEY_NUM = "number";
	public final String SKEY_IMG = "image";
	public final String SKEY_DATE = "date";
	public final String SKEY_TIME = "time";
	
	public static String userTable = "user_details";
	public static final String MU_ID = "user_id";
	public static final String MU_NAME = "user_name";
	public static final String MU_PASSWORD = "user_unicode";
	public static final String MU_MAIL_ID = "user_mailid";
	public static final String MU_USER_TYPE="user_type";
    public static final String MU_PHONE = "phone_no";
	public static final String MU_ADDRESS = "address";
	
	
	public DBHelper(Context context) {
		super(context, DBName, null, version);
		dbHelper = getWritableDatabase();

	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL("CREATE TABLE IF NOT EXISTS " + userTable + " (" + MU_ID
				+ " INTEGER PRIMARY KEY AUTOINCREMENT, " + MU_NAME
				+ " VARCHAR, " + MU_PASSWORD + " VARCHAR(20), " + MU_MAIL_ID
				+ " VARCHAR, " + MU_PHONE + " TEXT, " + MU_ADDRESS
				+ " VARCHAR ,"+ MU_USER_TYPE + " VARCHAR );");

		db.execSQL("CREATE TABLE IF NOT EXISTS " + studentTable + "(" + SKEY_ID
				+ " INTEGER PRIMARY KEY AUTOINCREMENT," + SKEY_NAME
				+ " VARCHAR," + SKEY_NUM + " VARCHAR UNIQUE," + "" + SKEY_IMG 
				+ " BLOB," + SKEY_DATE + " VARCHAR," + "" + SKEY_TIME
				+ " VARCHAR);");

		db.execSQL("CREATE TABLE IF NOT EXISTS " + tableName + "(" + MKEY_ID
				+ " INTEGER PRIMARY KEY AUTOINCREMENT," + MKEY_TELUGU
				+ " NUMBER," + MKEY_HINDI + " NUMBER," +  MKEY_ENGLISH
				+ " NUMBER," + MKEY_MATHS + " NUMBER," + MKEY_SCIECNCE 
				+ " NUMBER," + MKEY_SOCIAL + " NUMBER," + MKEY_TOTAL 
				+ " NUMBER," + MKEY_EXAMTYPE + " VARCHAR," + "" 
				+ MKEY_STUDENT + " INTEGER NOT NULL, FOREIGN KEY ("+MKEY_STUDENT+") REFERENCES "+studentTable+"("+SKEY_ID+"));");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}
	public void insertMarksDetails(String telugu_marks, String hindi_marks,
			String english_marks, String maths_marks, String science_marks, String social_marks, Integer total_marks, String exam_type, String student_no) {

		// TODO Auto-generated method stub

		ContentValues cv = new ContentValues();
		cv.put(MKEY_TELUGU, telugu_marks);
		cv.put(MKEY_HINDI, hindi_marks);
		cv.put(MKEY_ENGLISH, english_marks);
		cv.put(MKEY_MATHS, maths_marks);
		cv.put(MKEY_SCIECNCE, science_marks);
		cv.put(MKEY_SOCIAL, social_marks);
		cv.put(MKEY_TOTAL, total_marks);
		cv.put(MKEY_EXAMTYPE, exam_type);
		cv.put(MKEY_STUDENT, student_no);
		dbHelper.insert(tableName, null, cv);
	}
	/*public Cursor getUserDetails(String username) {
		return dbHelper.query(MU_TABLE, new String[] { MU_NAME, MU_PASSWORD },
				MU_NAME + " LIKE '%" + username + "%'", null, null, null, null,
				null);
	}
	public Cursor displayMarksData(String username) {
		return dbHelper.query(tableName, new String[] { KEY_ID,KEY_NAME,KEY_NUM,KEY_IMG,
			      KEY_DATE,KEY_TIME },
			      KEY_NAME + " LIKE '%" + username + "%'", null, null, null, null,
				null);
	}*/
	

	public void openDataBase() throws SQLException {

		// Open the database
		String myPath = DB_Path + DB_Name;
		dbHelper = SQLiteDatabase.openDatabase(myPath, null,
				SQLiteDatabase.OPEN_READWRITE);

	}

	@Override
	public synchronized void close() {
		super.close();
		if (dbHelper != null)
			dbHelper.close();
	}

	public int deleteUser(String id) {
		// TODO Auto-generated method stub

		return dbHelper.delete(tableName, MKEY_ID + "= '" + id + "'", null);

	}
	public int updateMarks(String id,String telugu,String hindi,String english,String maths, String science, String social, Integer total){
		int i = 0;
		
		String query="update markstable set telugu ='"+telugu+"', hindi='"+hindi+"', english='"+english+"', maths='"+maths+"' ,science='"+science+"' ,social='"+social+"' ,total="+total+" where _id='"+id+"'";
		try {
			dbHelper.execSQL(query);
			i=1;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			i=0;
			e.printStackTrace();
		}
		
		return i;
	}
	
}
