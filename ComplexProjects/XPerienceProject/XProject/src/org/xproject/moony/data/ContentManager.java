package org.xproject.moony.data;

import org.xproject.moony.data.contentprovider.XProviderDescriptor;
import org.xproject.moony.data.objects.BaseDescriptor;
import org.xproject.moony.data.objects.category.CategoryDescriptor;
import org.xproject.moony.data.objects.task.TaskDescriptor;

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
		
		uriMatcher.addURI(XProviderDescriptor.BASE_AUTHORITY, CategoryDescriptor.PATH_MANY, CategoryDescriptor.CATEGORY_MULTIPLE_TOKEN);
		uriMatcher.addURI(XProviderDescriptor.BASE_AUTHORITY, CategoryDescriptor.PATH_SINGLE, CategoryDescriptor.CATEGORY_SINGLE_TOKEN);
		
		return uriMatcher;
	}
	
	public static BaseDescriptor getCorrectObject(int match) {
		switch (match) {
		case TaskDescriptor.TASK_MULTIPLE_TOKEN:
		case TaskDescriptor.TASK_SINGLE_TOKEN:
			return new TaskDescriptor();
		case CategoryDescriptor.CATEGORY_MULTIPLE_TOKEN:
		case CategoryDescriptor.CATEGORY_SINGLE_TOKEN:
			return new CategoryDescriptor();
		default:
			throw new UnsupportedOperationException("No match found.");
		}
	}

	public static Uri getContentUri(String tableName) {
		if (TaskDescriptor.TABLE_NAME.equals(tableName)) {
			return TaskDescriptor.CONTENT_URI;
		} else if (CategoryDescriptor.TABLE_NAME.equals(tableName)) {
			return CategoryDescriptor.CONTENT_URI;
		} else {
			throw new UnsupportedOperationException("Table " + tableName + " was not found.");
		}
	}
	
	public static void onCreate(SQLiteDatabase db) {
		CategoryDescriptor.onCreate(db);
		TaskDescriptor.onCreate(db);
	}
	
	public static void dropAll(SQLiteDatabase db) {
		CategoryDescriptor.dropAll(db);
		TaskDescriptor.dropAll(db);
	}
}
