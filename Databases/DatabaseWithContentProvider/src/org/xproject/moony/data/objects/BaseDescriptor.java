package org.xproject.moony.data.objects;

import android.net.Uri;
import android.provider.BaseColumns;

public abstract class BaseDescriptor {

	public static final String ID = BaseColumns._ID;

	public BaseDescriptor() {
	}

	public String getType(Uri uri, int match) {
		return getContentType(match);
	}

	protected abstract String getIdFieldName();

	public abstract String getTableName();

	protected abstract Uri getContentUri();

	protected abstract String getContentType(int match);

	public abstract String[] getFields();

	public static BaseDescriptor getCorrectObject(int match) {
		switch (match) {
		case TaskDescriptor.TASK_MULTIPLE_TOKEN:
		case TaskDescriptor.TASK_SINGLE_TOKEN:
			return new TaskDescriptor();
		default:
			throw new UnsupportedOperationException("No match found.");
		}
	}

	public static Uri getContentUri(String tableName) {
		if (TaskDescriptor.TABLE_NAME.equals(tableName)) {
			return TaskDescriptor.CONTENT_URI;
		} else {
			throw new UnsupportedOperationException("Table " + tableName + " was not found.");
		}
	}
}
