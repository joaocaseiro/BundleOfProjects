package com.example.loaderlist;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.content.res.Configuration;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.example.loaderlist.database.Database;
import com.example.loaderlist.database.SimpleCursorLoader;
import com.example.loaderlist.database.SimpleObjectDatabaseDescriptor;

public class ListFragmentCursor extends ListFragment implements LoaderCallbacks<Cursor> {

	private static final int ID_NAME_LIST_LOADER = 1;
	private SimpleCursorAdapter adapter;
	private static Database db = null;

	public static ListFragmentCursor getInstance() {
		ListFragmentCursor fragment = new ListFragmentCursor();
		Bundle arguments = new Bundle();

		fragment.setArguments(arguments);
		return fragment;
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		db = new Database(getActivity());
		db.open();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = super.onCreateView(inflater, container, savedInstanceState);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		adapter = new SimpleCursorAdapter(getActivity(), R.layout.list_item, null, SimpleObjectDatabaseDescriptor.FIELDS, new int[] { R.id.list_item_id,
				R.id.list_item_name });

		ListView listView = (ListView) getView().findViewById(android.R.id.list);
		listView.setAdapter(adapter);
		getLoaderManager().initLoader(ID_NAME_LIST_LOADER, null, this);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
				FragmentTransaction transaction = getFragmentManager().beginTransaction();
				
				DetailsFragment fragment = DetailsFragment.getInstance(id);
				
				transaction.replace(R.id.detailsOfName, fragment);
				
				transaction.commit();
			}
		});
	}

	@Override
	public void onStart() {
		super.onStart();
	}

	@Override
	public void onResume() {
		super.onPause();
	}

	@Override
	public void onPause() {
		super.onPause();
	}

	@Override
	public void onStop() {
		super.onStop();
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();

		db.close();
		db = null;
	}

	@Override
	public void onDetach() {
		super.onDetach();
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
	}

	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		Loader<Cursor> loader = null;
		if (ID_NAME_LIST_LOADER == id) {
			loader = new MySimpleCursorLoader(getActivity());
		}
		return loader;
	}

	private static final class MySimpleCursorLoader extends SimpleCursorLoader {

		public MySimpleCursorLoader(Context context) {
			super(context);
		}

		@Override
		public Cursor loadInBackground() {
			Cursor c = db.getEntry(SimpleObjectDatabaseDescriptor.TBL_NAME, SimpleObjectDatabaseDescriptor.FIELDS, null);
			return c;
		}
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
		int id = loader.getId();
		if (ID_NAME_LIST_LOADER == id) {
			adapter.swapCursor(cursor);
		}
	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
		int id = loader.getId();
		if (ID_NAME_LIST_LOADER == id) {
			adapter.swapCursor(null);
		}
	}

}
