package org.xproject.moony.data;

import org.xproject.moony.data.contentprovider.XProviderDescriptor;
import org.xproject.moony.data.objects.TaskDescriptor;

import android.content.UriMatcher;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

public class ContentManager {
	public static UriMatcher initialize(UriMatcher uriMatcher) {
		if(uriMatcher == null) {
			uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
		}
		uriMatcher.addURI(XProviderDescriptor.BASE_AUTHORITY, TaskDescriptor.PATH_MANY, TaskDescriptor.TASK_MULTIPLE_TOKEN);
		uriMatcher.addURI(XProviderDescriptor.BASE_AUTHORITY, TaskDescriptor.PATH_SINGLE, TaskDescriptor.TASK_SINGLE_TOKEN);
		
		return uriMatcher;
	}

	public static Uri getContentURI(String table) {
		if(TaskDescriptor.TABLE_NAME.equals(table)) {
			return TaskDescriptor.CONTENT_URI;
		} else {
			return null;
		}
	}
	
	public static void onCreate(SQLiteDatabase db) {
		TaskDescriptor.onCreate(db);
	}
	
	public static void dropAll(SQLiteDatabase db) {
		TaskDescriptor.dropAll(db);
	}
}
