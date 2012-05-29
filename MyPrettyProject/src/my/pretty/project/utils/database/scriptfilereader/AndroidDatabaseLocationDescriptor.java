package my.pretty.project.utils.database.scriptfilereader;

import android.net.Uri;

public class AndroidDatabaseLocationDescriptor extends BaseDescriptor {
	public final static String TBL_NAME = "tbl_location";
	
	/** INHERITS MORE FIELDS FROM BaseDescriptor **/
	
	/** id of the location as received from the server **/
	public final static String ID_SERVER = "id_server";
	
	/** version of the location **/
	public final static String VERSION = "version";
	
	/** location is active or not **/
	public final static String ACTIVE = "active_location";
	
	/** name of the location **/
	public final static String NAME = "name";
	
	/** description of the location **/
	public final static String DESCRIPTION = "description";
	
	/** reference points of the location (?) **/
	public final static String REF_POINT_X = "ref_point_x";
	public final static String REF_POINT_Y = "ref_point_y";
	public final static String REF_POINT_Z = "ref_point_z";
	
	/** creator of the location **/
	public final static String CREATOR = "creator";
	
	/** creation date of the location (string containing a long with the time in miliseconds)**/
	public final static String CREATION_DATE = "creation_date";
	
	/** owner id of the location (?) **/
	public final static String OWNER = "owner_id";
	
	/** all fields of the location db object (for queries)**/
	public final static String[] FIELDS = { ID, ID_SERVER, VERSION, ACTIVE, NAME, DESCRIPTION, REF_POINT_X, REF_POINT_Y, REF_POINT_Z, CREATOR, CREATION_DATE, OWNER };
	
	public static final String PATH = TBL_NAME+"s";
	public static final int PATH_TOKEN = 107;
	public static final String PATH_FOR_ID = PATH+"/*";
	public static final int PATH_FOR_ID_TOKEN = 207;
	
	public static final String CONTENT_TYPE_DIR = "vnd.android.cursor.dir/vnd."+TBL_NAME+".app";
	public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd."+TBL_NAME+".app";
	
	public AndroidDatabaseLocationDescriptor(boolean many) {
		super(many);
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
	
	protected String getContentItemType() {
		return CONTENT_ITEM_TYPE;
	}

	protected String getContentTypeDir() {
		return CONTENT_TYPE_DIR;
	}

}
