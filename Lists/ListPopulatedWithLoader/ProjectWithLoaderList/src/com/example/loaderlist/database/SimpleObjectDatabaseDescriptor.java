package com.example.loaderlist.database;

import android.net.Uri;

public class SimpleObjectDatabaseDescriptor extends BaseDescriptor {

	public final static String TBL_NAME = "tbl_simple";
	
	/** INHERITS MORE FIELDS FROM BaseDescriptor **/
	
	public final static String NAME = "name";
	
	public static final String[] FIELDS = new String[] { ID, NAME };

	public static final String CONTENT_TYPE_DIR = "vnd.android.cursor.dir/vnd."+TBL_NAME+".app";
	public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd."+TBL_NAME+".app";

	public SimpleObjectDatabaseDescriptor(boolean many) {
		super(many);
	}
	
	public static String getCreatorDatabaseSql() {
		String creationSql = "CREATE TABLE "+TBL_NAME+" (" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
					NAME + " TEXT NOT NULL);";
 		return creationSql;
	}

	public static String getDestroyerDatabaseSql() {
		String destructionSql = "DROP TABLE IF EXISTS "+TBL_NAME+";";
		return destructionSql;
	}

	@Override
	protected String getIdFieldName() {
		return ID;
	}

	@Override
	protected String getTableName() {
		return TBL_NAME;
	}

	@Override
	protected Uri getContentUri() {
		return null;
	}

	@Override
	protected String getContentItemType() {
		return CONTENT_ITEM_TYPE;
	}

	@Override
	protected String getContentTypeDir() {
		return CONTENT_TYPE_DIR;
	}
}
