package org.xproject.moony.data.contentprovider;

import java.util.Arrays;
import java.util.HashSet;

import org.xproject.moony.data.ContentManager;
import org.xproject.moony.data.DatabaseHelper;
import org.xproject.moony.data.objects.BaseDescriptor;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

/**
 * Class of the content provider. Uses a SQLiteDatabase below to store the data
 * 
 * @author jcaseiro
 * 
 */
public class XProvider extends ContentProvider {

	private static final String DATABASE_NAME = "moony.db";
	private static final int DATABASE_VERSION = 9;
	private static final UriMatcher uriMatcher;
	private DatabaseHelper db = null;
	private Context context = null;

	// must not take arguments!!
	public XProvider() {
	}

	@Override
	public boolean onCreate() {
		context = getContext();
		db = new DatabaseHelper(context, DATABASE_NAME, null, DATABASE_VERSION);
		return false;
	}

	/**
	 * @return the object that will be used by the provider to interface with
	 *         the database
	 * 
	 * @param match
	 *            - the id of the match as it was registered in the uriMatcher
	 */
	private BaseDescriptor getCorrectObject(int match) {
		return BaseDescriptor.getCorrectObject(match);
	}

	@Override
	public String getType(Uri uri) {
		final int match = uriMatcher.match(uri);
		BaseDescriptor bd = getCorrectObject(match);
		return bd.getType(uri, match);
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		final int match = uriMatcher.match(uri);
		SQLiteDatabase providerDB = db.getWritableDatabase();
		BaseDescriptor bd = getCorrectObject(match);

		long id = providerDB.insert(bd.getTableName(), "", values);
		if (id > 0) {
			Uri modifiedUri = ContentUris.withAppendedId(uri, id);
			context.getContentResolver().notifyChange(modifiedUri, null);
			return modifiedUri;
		}
		throw new SQLException("Failed to insert row into " + uri + ". Row was: " + values);
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		final int match = uriMatcher.match(uri);
		SQLiteDatabase providerDB = db.getWritableDatabase();
		BaseDescriptor bd = getCorrectObject(match);

		int count = 0;

		count = providerDB.delete(bd.getTableName(), selection, selectionArgs);
		if (count > 0) {
			context.getContentResolver().notifyChange(uri, null);
			return count;
		}
		throw new SQLException("Failed to delete row with uri: " + uri + ". Selection was: " + selection);
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
		final int match = uriMatcher.match(uri);
		SQLiteDatabase providerDB = db.getReadableDatabase();
		BaseDescriptor bd = getCorrectObject(match);

		checkColumns(bd, projection);

		SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
		builder.setTables(bd.getTableName());
		Cursor cursor = builder.query(providerDB, projection, selection, selectionArgs, null, null, sortOrder);
		cursor.setNotificationUri(context.getContentResolver(), uri);
		return cursor;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
		final int match = uriMatcher.match(uri);
		SQLiteDatabase providerDB = db.getWritableDatabase();
		BaseDescriptor bd = getCorrectObject(match);
		int count = 0;
		count = (int) providerDB.update(bd.getTableName(), values, selection, selectionArgs);

		if (count > 0) {
			context.getContentResolver().notifyChange(uri, null);
			return count;
		}
		throw new SQLException("Failed to update row with uri: " + uri + ". Selection was: " + selection);
	}

	private void checkColumns(BaseDescriptor bd, String[] projection) {
		if (projection != null) {
			HashSet<String> requestedColumns = new HashSet<String>(Arrays.asList(projection));
			HashSet<String> availableColumns = new HashSet<String>(Arrays.asList(bd.getFields()));

			if (!availableColumns.containsAll(requestedColumns)) {
				throw new IllegalArgumentException("Unknown columns in projection");
			}
		}
	}

	static {
		uriMatcher = ContentManager.initialize(null);
	}
}