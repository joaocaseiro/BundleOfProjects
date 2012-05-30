package my.pretty.project.utils.database.localsqldatabase;

import my.pretty.project.utils.LogHelper;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.util.Log;

public class Database {
	private static final String DATABASE_NAME = "com.grupoatwork.domotics.android.app.database";

	private static final int DATABASE_VERSION = 12;

	private final Context context;

	private MyDbHelper dbHelper = null;

	private SQLiteDatabase db;
	
	public Database(Context _context) {
		context = _context;
		dbHelper = new MyDbHelper(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	public boolean isOpen() {
		return db != null && db.isOpen();
	}

	public synchronized Database open() throws SQLException {
		if (db == null)
			db = dbHelper.getWritableDatabase();
		return this;
	}

	public synchronized void close() {
		if (db != null)
			db.close();
		db = null;
	}

	public long insertEntry(String table, ContentValues values) {
		return db.insert(table, null, values);
	}

	public long updateEntry(String table, ContentValues values, String whereClause) {
		return db.update(table, values, whereClause, null);
	}

	public boolean removeEntry(String table, String whereClause) {
		return db.delete(table, whereClause, null) > 0;
	}

	public Cursor getAllEntriesRawQuery(String sql) {
		return db.rawQuery(sql, null);
	}

	public Cursor getEntry(String table, String[] fields, String whereClause) {
		return db.query(table, fields, whereClause, null, null, null, null);
	}
	
	public Cursor getEntry(String table, String[] fields, String whereClause, String[] whereArgs, String orderBy) {
		return db.query(table, fields, whereClause, whereArgs, null, null, orderBy);
	}

	public static class MyDbHelper extends SQLiteOpenHelper {

		public MyDbHelper(Context context, String name, CursorFactory factory, int version) {
			super(context, name, factory, version);
		}

		@Override
		public void onCreate(SQLiteDatabase _db) {
			LogHelper.log(Database.class, Log.DEBUG, "Creating database...");
			init(_db);
		}

		protected void init(SQLiteDatabase _db) {
			LogHelper.log(Database.class, Log.DEBUG, "Initializing database...");
			_db.execSQL(AndroidDatabaseLocationDescriptor.getCreatorDatabaseSql());
		}

		protected void dropAll(SQLiteDatabase _db) {
			LogHelper.log(Database.class, Log.DEBUG, "Droping database...");
			_db.execSQL(AndroidDatabaseLocationDescriptor.getDestroyerDatabaseSql());			
		}

		@Override
		public void onUpgrade(SQLiteDatabase _db, int _oldVersion, int _newVersion) {
			LogHelper.log(Database.class, Log.DEBUG, "Upgrading from version " + _oldVersion + " to " + _newVersion + ", which will destroy all old data.");
			dropAll(_db);
			onCreate(_db);
		}
	}
	
	public void beginTransaction() {
		db.beginTransaction();
	}

	public void setTransactionSuccessful() {
		db.setTransactionSuccessful();
	}

	public void endTransaction() {
		db.endTransaction();
	}
	
	public Context getContext() {
		return context;
	}
}
