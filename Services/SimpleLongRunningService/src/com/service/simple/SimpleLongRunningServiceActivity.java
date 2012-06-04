package com.service.simple;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;

public class SimpleLongRunningServiceActivity extends Activity {
	private static final String START_SERVICE_TASK = "tag_start_service";
	private static final String FINISHED = "finished";
	private static final String STOP_SERVICE_TASK = "tag_stop_service";
	private boolean finished = false;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		if (savedInstanceState != null) {
			finished = savedInstanceState.getBoolean(FINISHED);
		}

		if (!finished) {
			ServiceCommunicator comm = ((SimpleLongRunningServiceApplication) getApplication()).getServiceCommunicator();
			comm.registerForBroadcastsFromService();

			comm.executeRequest(this, new Runnable() {
				@Override
				public void run() {
					LogHelper.log(getClass(), Log.VERBOSE, "Sending commands to the service");
					((SimpleLongRunningServiceApplication) SimpleLongRunningServiceActivity.this.getApplication()).getServiceCommunicator().updateDB(
							SimpleLongRunningServiceActivity.this.getApplicationContext());
				}
			}, new Runnable() {
				@Override
				public void run() {
					LogHelper.log(getClass(), Log.VERBOSE, "Using information received from the service");

				}
			}, START_SERVICE_TASK);
			finished = true;
		}
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);

		outState.putBoolean(FINISHED, finished);
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();

		if (finished) {
			ServiceCommunicator comm = ((SimpleLongRunningServiceApplication) getApplication()).getServiceCommunicator();
			comm.registerForBroadcastsFromService();

			comm.executeRequest(this, new Runnable() {
				@Override
				public void run() {
					LogHelper.log(getClass(), Log.VERBOSE, "Sending commands to the service");
					((SimpleLongRunningServiceApplication) SimpleLongRunningServiceActivity.this.getApplication()).getServiceCommunicator().shutdown(
							SimpleLongRunningServiceActivity.this.getApplicationContext());
				}
			}, new Runnable() {
				@Override
				public void run() {
					LogHelper.log(getClass(), Log.VERBOSE, "Service is shutting down");

				}
			}, STOP_SERVICE_TASK);
			finished = true;
		}
	}
}