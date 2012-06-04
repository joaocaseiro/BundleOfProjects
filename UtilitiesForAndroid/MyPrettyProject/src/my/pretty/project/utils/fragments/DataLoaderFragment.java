package my.pretty.project.utils.fragments;

import junit.framework.Assert;
import my.pretty.project.R;
import my.pretty.project.utils.DataLoader;
import my.pretty.project.utils.LogHelper;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

public class DataLoaderFragment extends AsyncTaskDialogFragment<Void, Integer, Void> {

	private static final String DATA_LOADER_ARGUMENT = "data_loader_argument";
	private static final String RUNNING = "running";
	private ProgressBar progressBar;
	private DataLoader dataLoader;

	public boolean hasData = true;
	private boolean running;

	public DataLoaderFragment() {
	}

	public static DataLoaderFragment getInstance(DataLoader dataLoader) {
		DataLoaderFragment fragment = new DataLoaderFragment();
		Bundle args = new Bundle();
		args.putSerializable(DATA_LOADER_ARGUMENT, dataLoader);
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
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
		dataLoader = (DataLoader) getArguments().getSerializable(DATA_LOADER_ARGUMENT);

		Assert.assertNotNull(dataLoader);

		if (savedInstanceState != null) {
			running = savedInstanceState.getBoolean(RUNNING);
		}

		if (!running) {
			execute();
			running = true;
		}
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putBoolean(RUNNING, running);
	}

	@Override
	protected Void doInBackground(Void... params) {

		publishProgress(0);
		LogHelper.log(getClass(), Log.VERBOSE, "Started Download");

		try {
			dataLoader.loadData();
		} catch (Exception ex) {
			hasData = false;
			LogHelper.log(getClass(), Log.ERROR, " - doInBackground() : " + ex);
			ex.printStackTrace();
		}

		return null;
	}

	@Override
	protected void onPostExecute(Void result) {
		super.onPostExecute(result);

		LogHelper.log(getClass(), Log.INFO, "onPostExecute()");
		try {
			dataLoader.updateUI();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		running = false;

		dismiss();
	}

	@Override
	protected void onCancelled() {
		super.onCancelled();
		dataLoader.onCancel();
	}

	@Override
	protected void onProgressUpdate(Integer... values) {
		super.onProgressUpdate(values);
		progressBar.setProgress(values[0]);
	}

	@Override
	public void onDestroyView() {
		Dialog dialog = null;
		if ((dialog = getDialog()) != null) { // bug fix
			dialog.setDismissMessage(null);
		}
		super.onDestroyView();
	}
}
