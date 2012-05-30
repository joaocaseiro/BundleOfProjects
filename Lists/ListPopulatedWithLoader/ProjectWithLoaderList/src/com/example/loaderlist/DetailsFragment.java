package com.example.loaderlist;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.loaderlist.database.Database;
import com.example.loaderlist.database.SimpleObjectDatabase;

public class DetailsFragment extends Fragment {

	private static final String ID_OBJECT_DATABASE = "id_of_object_in_database";

	public static DetailsFragment getInstance(long id) {
		DetailsFragment fragment = new DetailsFragment();
		Bundle arguments = new Bundle();
		arguments.putLong(ID_OBJECT_DATABASE, id);
		fragment.setArguments(arguments);
		return fragment;
	}

	private long id;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.details_layout, null);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		Bundle arguments = getArguments();
		id = (Long) arguments.get(ID_OBJECT_DATABASE);

		Database db = new Database(getActivity());
		db.open();

		SimpleObjectDatabase object = SimpleObjectDatabase.findOne(id, db);

		db.close();
		db = null;

		View view = getView();
		((TextView) view.findViewById(R.id.id_details)).setText(String.valueOf(object.getId()));
		((TextView) view.findViewById(R.id.name_details)).setText(object.getName());
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
}
