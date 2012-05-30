package com.example.loaderlist;

import android.app.Activity;
import android.app.ListFragment;
import android.os.Parcel;
import android.os.Parcelable;

import com.example.loaderlist.RetainedFragment.Executor;
import com.example.loaderlist.database.Database;
import com.example.loaderlist.database.SimpleObjectDatabase;

public class MyExecutor implements Executor {
	private Activity activity;
	private ListFragment fragment;
	private Database db;
	
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
	
	public static final Parcelable.Creator<MyExecutor> CREATOR = new Parcelable.Creator<MyExecutor>() {
		public MyExecutor createFromParcel(Parcel in) {
			return new MyExecutor(in);
		}

		public MyExecutor[] newArray(int size) {
			return new MyExecutor[size];
		}
	};

	private MyExecutor(Parcel in) {
	}
	
	public MyExecutor(Activity activity) {
		update(activity);
	}

	private void update(Activity activity) {
		this.activity = activity;
		if(this.activity != null) {
			this.fragment = (ListFragment) activity.getFragmentManager().findFragmentById(R.id.listOfNames);
			
			if(db == null) {
				db = new Database(activity);
				db.open();				
			}
		} else {
			this.fragment = null;
			if(db != null) {
				if(db.isOpen()) {
					db.close();
				}
				db = null;
			}
		}
	}
	
	@Override
	public void run() {
		for (String s : listOfNames) {
			SimpleObjectDatabase.create(s, db);
		}
	}

	@Override
	public void invalidateOldActivityReferences() {
		update(null);
	}

	@Override
	public void updateNewActivityReferences(Activity newActivity) {
		update(newActivity);
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel parcel, int flags) {
		return;
	}

	@Override
	public void prepareRun() {
		fragment.setListShown(false);
	}

	@Override
	public void afterRun() {
		fragment.setListShown(true);
	}
	
}
