package my.pretty.project.utils.database.contentprovider;

import java.util.ArrayList;
import java.util.List;

import my.pretty.project.utils.LogHelper;

import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

public class AndroidDatabaseLocation {
	protected long id;
	protected long id_server;
	protected long version;

	protected boolean active;
	protected String name;
	protected String description;
	protected double x, y, z;

	protected List<String> tags;
	protected String creator;
	protected String creationDate;
	protected long creatorId;

	public static List<AndroidDatabaseLocation> getAll(List<AndroidDatabaseLocation> alist, Database db) {
		LogHelper.log(AndroidDatabaseLocation.class, Log.VERBOSE, " - AndroidDatabaseLocation.getAll(alist(" + alist + ")"
				+ (alist == null ? "" : " size(" + alist.size() + ")") + ")");
		List<AndroidDatabaseLocation> tlist = (alist == null ? new ArrayList<AndroidDatabaseLocation>() : alist);

		Cursor c = db.getEntry(AndroidDatabaseLocationDescriptor.TBL_NAME, AndroidDatabaseLocationDescriptor.FIELDS, null);
		if (c.moveToFirst()) {
			do {
				AndroidDatabaseLocation s = new AndroidDatabaseLocation(c.getLong(c.getColumnIndex(AndroidDatabaseLocationDescriptor.ID)),
						c.getLong(c.getColumnIndex(AndroidDatabaseLocationDescriptor.ID_SERVER)), c.getLong(c
								.getColumnIndex(AndroidDatabaseLocationDescriptor.VERSION)), c.getInt(c
								.getColumnIndex(AndroidDatabaseLocationDescriptor.ACTIVE)) != 0, c.getString(c
								.getColumnIndex(AndroidDatabaseLocationDescriptor.NAME)), c.getString(c
								.getColumnIndex(AndroidDatabaseLocationDescriptor.DESCRIPTION)), c.getDouble(c
								.getColumnIndex(AndroidDatabaseLocationDescriptor.REF_POINT_X)), c.getDouble(c
								.getColumnIndex(AndroidDatabaseLocationDescriptor.REF_POINT_Y)), c.getDouble(c
								.getColumnIndex(AndroidDatabaseLocationDescriptor.REF_POINT_Z)), c.getString(c
								.getColumnIndex(AndroidDatabaseLocationDescriptor.CREATOR)), c.getString(c
								.getColumnIndex(AndroidDatabaseLocationDescriptor.CREATION_DATE)), c.getLong(c
								.getColumnIndex(AndroidDatabaseLocationDescriptor.OWNER)));
				tlist.add(s);
			} while (c.moveToNext());
		}
		c.close();
		return tlist;
	}

	public static List<AndroidDatabaseLocation> getAllByLocation(List<AndroidDatabaseLocation> alist, long idLocation, Database db) {
		LogHelper.log(AndroidDatabaseLocation.class, Log.VERBOSE, " - AndroidDatabaseLocation.getAllByLocation(alist(" + alist + ")"
				+ (alist == null ? "" : " size(" + alist.size() + ")") + ")");
		List<AndroidDatabaseLocation> tlist = (alist == null ? new ArrayList<AndroidDatabaseLocation>() : alist);

		Cursor c = db.getEntry(AndroidDatabaseLocationDescriptor.TBL_NAME, AndroidDatabaseLocationDescriptor.FIELDS,
				AndroidDatabaseLocationDescriptor.OWNER + " = " + idLocation);
		if (c.moveToFirst()) {
			do {
				AndroidDatabaseLocation s = new AndroidDatabaseLocation(c.getLong(c.getColumnIndex(AndroidDatabaseLocationDescriptor.ID)),
						c.getLong(c.getColumnIndex(AndroidDatabaseLocationDescriptor.ID_SERVER)), c.getLong(c
								.getColumnIndex(AndroidDatabaseLocationDescriptor.VERSION)), c.getInt(c
								.getColumnIndex(AndroidDatabaseLocationDescriptor.ACTIVE)) != 0, c.getString(c
								.getColumnIndex(AndroidDatabaseLocationDescriptor.NAME)), c.getString(c
								.getColumnIndex(AndroidDatabaseLocationDescriptor.DESCRIPTION)), c.getDouble(c
								.getColumnIndex(AndroidDatabaseLocationDescriptor.REF_POINT_X)), c.getDouble(c
								.getColumnIndex(AndroidDatabaseLocationDescriptor.REF_POINT_Y)), c.getDouble(c
								.getColumnIndex(AndroidDatabaseLocationDescriptor.REF_POINT_Z)), c.getString(c
								.getColumnIndex(AndroidDatabaseLocationDescriptor.CREATOR)), c.getString(c
								.getColumnIndex(AndroidDatabaseLocationDescriptor.CREATION_DATE)), c.getLong(c
								.getColumnIndex(AndroidDatabaseLocationDescriptor.OWNER)));
				tlist.add(s);
			} while (c.moveToNext());
		}
		c.close();
		return tlist;
	}

	public static AndroidDatabaseLocation get(long id, Database db) {
		LogHelper.log(AndroidDatabaseLocation.class, Log.VERBOSE, " - AndroidDatabaseLocation.get(id(" + id + ")");
		AndroidDatabaseLocation cc = null;

		Cursor c = db.getEntry(AndroidDatabaseLocationDescriptor.TBL_NAME, AndroidDatabaseLocationDescriptor.FIELDS,
				AndroidDatabaseLocationDescriptor.ID + "=" + id);
		if (c.moveToFirst()) {
			cc = new AndroidDatabaseLocation(c.getLong(c.getColumnIndex(AndroidDatabaseLocationDescriptor.ID)), c.getLong(c
					.getColumnIndex(AndroidDatabaseLocationDescriptor.ID_SERVER)), c.getLong(c
					.getColumnIndex(AndroidDatabaseLocationDescriptor.VERSION)), c.getInt(c
					.getColumnIndex(AndroidDatabaseLocationDescriptor.ACTIVE)) != 0, c.getString(c
					.getColumnIndex(AndroidDatabaseLocationDescriptor.NAME)), c.getString(c
					.getColumnIndex(AndroidDatabaseLocationDescriptor.DESCRIPTION)), c.getDouble(c
					.getColumnIndex(AndroidDatabaseLocationDescriptor.REF_POINT_X)), c.getDouble(c
					.getColumnIndex(AndroidDatabaseLocationDescriptor.REF_POINT_Y)), c.getDouble(c
					.getColumnIndex(AndroidDatabaseLocationDescriptor.REF_POINT_Z)), c.getString(c
					.getColumnIndex(AndroidDatabaseLocationDescriptor.CREATOR)), c.getString(c
					.getColumnIndex(AndroidDatabaseLocationDescriptor.CREATION_DATE)), c.getLong(c
					.getColumnIndex(AndroidDatabaseLocationDescriptor.OWNER)));
		}
		c.close();

		return cc;
	}

	public static long create(long id_server, long version, boolean active, String name, String description, double refPointX,
			double refPointY, double refPointZ, String creator, String creationDate, long owner, Database db) {
		LogHelper.log(AndroidDatabaseLocation.class, Log.VERBOSE, " - AndroidDatabaseLocation.create(id_server(" + id_server + ") version("
				+ version + ") active(" + active + ") name(" + name + ") description(" + description + ") refPointX(" + refPointX
				+ ") refPointY(" + refPointY + ") refPointZ(" + refPointZ + ") creator(" + creator + ") creationDate(" + creationDate
				+ ") owner(" + owner + "))");

		ContentValues cv = new ContentValues();
		cv.put(AndroidDatabaseLocationDescriptor.ID_SERVER, id_server);
		cv.put(AndroidDatabaseLocationDescriptor.VERSION, version);
		cv.put(AndroidDatabaseLocationDescriptor.ACTIVE, active);
		cv.put(AndroidDatabaseLocationDescriptor.NAME, name);
		cv.put(AndroidDatabaseLocationDescriptor.DESCRIPTION, description);
		cv.put(AndroidDatabaseLocationDescriptor.REF_POINT_X, refPointX);
		cv.put(AndroidDatabaseLocationDescriptor.REF_POINT_Y, refPointY);
		cv.put(AndroidDatabaseLocationDescriptor.REF_POINT_Z, refPointZ);
		cv.put(AndroidDatabaseLocationDescriptor.CREATOR, creator);
		cv.put(AndroidDatabaseLocationDescriptor.CREATION_DATE, creationDate);
		cv.put(AndroidDatabaseLocationDescriptor.OWNER, owner);

		return db.insertEntry(AndroidDatabaseLocationDescriptor.TBL_NAME, cv);
	}

	public static long update(long id, long id_server, long version, boolean active, String name, String description, double refPointX,
			double refPointY, double refPointZ, String creator, String creationDate, long owner, Database db) {
		LogHelper.log(AndroidDatabaseLocation.class, Log.VERBOSE, " - AndroidDatabaseLocation.update(id(" + id + ") id_server(" + id_server
				+ ") version(" + version + ") active(" + active + ") name(" + name + ") description(" + description + ") refPointX("
				+ refPointX + ") refPointY(" + refPointY + ") refPointZ(" + refPointZ + ") creator(" + creator + ") creationDate("
				+ creationDate + ") owner(" + owner + "))");

		ContentValues cv = new ContentValues();
		cv.put(AndroidDatabaseLocationDescriptor.ID_SERVER, id_server);
		cv.put(AndroidDatabaseLocationDescriptor.VERSION, version);
		cv.put(AndroidDatabaseLocationDescriptor.ACTIVE, active);
		cv.put(AndroidDatabaseLocationDescriptor.NAME, name);
		cv.put(AndroidDatabaseLocationDescriptor.DESCRIPTION, description);
		cv.put(AndroidDatabaseLocationDescriptor.REF_POINT_X, refPointX);
		cv.put(AndroidDatabaseLocationDescriptor.REF_POINT_Y, refPointY);
		cv.put(AndroidDatabaseLocationDescriptor.REF_POINT_Z, refPointZ);
		cv.put(AndroidDatabaseLocationDescriptor.CREATOR, creator);
		cv.put(AndroidDatabaseLocationDescriptor.CREATION_DATE, creationDate);
		cv.put(AndroidDatabaseLocationDescriptor.OWNER, owner);

		return db.updateEntry(AndroidDatabaseLocationDescriptor.TBL_NAME, cv, AndroidDatabaseLocationDescriptor.ID + "=" + id);
	}

	public static void delete(long id, Database db) {
		LogHelper.log(AndroidDatabaseLocation.class, Log.VERBOSE, " - AndroidDatabaseLocation.delete(id(" + id + "))");
		db.removeEntry(AndroidDatabaseLocationDescriptor.TBL_NAME, AndroidDatabaseLocationDescriptor.ID + "=" + id);
	}

	public static void clearAllRecordsFromDatabase(Database db) {
		LogHelper.log(AndroidDatabaseLocation.class, Log.VERBOSE, " - AndroidDatabaseLocation.clearAllRecordsFromDatabase()");
		db.removeEntry(AndroidDatabaseLocationDescriptor.TBL_NAME, null);
	}

	public AndroidDatabaseLocation(long id, long id_server, long version, boolean active, String name, String description,
			double refPointX, double refPointY, double refPointZ, String creator, String creationDate, long owner) {
		LogHelper.log(AndroidDatabaseLocation.class, Log.VERBOSE, " - AndroidDatabaseLocation(id(" + id + ") id_server(" + id_server
				+ ") version(" + version + ") active(" + active + ") name(" + name + ") description(" + description + ") refPointX("
				+ refPointX + ") refPointY(" + refPointY + ") refPointZ(" + refPointZ + ") creator(" + creator + ") creationDate("
				+ creationDate + ") owner(" + owner + "))");
		this.id = id;
		this.id_server = id_server;
		this.version = version;
		this.active = active;
		this.name = name;
		this.description = description;
		this.x = refPointX;
		this.y = refPointY;
		this.z = refPointZ;
		this.creator = creator;
		this.creationDate = creationDate;
		this.creatorId = owner;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getVersion() {
		return version;
	}

	public void setVersion(long version) {
		this.version = version;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public long getId_server() {
		return id_server;
	}

	public void setId_server(long id_server) {
		this.id_server = id_server;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double getZ() {
		return z;
	}

	public void setZ(double z) {
		this.z = z;
	}

	public String getTags() {
		String result = "";
		if (null != tags) {
			int size = tags.size() - 1, i = 0;
			for (String s : tags) {
				result += s;
				if (i != size) {
					result += ", ";
				}
				i++;
			}
		}
		return result;
	}

	public void setTags(List<String> tags) {
		this.tags = tags;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}

	public long getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(long creatorId) {
		this.creatorId = creatorId;
	}

	public static List<AndroidDatabaseLocation> getAllByServerID(List<AndroidDatabaseLocation> tmp, long serverID, Database db) {
		LogHelper.log(AndroidDatabaseLocation.class, Log.VERBOSE, " - AndroidDatabaseLocation.getAllByServerID(alist(" + tmp
				+ ") serverID(" + serverID + ")" + (tmp == null ? "" : " size(" + tmp.size() + ")") + ")");
		List<AndroidDatabaseLocation> tlist = (tmp == null ? new ArrayList<AndroidDatabaseLocation>() : tmp);

		Cursor c = db.getEntry(AndroidDatabaseLocationDescriptor.TBL_NAME, AndroidDatabaseLocationDescriptor.FIELDS,
				AndroidDatabaseLocationDescriptor.ID_SERVER + "=" + serverID);
		if (c.moveToFirst()) {
			do {
				AndroidDatabaseLocation s = new AndroidDatabaseLocation(c.getLong(c.getColumnIndex(AndroidDatabaseLocationDescriptor.ID)),
						c.getLong(c.getColumnIndex(AndroidDatabaseLocationDescriptor.ID_SERVER)), c.getLong(c
								.getColumnIndex(AndroidDatabaseLocationDescriptor.VERSION)), c.getInt(c
								.getColumnIndex(AndroidDatabaseLocationDescriptor.ACTIVE)) != 0, c.getString(c
								.getColumnIndex(AndroidDatabaseLocationDescriptor.NAME)), c.getString(c
								.getColumnIndex(AndroidDatabaseLocationDescriptor.DESCRIPTION)), c.getDouble(c
								.getColumnIndex(AndroidDatabaseLocationDescriptor.REF_POINT_X)), c.getDouble(c
								.getColumnIndex(AndroidDatabaseLocationDescriptor.REF_POINT_Y)), c.getDouble(c
								.getColumnIndex(AndroidDatabaseLocationDescriptor.REF_POINT_Z)), c.getString(c
								.getColumnIndex(AndroidDatabaseLocationDescriptor.CREATOR)), c.getString(c
								.getColumnIndex(AndroidDatabaseLocationDescriptor.CREATION_DATE)), c.getLong(c
								.getColumnIndex(AndroidDatabaseLocationDescriptor.OWNER)));
				tlist.add(s);
			} while (c.moveToNext());
		}
		c.close();
		return tlist;
	}

	public static List<AndroidDatabaseLocation> getAllByOwnerID(List<AndroidDatabaseLocation> tmp, long ownerID, Database db) {
		LogHelper.log(AndroidDatabaseLocation.class, Log.VERBOSE, " - AndroidDatabaseLocation.getAllByOwnerID(alist(" + tmp + ") serverID("
				+ ownerID + ")" + (tmp == null ? "" : " size(" + tmp.size() + ")") + ")");
		List<AndroidDatabaseLocation> tlist = (tmp == null ? new ArrayList<AndroidDatabaseLocation>() : tmp);

		Cursor c = db.getEntry(AndroidDatabaseLocationDescriptor.TBL_NAME, AndroidDatabaseLocationDescriptor.FIELDS,
				AndroidDatabaseLocationDescriptor.OWNER + "=" + ownerID);
		if (c.moveToFirst()) {
			do {
				AndroidDatabaseLocation s = new AndroidDatabaseLocation(c.getLong(c.getColumnIndex(AndroidDatabaseLocationDescriptor.ID)),
						c.getLong(c.getColumnIndex(AndroidDatabaseLocationDescriptor.ID_SERVER)), c.getLong(c
								.getColumnIndex(AndroidDatabaseLocationDescriptor.VERSION)), c.getInt(c
								.getColumnIndex(AndroidDatabaseLocationDescriptor.ACTIVE)) != 0, c.getString(c
								.getColumnIndex(AndroidDatabaseLocationDescriptor.NAME)), c.getString(c
								.getColumnIndex(AndroidDatabaseLocationDescriptor.DESCRIPTION)), c.getDouble(c
								.getColumnIndex(AndroidDatabaseLocationDescriptor.REF_POINT_X)), c.getDouble(c
								.getColumnIndex(AndroidDatabaseLocationDescriptor.REF_POINT_Y)), c.getDouble(c
								.getColumnIndex(AndroidDatabaseLocationDescriptor.REF_POINT_Z)), c.getString(c
								.getColumnIndex(AndroidDatabaseLocationDescriptor.CREATOR)), c.getString(c
								.getColumnIndex(AndroidDatabaseLocationDescriptor.CREATION_DATE)), c.getLong(c
								.getColumnIndex(AndroidDatabaseLocationDescriptor.OWNER)));
				tlist.add(s);
			} while (c.moveToNext());
		}
		c.close();
		return tlist;
	}

	public static AndroidDatabaseLocation getByServerID(long serverID, Database db) {
		LogHelper.log(AndroidDatabaseLocation.class, Log.VERBOSE, " - AndroidDatabaseLocation.getByServerID(id(" + serverID + ")");
		AndroidDatabaseLocation cc = null;

		Cursor c = db.getEntry(AndroidDatabaseLocationDescriptor.TBL_NAME, AndroidDatabaseLocationDescriptor.FIELDS,
				AndroidDatabaseLocationDescriptor.ID_SERVER + "=" + serverID);
		if (c.moveToFirst()) {
			cc = new AndroidDatabaseLocation(c.getLong(c.getColumnIndex(AndroidDatabaseLocationDescriptor.ID)), c.getLong(c
					.getColumnIndex(AndroidDatabaseLocationDescriptor.ID_SERVER)), c.getLong(c
					.getColumnIndex(AndroidDatabaseLocationDescriptor.VERSION)), c.getInt(c
					.getColumnIndex(AndroidDatabaseLocationDescriptor.ACTIVE)) != 0, c.getString(c
					.getColumnIndex(AndroidDatabaseLocationDescriptor.NAME)), c.getString(c
					.getColumnIndex(AndroidDatabaseLocationDescriptor.DESCRIPTION)), c.getDouble(c
					.getColumnIndex(AndroidDatabaseLocationDescriptor.REF_POINT_X)), c.getDouble(c
					.getColumnIndex(AndroidDatabaseLocationDescriptor.REF_POINT_Y)), c.getDouble(c
					.getColumnIndex(AndroidDatabaseLocationDescriptor.REF_POINT_Z)), c.getString(c
					.getColumnIndex(AndroidDatabaseLocationDescriptor.CREATOR)), c.getString(c
					.getColumnIndex(AndroidDatabaseLocationDescriptor.CREATION_DATE)), c.getLong(c
					.getColumnIndex(AndroidDatabaseLocationDescriptor.OWNER)));
		}
		c.close();

		return cc;
	}
}
