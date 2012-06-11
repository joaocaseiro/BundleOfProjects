package org.xproject.moony.data.objects;

import java.util.ArrayList;
import java.util.List;

import org.xproject.moony.data.DatabaseObject;
import org.xproject.moony.data.contentresolver.XResolver;
import org.xproject.moony.data.exceptions.UnrecognizedDatabaseObjectException;
import org.xproject.moony.utils.LogHelper;

import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

public class Task implements DatabaseObject {

	private long id;
	private String name;
	private int value;

	public Task(String name, int value) {
		this.id = -1;
		this.name = name;
		this.value = value;
	}

	private Task(long id, String name, int value) {
		this.id = id;
		this.name = name;
		this.value = value;
	}

	@Override
	public DatabaseObject findOne(long id, XResolver resolver) {
		LogHelper.log(Task.class, Log.DEBUG, " - Task.findOne(id(" + id + ")");
		Task task = null;

		Cursor c = resolver.getEntry(TaskDescriptor.TABLE_NAME, TaskDescriptor.FIELDS, TaskDescriptor.ID + "=" + id, null, null);
		if (c.moveToFirst()) {
			task = new Task(c.getLong(c.getColumnIndex(TaskDescriptor.ID)), c.getString(c.getColumnIndex(TaskDescriptor.NAME)), c.getInt(c
					.getColumnIndex(TaskDescriptor.VALUE)));
		}
		c.close();

		return task;
	}

	@Override
	public DatabaseObject findOne(DatabaseObject object, XResolver resolver) {
		if (object instanceof Task) {
			Task task = (Task) object;
			return findOne(task.getId(), resolver);
		} else {
			throw new UnrecognizedDatabaseObjectException("Unrecognized object type. Object " + object);
		}
	}

	@Override
	public List<DatabaseObject> findAll(List<DatabaseObject> list, XResolver resolver) {
		LogHelper.log(Task.class, Log.DEBUG, " - Task.findAll(list(" + list + "), resolver(" + resolver + "))");
		if (list == null) {
			list = new ArrayList<DatabaseObject>();
		}

		Cursor c = resolver.getEntry(TaskDescriptor.TABLE_NAME, TaskDescriptor.FIELDS, null, null, null);
		for (; c.moveToNext();) {
			Task task = new Task(c.getLong(c.getColumnIndex(TaskDescriptor.ID)), c.getString(c.getColumnIndex(TaskDescriptor.NAME)), c.getInt(c
					.getColumnIndex(TaskDescriptor.VALUE)));
			list.add(task);
		}
		c.close();

		return list;
	}

	@Override
	public DatabaseObject save(DatabaseObject object, XResolver resolver) {

		long id = -1;

		if (object instanceof Task) {
			LogHelper.log(Task.class, Log.DEBUG, " - Task.save(task(" + object + "), resolver(" + resolver + "))");
			Task task = (Task) object;

			ContentValues values = new ContentValues();
			values.put(TaskDescriptor.NAME, task.getName());
			values.put(TaskDescriptor.VALUE, task.getValue());

			if ((id = task.getId()) == -1) {
				resolver.insertEntry(TaskDescriptor.TABLE_NAME, values);
			} else {
				resolver.updateEntry(TaskDescriptor.TABLE_NAME, values, TaskDescriptor.ID + " = " + id, null);
			}
		} else {
			throw new UnrecognizedDatabaseObjectException("Unrecognized object type. Object " + object);
		}

		return object;
	}

	@Override
	public int remove(long id, XResolver resolver) {
		int count = resolver.removeEntry(TaskDescriptor.TABLE_NAME, TaskDescriptor.ID + " = " + id, null);
		return count;
	}

	@Override
	public int remove(DatabaseObject object, XResolver resolver) {
		if (object instanceof Task) {
			LogHelper.log(Task.class, Log.DEBUG, " - Task.remove(task(" + object + "), resolver(" + resolver + "))");
			Task task = (Task) object;
			int count = remove(task.getId(), resolver);
			return count;
		} else {
			throw new UnrecognizedDatabaseObjectException("Unrecognized object type. Object " + object);
		}
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

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	@Override
	public String toString() {
		String result = "id: " + id + " name: " + name + " value: " + value;
		return result;
	}
}
