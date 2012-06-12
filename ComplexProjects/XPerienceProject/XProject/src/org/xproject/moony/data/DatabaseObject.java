package org.xproject.moony.data;

import java.util.List;

import org.xproject.moony.data.contentresolver.XResolver;

public interface DatabaseObject {
	public DatabaseObject findOne(long id, XResolver resolver);

	public DatabaseObject findOne(DatabaseObject object, XResolver resolver);

	public List<DatabaseObject> findAll(List<DatabaseObject> list, XResolver resolver);

	public DatabaseObject save(DatabaseObject object, XResolver resolver);

	public int remove(long id, XResolver resolver);

	public int remove(DatabaseObject object, XResolver resolver);
}
