package org.xproject.moony;

import java.util.ArrayList;
import java.util.List;

import org.xproject.moony.data.DatabaseObject;
import org.xproject.moony.data.contentresolver.XResolver;
import org.xproject.moony.data.objects.category.Category;
import org.xproject.moony.data.objects.task.Task;
import org.xproject.moony.utils.LogHelper;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class XProjectActivity extends Activity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		XResolver resolver = new XResolver(this);
		
		Task task = new Task("", 0, null);
		Category category = new Category("Cozinha");
		
		task.removeAll(null, resolver);
		category.removeAll(null, resolver);
		
		List<DatabaseObject> list = task.findAll(null, resolver);
		for (DatabaseObject object : list) {
			LogHelper.log(getClass(), Log.VERBOSE, "Object: " + object);
		}
		
		category = (Category) category.save(category, resolver);
		
		task = new Task("Fazer mousse de chocolate", 1200, category);
		task = (Task) task.save(task, resolver);

		list = task.findAll(null, resolver);
		for (DatabaseObject object : list) {
			LogHelper.log(getClass(), Log.VERBOSE, "Object: " + object);
		}
		
		task = new Task("Lavar a loica", 700, category);
		task = (Task) task.save(task, resolver);
		
		list = task.findAll(null, resolver);
		for (DatabaseObject object : list) {
			LogHelper.log(getClass(), Log.VERBOSE, "Object: " + object);
		}

		task = (Task) list.get(0);
		task.remove(task, resolver);
		
		list = task.findAll(null, resolver);
		for (DatabaseObject object : list) {
			LogHelper.log(getClass(), Log.VERBOSE, "Object: " + object);
		}
		
		category = new Category("Preguica");
		category = (Category) category.save(category, resolver);
		
		task = (Task) list.get(0);
		task.setName("Nao fazer nada");
		task.setValue(0);
		task.setCategory(category);
		task.save(task, resolver);
		
		list = task.findAll(null, resolver);
		for (DatabaseObject object : list) {
			LogHelper.log(getClass(), Log.VERBOSE, "Object: " + object);
		}
		
		task = new Task("Job 1", 100, category);
		task.save(task, resolver);
		
		Task task1 = new Task("Job 2", 200, category);
		task1 = (Task) task1.save(task1, resolver);
		
		Task task2 = new Task("Job 3", 300, category);
		task2.save(task2, resolver);
		
		Task task3 = new Task("Job 4", 400, category);
		task3 = (Task) task3.save(task3, resolver);
		
		list = task.findAll(null, resolver);
		for (DatabaseObject object : list) {
			LogHelper.log(getClass(), Log.VERBOSE, "Object: " + object);
		}
		
		list = new ArrayList<DatabaseObject>();
		list.add(task1);
		list.add(task2);
		list.add(task3);
		
		task.removeAll(list, resolver);
		
		list = task.findAll(null, resolver);
		for (DatabaseObject object : list) {
			LogHelper.log(getClass(), Log.VERBOSE, "Object: " + object);
		}
	}
}