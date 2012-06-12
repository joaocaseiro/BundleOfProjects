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
}
