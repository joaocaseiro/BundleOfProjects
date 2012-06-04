package com.service.simple;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

public class ServiceCommunicator {

	private ServiceObserver recv;
	private DataLoaderFragment fragment;
	private boolean dataNotDelivered;
	private Context context;
	private boolean serviceRunning = false;

	public ServiceCommunicator(Context ctx) {
		this.context = ctx;
	}

	public void registerForBroadcastsFromService() {
		IntentFilter intf = new IntentFilter(UpdaterService.SERVICE_STARTING);
		intf.addAction(UpdaterService.SERVICE_RESTARTING);
		intf.addAction(UpdaterService.SERVICE_STOPPING);
		intf.addAction(UpdaterService.DATABASE_UPDATED);
		intf.addAction(UpdaterService.DATABASE_UPDATE_FAILED);

		recv = new ServiceObserver();
		context.registerReceiver(recv, intf);
	}

	public void unregisterForBroadcastsFromService() {
		context.unregisterReceiver(recv);
	}

	public class ServiceObserver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			if (UpdaterService.SERVICE_STARTING.equals(intent.getAction())) {
				LogHelper.log(getClass(), Log.VERBOSE, "Callback service starting");
			} else if (UpdaterService.SERVICE_RESTARTING.equals(intent.getAction())) {
				LogHelper.log(getClass(), Log.VERBOSE, "Callback service restarting");				
			} else if (UpdaterService.SERVICE_STOPPING.equals(intent.getAction())) {
				LogHelper.log(getClass(), Log.VERBOSE, "Callback service stopping");								
			} else if (UpdaterService.DATABASE_UPDATED.equals(intent.getAction())) {
				LogHelper.log(getClass(), Log.VERBOSE, "Callback service updated");
				dataNotDelivered = false;
			} else if (UpdaterService.DATABASE_UPDATE_FAILED.equals(intent.getAction())) {
				LogHelper.log(getClass(), Log.VERBOSE, "Callback service updated failed");
				dataNotDelivered = false;
			}

		}
	}
	
	public void executeRequest(Context ctx, final Runnable runParallel, final Runnable runAfterFinish, String tag) {
		fragment = (DataLoaderFragment) ((Activity) ctx).getFragmentManager().findFragmentByTag(tag);
		if (fragment == null) {
			FragmentTransaction transaction = ((Activity) ctx).getFragmentManager().beginTransaction();
			fragment = DataLoaderFragment.getInstance(new DataLoader() {

				private static final long serialVersionUID = 1L;

				public void updateUI() {
					runAfterFinish.run();
				}

				public void onCancel() {
				}

				public void loadData() {
					dataNotDelivered = true;
					runParallel.run();
					while (dataNotDelivered) {
						; // TODO - another method to pass time?
					}
				}
			});
			
			transaction.add(fragment, tag);
			transaction.commit();
		}
	}


	/**
	 * Static method to start an update (non-block)
	 */
	public void updateDB(Context ctx) {
		LogHelper.log(UpdaterService.class, Log.DEBUG, "Static method updateDB from Service started");
		Intent i = new Intent(ctx, UpdaterService.class);
		i.setAction(UpdaterService.USER_ISSUED_UPDATE_ALL_DB);
		ctx.startService(i);
	}

	public boolean isServiceRunning() {
		return serviceRunning;
	}
}
