package org.xproject.moony.data.objects.category;

import java.util.ArrayList;
import java.util.List;

import org.xproject.moony.data.DatabaseObject;
import org.xproject.moony.data.contentresolver.XResolver;
import org.xproject.moony.data.exceptions.UnrecognizedDatabaseObjectException;
import org.xproject.moony.data.objects.task.Task;
import org.xproject.moony.utils.LogHelper;

import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

public class Category implements DatabaseObject {
	private Long id;
	private String name;

	public Category(String name) {
		this.id = new Long(-1);
		this.name = name;
	}

	public Category(long id, String name) {
		this.id = id;
		this.name = name;
	}

	@Override
	public DatabaseObject findOne(long id, XResolver resolver) {
		LogHelper.log(Category.class, Log.DEBUG, " - Category.findOne(id(" + id + "))");
		Category category = null;

		Cursor c = resolver.getEntry(CategoryDescriptor.TABLE_NAME, CategoryDescriptor.FIELDS, CategoryDescriptor.ID + "=" + id, null, null);
		if (c.moveToFirst()) {
			category = new Category(c.getLong(c.getColumnIndex(CategoryDescriptor.ID)), c.getString(c.getColumnIndex(CategoryDescriptor.NAME)));
		}
		c.close();

		return category;
	}

	@Override
	public DatabaseObject findOne(DatabaseObject object, XResolver resolver) {
		if (object instanceof Category) {
			Category category = (Category) object;
			return findOne(category.getId(), resolver);
		} else {
			throw new UnrecognizedDatabaseObjectException("Unrecognized object type. Object " + object);
		}
	}

	@Override
	public List<DatabaseObject> findAll(List<DatabaseObject> list, XResolver resolver) {
		LogHelper.log(Task.class, Log.DEBUG, " - Category.findAll(list(" + list + "), resolver(" + resolver + "))");
		if (list == null) {
			list = new ArrayList<DatabaseObject>();
		}

		Cursor c = resolver.getEntry(CategoryDescriptor.TABLE_NAME, CategoryDescriptor.FIELDS, null, null, null);
		for (; c.moveToNext();) {
			Category category = new Category(c.getLong(c.getColumnIndex(CategoryDescriptor.ID)), c.getString(c.getColumnIndex(CategoryDescriptor.NAME)));
			list.add(category);
		}
		c.close();

		return list;
	}

	@Override
	public DatabaseObject save(DatabaseObject object, XResolver resolver) {
		long id = -1;

		Category category = null;
		if (object instanceof Category) {
			LogHelper.log(Category.class, Log.DEBUG, " - Category.save(task(" + object + "), resolver(" + resolver + "))");
			category = (Category) object;

			ContentValues values = new ContentValues();
			values.put(CategoryDescriptor.NAME, category.getName());

			if ((id = category.getId()) == -1) {
				id = resolver.insertEntry(CategoryDescriptor.TABLE_NAME, values);
				category.setId(id);
			} else {
				resolver.updateEntry(CategoryDescriptor.TABLE_NAME, values, CategoryDescriptor.ID + " = " + id, null);
			}
		} else {
			throw new UnrecognizedDatabaseObjectException("Unrecognized object type. Object " + object);
		}

		return category;
	}

	@Override
	public int remove(long id, XResolver resolver) {
		LogHelper.log(Category.class, Log.DEBUG, " - Category.remove(id(" + id + "), resolver(" + resolver + "))");
		int count = resolver.removeEntry(CategoryDescriptor.TABLE_NAME, CategoryDescriptor.ID + " = " + id, null);
		return count;
	}

	@Override
	public int remove(DatabaseObject object, XResolver resolver) {
		if (object instanceof Category) {
			Category category = (Category) object;
			int count = remove(category.getId(), resolver);
			return count;
		} else {
			throw new UnrecognizedDatabaseObjectException("Unrecognized object type. Object " + object);
		}
	}

	public int removeAll(List<DatabaseObject> list, XResolver resolver) {
		LogHelper.log(Task.class, Log.DEBUG, " - Category.removeAll(list(" + list + "), resolver(" + resolver + "))");

		String filter = null;

		if (list != null) {
			int i = 0;
			filter = "";
			for (DatabaseObject obj : list) {
				if (obj instanceof Category) {
					Category category = (Category) obj;
					filter += CategoryDescriptor.ID + " = " + category.getId();
					if (i != list.size() - 1) {
						filter += " OR ";
					}
				}
				i++;
			}
		}

		int count = resolver.removeEntry(CategoryDescriptor.TABLE_NAME, filter, null);
		return count;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "id: " + id + " name: " + name;
	}
}
