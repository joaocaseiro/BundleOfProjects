package org.xproject.moony.data.objects.task;

import org.xproject.moony.data.ContentManagerDescriptor;
import org.xproject.moony.data.contentprovider.XProviderDescriptor;
import org.xproject.moony.data.objects.BaseDescriptor;
import org.xproject.moony.data.objects.category.CategoryDescriptor;

import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.provider.BaseColumns;

public class TaskDescriptor extends BaseDescriptor {

	public static final int VERSION = 1;
	public static final String TABLE_NAME = "Table_Task";

	public static final String NAME = "Task_Name";
	public static final String VALUE = "Task_XP_Value";
	public static final String CATEGORY_ID = "Task_Category_Id";

	public static final Uri CONTENT_URI = XProviderDescriptor.BASE_URI.buildUpon().appendPath(TABLE_NAME).build();

	public static final String[] FIELDS = new String[] { ID, NAME, VALUE, CATEGORY_ID };
	
	@SuppressWarnings({"rawtypes"})
	public static final Class[] FIELDS_TYPE = new Class[] { Long.class, String.class, Integer.class, Long.class };

	public static final String PATH_MANY = TABLE_NAME;
	public static final String PATH_SINGLE = TABLE_NAME + "/*";

	public static final int TASK_MULTIPLE_TOKEN = ContentManagerDescriptor.TASK_MULTIPLE_TOKEN;
	public static final int TASK_SINGLE_TOKEN = ContentManagerDescriptor.TASK_SINGLE_TOKEN;

	public static final String CONTENT_TYPE_DIR = "vnd.android.cursor.dir/vnd." + TABLE_NAME + ".app";
	public static final String CONTENT_TYPE_ITEM = "vnd.android.cursor.item/vnd." + TABLE_NAME + ".app";

	public TaskDescriptor() {
		super();
	}

	@Override
	protected String getIdFieldName() {
		return BaseColumns._ID;
	}

	@Override
	public String getTableName() {
		return TABLE_NAME;
	}

	@Override
	protected Uri getContentUri() {
		return CONTENT_URI;
	}

	@Override
	protected String getContentType(int match) {
		switch (match) {
		case TASK_SINGLE_TOKEN:
			return CONTENT_TYPE_ITEM;
		case TASK_MULTIPLE_TOKEN:
			return CONTENT_TYPE_DIR;
		}
		return CONTENT_TYPE_ITEM;
	}

	public static void onCreate(SQLiteDatabase db) {
		db.execSQL("create table " + TABLE_NAME + " (" + 
				ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + 
				NAME + " TEXT, " + 
				VALUE + " INTEGER, " + 
				CATEGORY_ID + " INTEGER REFERENCES "+CategoryDescriptor.TABLE_NAME+"("+CategoryDescriptor.ID+") ON DELETE CASCADE);");
	}

	public static void dropAll(SQLiteDatabase db) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
	}

	@Override
	public String[] getFields() {
		return FIELDS;
	}
}
