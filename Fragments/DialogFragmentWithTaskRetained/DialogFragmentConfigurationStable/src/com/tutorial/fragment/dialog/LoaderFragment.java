package com.tutorial.fragment.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

public class LoaderFragment extends AsyncTaskDialogFragment<Void, Integer, Void> {

	private ProgressBar progressBar;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		execute();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.loader, null);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		getDialog().setTitle("Downloading");
		progressBar = (ProgressBar) getView().findViewById(R.id.progress);
	}

	@Override
	protected Void doInBackground(Void... params) {

		publishProgress(0);
		LogHelper.log(getClass(), Log.VERBOSE, "Started Download");

		try {
			Thread.sleep(1000);
			publishProgress(0);
			LogHelper.log(getClass(), Log.VERBOSE, "First Package Downloaded");

			Thread.sleep(2000);
			publishProgress(20);
			LogHelper.log(getClass(), Log.VERBOSE, "Second Package Downloaded");

			Thread.sleep(3000);
			publishProgress(40);
			LogHelper.log(getClass(), Log.VERBOSE, "Third Package Downloaded");

			Thread.sleep(4000);
			publishProgress(70);
			LogHelper.log(getClass(), Log.VERBOSE, "Fourth Package Downloaded");

			Thread.sleep(5000);
			publishProgress(100);
			LogHelper.log(getClass(), Log.VERBOSE, "Finished Downloading");
		} catch (InterruptedException e) {
		}

		return null;
	}

	@Override
	protected void onPostExecute(Void result) {
		super.onPostExecute(result);

		dismiss();
	}

	@Override
	protected void onProgressUpdate(Integer... values) {
		super.onProgressUpdate(values);
		progressBar.setProgress(values[0]);
	}

	@Override
	public void onDestroyView() {
		Dialog dialog = null;
		if ((dialog = getDialog()) != null) {
			dialog.setDismissMessage(null);
		}
		super.onDestroyView();
	}
}
