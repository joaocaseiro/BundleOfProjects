package org.xproject.moony;

import java.util.List;

import org.xproject.moony.data.DatabaseObject;
import org.xproject.moony.data.contentresolver.XResolver;
import org.xproject.moony.data.objects.Task;
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
		Task task = new Task("Levar o lixo", 20);
		task = (Task) task.save(task, resolver);

		List<DatabaseObject> list = task.findAll(null, resolver);
		for (DatabaseObject object : list) {
			LogHelper.log(getClass(), Log.VERBOSE, "Object: " + object);
		}
		
		task = new Task("Lavar o carro", 500);
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
		
		task = (Task) list.get(0);
		task.setName("Nao fazer nada");
		task.setValue(0);
		task.save(task, resolver);
		
		list = task.findAll(null, resolver);
		for (DatabaseObject object : list) {
			LogHelper.log(getClass(), Log.VERBOSE, "Object: " + object);
		}
	}
}