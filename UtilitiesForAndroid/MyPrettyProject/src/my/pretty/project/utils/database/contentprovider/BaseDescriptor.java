package my.pretty.project.utils.database.contentprovider;

import my.pretty.project.utils.database.scriptfilereader.Database.MyDbHelper;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.provider.BaseColumns;
import android.text.TextUtils;

public abstract class BaseDescriptor {

	public static final String ID = BaseColumns._ID;
	private boolean many;
	
	public BaseDescriptor(boolean many) {
		this.many = many;
	}
	
	public String getType(Uri uri) {
		if(many) {
			return getContentTypeDir();
		} else {
			return getContentItemType();
		}
	}
	
	public Uri insert(Uri uri, ContentValues values, MyDbHelper db, Context context) {
		SQLiteDatabase database = db.getWritableDatabase();
		long id = database.insert(getTableName(), null, values);
		context.getContentResolver().notifyChange(uri, null);
		return getContentUri().buildUpon().appendPath(String.valueOf(id)).build();
	}

	public int delete(Uri uri, String selection, String[] selectionArgs, MyDbHelper db, Context context) {
		int count = 0;
		SQLiteDatabase database = db.getWritableDatabase();
		
		if(many) {
			count = database.delete(getTableName(), selection, selectionArgs);
		} else {
			String id = uri.getPathSegments().get(1);
			count = database.delete(getTableName(), getIdFieldName() + " = " + id + (!TextUtils.isEmpty(selection) ? 
					" AND (" + selection + ')' : ""), selectionArgs);
		}
		
		context.getContentResolver().notifyChange(uri, null);
		return count;
	}

	public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder, MyDbHelper db, Context context) {
		SQLiteDatabase database = db.getWritableDatabase();
		
		SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
		builder.setTables(getTableName());
		Cursor cursor = builder.query(database, projection, selection, selectionArgs, null, null, sortOrder);
		return cursor;
	}
	
	public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs, MyDbHelper db, Context context) {
		int count = 0;
		SQLiteDatabase database = db.getWritableDatabase();
		
		if(many) {
			count = (int) database.update(getTableName(), values, selection, selectionArgs);
		} else {
			String id = uri.getPathSegments().get(1);
			count = (int) database.update(getTableName(), values, getIdFieldName() + " = " + id + 
					(!TextUtils.isEmpty(selection) ? " AND (" + selection + ')' : ""), selectionArgs);
		}
		
		return count;
	}
	
	protected abstract String getIdFieldName();
	protected abstract String getTableName();
	protected abstract Uri getContentUri();
	protected abstract String getContentItemType();
	protected abstract String getContentTypeDir();

}
