package my.pretty.project.utils.database.contentprovider;

import my.pretty.project.utils.LogHelper;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.net.Uri;
import android.util.Log;

public class Database {
	private final Context context;

	public Database(Context context) {
		this.context = context;
	}

	@Deprecated
	public boolean isOpen() {
		return true;
	}

	@Deprecated
	public Database open() throws SQLException {
		LogHelper.log(Database.class, Log.INFO, " - db.open()");
		return this;
	}
	
	@Deprecated
	public Database openReadOnly() throws SQLException {
		LogHelper.log(Database.class, Log.INFO, " - db.open()");
		return this;
	}

	@Deprecated
	public void close() {
		LogHelper.log(Database.class, Log.INFO, " - db.close()");
	}

	public long insertEntry(String table, ContentValues values) {
		Uri contentUri = getContentURI(table);
		ContentResolver resolver = context.getContentResolver();
		Uri uri = resolver.insert(contentUri, values);
		long id = Long.parseLong(uri.getPathSegments().get(1));
		return id;
	}

	public long updateEntry(String table, ContentValues values, String whereClause) {
		Uri contentUri = getContentURI(table);
		ContentResolver resolver = context.getContentResolver();
		int count = resolver.update(contentUri, values, whereClause, null);
		return count;
	}
	
	public long updateEntry(String table, ContentValues values, String selection, String[] selectionArgs) {
		Uri contentUri = getContentURI(table);
		ContentResolver resolver = context.getContentResolver();
		int count = resolver.update(contentUri, values, selection, selectionArgs);
		return count;
	}

	public boolean removeEntry(String table, String whereClause) {
		Uri contentUri = getContentURI(table);
		ContentResolver resolver = context.getContentResolver();
		int count = resolver.delete(contentUri, whereClause, null);
		return count > 0;
	}
	
	public int removeEntry(String table, String whereClause, String[] argsClause) {
		Uri contentUri = getContentURI(table);
		ContentResolver resolver = context.getContentResolver();
		int count = resolver.delete(contentUri, whereClause, argsClause);
		return count;
	}

	@Deprecated
	public Cursor getAllEntriesRawQuery(String sql) {
		//throw new DeprecatedMethodException();
		return null;
	}

	/**
	 * It is using instanceof to know if it is an activity or not and returns a managed or not cursor, accordingly. Might work because Services dont need to worry with 
	 * lifecycle (normally) but requires further testing (alternative is to pass an activity to this class, but then it cant be used by services)  
	 * 
	 * @return a managed or not cursor, depending on wether it is an instanceof Activity
	 */
	public Cursor getEntry(String table, String[] fields, String whereClause) {
		Uri contentUri = getContentURI(table);
		if(context instanceof Activity) {
			return ((Activity)context).managedQuery(contentUri, fields, whereClause, null, null);
		} else {
			LogHelper.log(Database.class, Log.DEBUG, "contentUri: "+contentUri);
			LogHelper.log(Database.class, Log.DEBUG, "fields: "+fields);
			LogHelper.log(Database.class, Log.DEBUG, "whereClause: "+whereClause);
			LogHelper.log(Database.class, Log.DEBUG, "context: "+context);
			return context.getContentResolver().query(contentUri, fields, whereClause, null, null);
		}
	}
	
	public Cursor getEntry(String table, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
		Uri contentUri = getContentURI(table);
		if(context instanceof Activity) {
			return ((Activity)context).managedQuery(contentUri, projection, selection, selectionArgs, sortOrder);
		} else {
			return context.getContentResolver().query(contentUri, projection, selection, selectionArgs, sortOrder);
		}
	}
	
	private Uri getContentURI(String table) {
		return DescriptorManager.getContentUri(table);
	}

	@Deprecated
	public void beginTransaction() {
		//throw new DeprecatedMethodException();
	}

	@Deprecated
	public void setTransactionSuccessful() {
		//throw new DeprecatedMethodException();
	}

	@Deprecated
	public void endTransaction() {
		//throw new DeprecatedMethodException();
	}

}
