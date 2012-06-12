package org.xproject.moony.data;

import org.xproject.moony.utils.LogHelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {

	public DatabaseHelper(Context context, String tableName, CursorFactory cursorFactory, int tableVersion) {
		super(context, tableName, cursorFactory, tableVersion);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		LogHelper.log(getClass(), Log.VERBOSE, "Creating Database...");
		ContentManager.onCreate(db);
	}

	protected void dropAll(SQLiteDatabase db) {
		LogHelper.log(getClass(), Log.VERBOSE, "Destroying Database data...");
		ContentManager.dropAll(db);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		LogHelper.log(getClass(), Log.VERBOSE, "Upgrading database from version " + oldVersion + " to " + newVersion + ", which will destroy all old data");

		dropAll(db);
		onCreate(db);
	}
}
