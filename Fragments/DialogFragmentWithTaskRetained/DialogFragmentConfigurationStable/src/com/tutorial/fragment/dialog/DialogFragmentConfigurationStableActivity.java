package com.tutorial.fragment.dialog;

import android.app.Activity;
import android.os.Bundle;

public class DialogFragmentConfigurationStableActivity extends Activity {
	private static final String DIALOG_FRAGMENT_TAG = "dialog_fragment_tag";
	private static final String FINISHED = "finished";
	private LoaderFragment fragment;
	private boolean finished = false;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		if(savedInstanceState != null) {
			finished = savedInstanceState.getBoolean(FINISHED);
		}

		if ((fragment = (LoaderFragment) getFragmentManager().findFragmentByTag(DIALOG_FRAGMENT_TAG)) == null && !finished) {
			fragment = new LoaderFragment();
			fragment.show(getFragmentManager(), DIALOG_FRAGMENT_TAG);
			finished  = true;
		}
	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		
		outState.putBoolean(FINISHED, finished);
	}
}