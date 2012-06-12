package org.xproject.moony.data.contentresolver;

import org.xproject.moony.data.ContentManager;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

public class XResolver {

	private Context context;

	public XResolver(Context context) {
		this.context = context;
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

	public Cursor getEntry(String table, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
		Uri contentUri = getContentURI(table);
		return context.getContentResolver().query(contentUri, projection, selection, selectionArgs, sortOrder);
	}

	private Uri getContentURI(String table) {
		return ContentManager.getContentUri(table);
	}
}
