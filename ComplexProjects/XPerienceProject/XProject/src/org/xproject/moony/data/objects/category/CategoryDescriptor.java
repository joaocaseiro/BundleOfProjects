package org.xproject.moony.data.objects.category;

import org.xproject.moony.data.ContentManagerDescriptor;
import org.xproject.moony.data.contentprovider.XProviderDescriptor;
import org.xproject.moony.data.objects.BaseDescriptor;

import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

public class CategoryDescriptor extends BaseDescriptor {

	public static final int VERSION = 1;
	public static final String TABLE_NAME = "Table_Category";

	public static final String NAME = "Category_Name";

	public static final Uri CONTENT_URI = XProviderDescriptor.BASE_URI.buildUpon().appendPath(TABLE_NAME).build();

	public static final String[] FIELDS = new String[] { ID, NAME };

	@SuppressWarnings({"rawtypes"})
	public static final Class[] FIELDS_TYPE = new Class[] { Long.class, String.class };

	public static final String PATH_MANY = TABLE_NAME;
	public static final String PATH_SINGLE = TABLE_NAME + "/*";
	public static final int CATEGORY_MULTIPLE_TOKEN = ContentManagerDescriptor.CATEGORY_MULTIPLE_TOKEN;
	public static final int CATEGORY_SINGLE_TOKEN = ContentManagerDescriptor.CATEGORY_SINGLE_TOKEN;

	public static final String CONTENT_TYPE_DIR = "vnd.android.cursor.dir/vnd." + TABLE_NAME + ".app";
	public static final String CONTENT_TYPE_ITEM = "vnd.android.cursor.item/vnd." + TABLE_NAME + ".app";

	@Override
	protected String getIdFieldName() {
		return ID;
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
		case CATEGORY_SINGLE_TOKEN:
			return CONTENT_TYPE_ITEM;
		case CATEGORY_MULTIPLE_TOKEN:
			return CONTENT_TYPE_DIR;
		}
		return CONTENT_TYPE_ITEM;
	}

	@Override
	public String[] getFields() {
		return FIELDS;
	}

	public static void onCreate(SQLiteDatabase db) {
		db.execSQL("create table " + TABLE_NAME + " (" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + NAME + " TEXT, UNIQUE("+NAME+"));");
	}

	public static void dropAll(SQLiteDatabase db) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
	}
}
