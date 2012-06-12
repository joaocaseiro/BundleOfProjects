package org.xproject.moony.data.objects.task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import org.xproject.moony.data.DatabaseObject;
import org.xproject.moony.data.contentresolver.XResolver;
import org.xproject.moony.data.exceptions.UnrecognizedDatabaseObjectException;
import org.xproject.moony.data.objects.category.Category;
import org.xproject.moony.utils.LogHelper;

import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

public class Task implements DatabaseObject {

	private HashMap<Long, Category> cache;

	private Long id;
	private String name;
	private Category category;
	private Integer value;

	public Task(String name, int value, Category category) {
		this.id = new Long(-1);
		this.name = name;
		this.value = value;
		this.category = category;

		init();
	}

	private void init() {
		cache = new LinkedHashMap<Long, Category>();
	}

	private Task(long id, String name, int value, Category category) {
		this.id = id;
		this.name = name;
		this.value = value;
		this.category = category;

		init();
	}

	@Override
	public DatabaseObject findOne(long id, XResolver resolver) {
		LogHelper.log(Task.class, Log.DEBUG, " - Task.findOne(id(" + id + ")");
		Task task = null;

		Cursor c = resolver.getEntry(TaskDescriptor.TABLE_NAME, TaskDescriptor.FIELDS, TaskDescriptor.ID + "=" + id, null, null);
		if (c.moveToFirst()) {
			long categoryId = c.getLong(c.getColumnIndex(TaskDescriptor.CATEGORY_ID));
			Category category = resolveCategoryId(resolver, categoryId, new Category(""));
			task = new Task(c.getLong(c.getColumnIndex(TaskDescriptor.ID)), c.getString(c.getColumnIndex(TaskDescriptor.NAME)), c.getInt(c
					.getColumnIndex(TaskDescriptor.VALUE)), category);
		}
		c.close();

		return task;
	}

	private Category resolveCategoryId(XResolver resolver, long categoryId, Category category) {
		Category categoryTmp = null;
		if ((categoryTmp = cache.get(categoryId)) == null) {
			category = (Category) category.findOne(categoryId, resolver);
			cache.put(categoryId, category);
		} else {
			category = categoryTmp;
		}
		return category;
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

		Category categoryUtil = new Category("");
		Cursor c = resolver.getEntry(TaskDescriptor.TABLE_NAME, TaskDescriptor.FIELDS, null, null, null);
		for (; c.moveToNext();) {
			long categoryId = c.getLong(c.getColumnIndex(TaskDescriptor.CATEGORY_ID));
			Category category = resolveCategoryId(resolver, categoryId, categoryUtil);
			Task task = new Task(c.getLong(c.getColumnIndex(TaskDescriptor.ID)), c.getString(c.getColumnIndex(TaskDescriptor.NAME)), c.getInt(c
					.getColumnIndex(TaskDescriptor.VALUE)), category);
			list.add(task);
		}
		c.close();

		return list;
	}

	@Override
	public DatabaseObject save(DatabaseObject object, XResolver resolver) {
		long id = -1;

		Task task = null;
		if (object instanceof Task) {
			LogHelper.log(Task.class, Log.DEBUG, " - Task.save(task(" + object + "), resolver(" + resolver + "))");
			task = (Task) object;

			ContentValues values = new ContentValues();
			values.put(TaskDescriptor.NAME, task.getName());
			values.put(TaskDescriptor.VALUE, task.getValue());
			values.put(TaskDescriptor.CATEGORY_ID, task.getCategory().getId());

			if ((id = task.getId()) == -1) {
				id = resolver.insertEntry(TaskDescriptor.TABLE_NAME, values);
				task.setId(id);
			} else {
				resolver.updateEntry(TaskDescriptor.TABLE_NAME, values, TaskDescriptor.ID + " = " + id, null);
			}
		} else {
			throw new UnrecognizedDatabaseObjectException("Unrecognized object type. Object " + object);
		}

		return task;
	}

	@Override
	public int remove(long id, XResolver resolver) {
		LogHelper.log(Task.class, Log.DEBUG, " - Task.remove(id(" + id + "), resolver(" + resolver + "))");
		int count = resolver.removeEntry(TaskDescriptor.TABLE_NAME, TaskDescriptor.ID + " = " + id, null);
		return count;
	}

	@Override
	public int remove(DatabaseObject object, XResolver resolver) {
		if (object instanceof Task) {
			Task task = (Task) object;
			int count = remove(task.getId(), resolver);
			return count;
		} else {
			throw new UnrecognizedDatabaseObjectException("Unrecognized object type. Object " + object);
		}
	}
	
	public int removeAll(List<DatabaseObject> list, XResolver resolver) {
		LogHelper.log(Task.class, Log.DEBUG, " - Task.removeAll(list(" + list + "), resolver(" + resolver + "))");
		
		String filter = null;
		
		if(list != null) {
			int i = 0;
			filter = "";
			for(DatabaseObject obj : list) {
				if(obj instanceof Task) {
					Task task = (Task)obj;
					filter += TaskDescriptor.ID + " = " + task.getId();
					if(i != list.size() - 1) {
						filter += " OR ";
					}
				}
				i++;
			}
		}
		
		int count = resolver.removeEntry(TaskDescriptor.TABLE_NAME, filter, null);
		return count;
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
		String result = "id: " + id + " name: " + name + " category: " + category.getName() + " value: " + value;
		return result;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}
}
