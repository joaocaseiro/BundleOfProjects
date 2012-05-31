package com.tutorial.listview.adapter;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class SimpleDialogFragment extends DialogFragment {

	private static final String ARGUMENT_POSITION = "argument_position";
	private static final String ARGUMENT_NAME = "argument_name";

	public static SimpleDialogFragment getInstance(String name, int position) {
		SimpleDialogFragment fragment = new SimpleDialogFragment();
		Bundle arguments = new Bundle();
		arguments.putInt(ARGUMENT_POSITION, position);
		arguments.putString(ARGUMENT_NAME, name);
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
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		getDialog().setTitle("Detalhes");
		View view = inflater.inflate(R.layout.dialog_detail_item, null);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		Bundle arguments = getArguments();
		// int position = arguments.getInt(ARGUMENT_POSITION);
		String name = arguments.getString(ARGUMENT_NAME);

		ImageView image = (ImageView) getView().findViewById(R.id.list_item_image);
		image.setImageResource(R.drawable.ic_launcher);
		TextView text = (TextView) getView().findViewById(R.id.list_item_text);
		text.setText(name);
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
