package com.example.loaderlist.database;

import java.util.ArrayList;
import java.util.List;

import com.example.loaderlist.utils.LogHelper;

import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

public class SimpleObjectDatabase {
	protected long id;
	protected String name;
	
	public static List<SimpleObjectDatabase> findAll(List<SimpleObjectDatabase> alist, Database db) {
		LogHelper.log(SimpleObjectDatabase.class, Log.VERBOSE, " - SimpleObjectDatabase.getAll(alist(" + alist + ")"
				+ (alist == null ? "" : " size(" + alist.size() + ")") + ")");
		List<SimpleObjectDatabase> tlist = (alist == null ? new ArrayList<SimpleObjectDatabase>() : alist);

		Cursor c = db.getEntry(SimpleObjectDatabaseDescriptor.TBL_NAME, SimpleObjectDatabaseDescriptor.FIELDS, null);
		if (c.moveToFirst()) {
			do {
				SimpleObjectDatabase s = new SimpleObjectDatabase(c.getLong(c.getColumnIndex(SimpleObjectDatabaseDescriptor.ID)),
						c.getString(c.getColumnIndex(SimpleObjectDatabaseDescriptor.NAME)));
				tlist.add(s);
			} while (c.moveToNext());
		}
		c.close();
		return tlist;
	}
	
	public static SimpleObjectDatabase findOne(long id, Database db) {
		LogHelper.log(SimpleObjectDatabase.class, Log.VERBOSE, " - SimpleObjectDatabase.get(id(" + id + ")");
		SimpleObjectDatabase cc = null;

		Cursor c = db.getEntry(SimpleObjectDatabaseDescriptor.TBL_NAME, SimpleObjectDatabaseDescriptor.FIELDS,
				SimpleObjectDatabaseDescriptor.ID + "=" + id);
		if (c.moveToFirst()) {
			cc = new SimpleObjectDatabase(c.getLong(c.getColumnIndex(SimpleObjectDatabaseDescriptor.ID)),
					c.getString(c.getColumnIndex(SimpleObjectDatabaseDescriptor.NAME)));
		}
		c.close();

		return cc;
	}
	
	public static long create(String name, Database db) {
		ContentValues cv = new ContentValues();
		cv.put(SimpleObjectDatabaseDescriptor.NAME, name);
		long id = db.insertEntry(SimpleObjectDatabaseDescriptor.TBL_NAME, cv);

		LogHelper.log(SimpleObjectDatabase.class, Log.VERBOSE, " - SimpleObjectDatabase.create(id(" + id + ") name("+name+"))");
		return id;
	}
	
	public static long update(long id, String name, Database db) {
		LogHelper.log(SimpleObjectDatabase.class, Log.VERBOSE, " - SimpleObjectDatabase.update(id(" + id + ") name("+name+"))");
		ContentValues cv = new ContentValues();
		cv.put(SimpleObjectDatabaseDescriptor.NAME, name);

		return db.updateEntry(SimpleObjectDatabaseDescriptor.TBL_NAME, cv, SimpleObjectDatabaseDescriptor.ID + "=" + id);
	}
	
	public static void delete(long id, Database db) {
		LogHelper.log(SimpleObjectDatabase.class, Log.VERBOSE, " - SimpleObjectDatabase.delete(id(" + id + "))");
		db.removeEntry(SimpleObjectDatabaseDescriptor.TBL_NAME, SimpleObjectDatabaseDescriptor.ID + "=" + id);
	}

	public static void deleteAll(Database db) {
		LogHelper.log(SimpleObjectDatabase.class, Log.VERBOSE, " - SimpleObjectDatabase.deleteAll()");
		db.removeEntry(SimpleObjectDatabaseDescriptor.TBL_NAME, null);
	}
	
	public SimpleObjectDatabase(long id, String name) {
		this.id = id;
		this.name = name;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
