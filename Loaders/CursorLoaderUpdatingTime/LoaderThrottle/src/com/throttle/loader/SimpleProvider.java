package com.throttle.loader;

import java.util.HashMap;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

import com.throttle.loader.LoaderThrottle.DatabaseHelper;
import com.throttle.loader.LoaderThrottle.MainTable;

/**
 * A very simple implementation of a content provider.
 */
public class SimpleProvider extends ContentProvider {
	// A projection map used to select columns from the database
	private final HashMap<String, String> mNotesProjectionMap;
	// Uri matcher to decode incoming URIs.
	private final UriMatcher mUriMatcher;

	// The incoming URI matches the main table URI pattern
	private static final int MAIN = 1;
	// The incoming URI matches the main table row ID URI pattern
	private static final int MAIN_ID = 2;

	// Handle to a new DatabaseHelper.
	private DatabaseHelper mOpenHelper;

	/**
	 * Global provider initialization.
	 */
	public SimpleProvider() {
		// Create and initialize URI matcher.
		mUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
		mUriMatcher.addURI(LoaderThrottle.AUTHORITY, MainTable.TABLE_NAME, MAIN);
		mUriMatcher.addURI(LoaderThrottle.AUTHORITY, MainTable.TABLE_NAME + "/#", MAIN_ID);

		// Create and initialize projection map for all columns. This is
		// simply an identity mapping.
		mNotesProjectionMap = new HashMap<String, String>();
		mNotesProjectionMap.put(MainTable._ID, MainTable._ID);
		mNotesProjectionMap.put(MainTable.COLUMN_NAME_DATA, MainTable.COLUMN_NAME_DATA);
	}

	/**
	 * Perform provider creation.
	 */
	@Override
	public boolean onCreate() {
		mOpenHelper = new DatabaseHelper(getContext());
		// Assumes that any failures will be reported by a thrown exception.
		return true;
	}

	/**
	 * Handle incoming queries.
	 */
	@Override
	public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

		// Constructs a new query builder and sets its table name
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
		qb.setTables(MainTable.TABLE_NAME);

		switch (mUriMatcher.match(uri)) {
		case MAIN:
			// If the incoming URI is for main table.
			qb.setProjectionMap(mNotesProjectionMap);
			break;

		case MAIN_ID:
			// The incoming URI is for a single row.
			qb.setProjectionMap(mNotesProjectionMap);
			qb.appendWhere(MainTable._ID + "=?");
			selectionArgs = DatabaseUtils.appendSelectionArgs(selectionArgs, new String[] { uri.getLastPathSegment() });
			break;

		default:
			throw new IllegalArgumentException("Unknown URI " + uri);
		}

		if (TextUtils.isEmpty(sortOrder)) {
			sortOrder = MainTable.DEFAULT_SORT_ORDER;
		}

		SQLiteDatabase db = mOpenHelper.getReadableDatabase();

		Cursor c = qb.query(db, projection, selection, selectionArgs, null /*
																			 * no
																			 * group
																			 */, null /*
																					 * no
																					 * filter
																					 */, sortOrder);

		c.setNotificationUri(getContext().getContentResolver(), uri);
		return c;
	}

	/**
	 * Return the MIME type for an known URI in the provider.
	 */
	@Override
	public String getType(Uri uri) {
		switch (mUriMatcher.match(uri)) {
		case MAIN:
			return MainTable.CONTENT_TYPE;
		case MAIN_ID:
			return MainTable.CONTENT_ITEM_TYPE;
		default:
			throw new IllegalArgumentException("Unknown URI " + uri);
		}
	}

	/**
	 * Handler inserting new data.
	 */
	@Override
	public Uri insert(Uri uri, ContentValues initialValues) {
		if (mUriMatcher.match(uri) != MAIN) {
			// Can only insert into to main URI.
			throw new IllegalArgumentException("Unknown URI " + uri);
		}

		ContentValues values;

		if (initialValues != null) {
			values = new ContentValues(initialValues);
		} else {
			values = new ContentValues();
		}

		if (values.containsKey(MainTable.COLUMN_NAME_DATA) == false) {
			values.put(MainTable.COLUMN_NAME_DATA, "");
		}

		SQLiteDatabase db = mOpenHelper.getWritableDatabase();

		long rowId = db.insert(MainTable.TABLE_NAME, null, values);

		// If the insert succeeded, the row ID exists.
		if (rowId > 0) {
			Uri noteUri = ContentUris.withAppendedId(MainTable.CONTENT_ID_URI_BASE, rowId);
			getContext().getContentResolver().notifyChange(noteUri, null);
			return noteUri;
		}

		throw new SQLException("Failed to insert row into " + uri);
	}

	/**
	 * Handle deleting data.
	 */
	@Override
	public int delete(Uri uri, String where, String[] whereArgs) {
		SQLiteDatabase db = mOpenHelper.getWritableDatabase();
		String finalWhere;

		int count;

		switch (mUriMatcher.match(uri)) {
		case MAIN:
			// If URI is main table, delete uses incoming where clause and args.
			count = db.delete(MainTable.TABLE_NAME, where, whereArgs);
			break;

		// If the incoming URI matches a single note ID, does the delete based
		// on the
		// incoming data, but modifies the where clause to restrict it to the
		// particular note ID.
		case MAIN_ID:
			// If URI is for a particular row ID, delete is based on incoming
			// data but modified to restrict to the given ID.
			finalWhere = DatabaseUtils.concatenateWhere(MainTable._ID + " = " + ContentUris.parseId(uri), where);
			count = db.delete(MainTable.TABLE_NAME, finalWhere, whereArgs);
			break;

		default:
			throw new IllegalArgumentException("Unknown URI " + uri);
		}

		getContext().getContentResolver().notifyChange(uri, null);

		return count;
	}

	/**
	 * Handle updating data.
	 */
	@Override
	public int update(Uri uri, ContentValues values, String where, String[] whereArgs) {
		SQLiteDatabase db = mOpenHelper.getWritableDatabase();
		int count;
		String finalWhere;

		switch (mUriMatcher.match(uri)) {
		case MAIN:
			// If URI is main table, update uses incoming where clause and args.
			count = db.update(MainTable.TABLE_NAME, values, where, whereArgs);
			break;

		case MAIN_ID:
			// If URI is for a particular row ID, update is based on incoming
			// data but modified to restrict to the given ID.
			finalWhere = DatabaseUtils.concatenateWhere(MainTable._ID + " = " + ContentUris.parseId(uri), where);
			count = db.update(MainTable.TABLE_NAME, values, finalWhere, whereArgs);
			break;

		default:
			throw new IllegalArgumentException("Unknown URI " + uri);
		}

		getContext().getContentResolver().notifyChange(uri, null);

		return count;
	}
}