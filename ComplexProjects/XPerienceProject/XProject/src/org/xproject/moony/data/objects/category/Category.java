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
	/**
	 * returns the category with the given id or null if not found
	 */
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
	/**
	 * returns the category with the id of the given object(must be a category or an error is thrown) 
	 */
	public DatabaseObject findOne(DatabaseObject object, XResolver resolver) {
		if (object instanceof Category) {
			Category category = (Category) object;
			return findOne(category.getId(), resolver);
		} else {
			throw new UnrecognizedDatabaseObjectException("Unrecognized object type. Object " + object);
		}
	}

	@Override
	/**
	 * returns a list of all the categories. If the list provided is not null, it adds these elements to the end of the list 
	 * 
	 * @param list - the list in which the objects found are to be added or null if a new list is to be returned
	 * @param resolver - the ContentResolver
	 * @returns the list of objects (categories) in the database
	 */
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
	/**
	 * saves the object to the database or updates it if it already exists. The object must be a category or an error will be thrown.
	 * returns the object that was added or updated (if it was added the id field should be the default(-1) and it will be updated with the id value)
	 *  
	 * @param object - the object to be added or updated
	 * @param resolver - the ContentResolver
	 * @returns the object that was added or updated with the new information. If the object has id = -1 it was not possible to add it to the database
	 */
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
	/**
	 * removes the object with the given id
	 * 
	 * @param id - id of the object to be removed
	 * @param resolver - the ContentResolver
	 * @returns the number of objects removed
	 */
	public int remove(long id, XResolver resolver) {
		LogHelper.log(Category.class, Log.DEBUG, " - Category.remove(id(" + id + "), resolver(" + resolver + "))");
		int count = resolver.removeEntry(CategoryDescriptor.TABLE_NAME, CategoryDescriptor.ID + " = " + id, null);
		return count;
	}

	@Override
	/**
	 * removes the object with the same id as the object passed. Must be a category or an error will be thrown.
	 * 
	 * @param object - object to be removed
	 * @param resolver - the ContentResolver
	 * @returns the number of objects removed
	 */
	public int remove(DatabaseObject object, XResolver resolver) {
		if (object instanceof Category) {
			Category category = (Category) object;
			int count = remove(category.getId(), resolver);
			return count;
		} else {
			throw new UnrecognizedDatabaseObjectException("Unrecognized object type. Object " + object);
		}
	}

	/**
	 * removes all the objects from the database
	 * 
	 * @param list - if not null, expects a list of objects to be removed instead of all objects. The objects must be categories and only the id 
	 * of these categories will be taken into account when removing
	 *  
	 * @param resolver - the ContentResolver
	 * @returns the number of objects removed
	 */
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
