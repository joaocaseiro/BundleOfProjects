package com.example.loaderlist;

import android.os.Bundle;

import com.example.loaderlist.database.Database;
import com.example.loaderlist.database.SimpleObjectDatabase;

public class FillDatabaseFragment extends AsyncTaskFragment<Void, Integer, Void> {

	private static final int NOT_STARTED = 0;
	private static final int STARTED = 1;
	private static final int FINISHED = 2;
	
	private Database db;
	private DatabaseFilledCallback callback;
	private int state = NOT_STARTED;
	private static String[] listOfNames = { "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a",
			"a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a",
			"a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a",
			"a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a",
			"a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a",
			"a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a",
			"a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a",
			"a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a",
			"a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a",
			"a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a",
			"a", "a", "a", "a", "a", "a", "a", "a", "a" };

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setVerbose(true);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		if(NOT_STARTED == state) {
			execute();
		}
	}
	
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		state = STARTED;
		
		db = new Database(getActivity());
		db.open();
	}

	@Override
	protected Void doInBackground(Void... params) {
		for (String s : listOfNames) {
			SimpleObjectDatabase.create(s, db);
		}
		return null;
	}
	
	@Override
	protected void onPostExecute(Void result) {
		super.onPostExecute(result);
		
		db.close();
		db = null;
		
		this.state = FINISHED;
		
		if(callback != null) {
			callback.dataReady();
		}
	}
	
	public void setOnDatabaseFilledCallback(DatabaseFilledCallback callback) {
		this.callback = callback;
	}

	public interface DatabaseFilledCallback {
		public void dataReady();
	}
	
	public boolean hasFinished() {
		return state == FINISHED;
	}
}
