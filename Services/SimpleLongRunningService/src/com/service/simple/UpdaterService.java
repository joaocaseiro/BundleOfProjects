package com.service.simple;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class UpdaterService extends Service {
	private static final String SERVICE_ID = "com.android.example.service";

	// states of the service
	public static final String SERVICE_NOT_CONNECTED = SERVICE_ID + ".SERVICE_NOT_CONNECTED";
	public static final String SERVICE_CONNECTING = SERVICE_ID + ".SERVICE_CONNECTING";
	public static final String SERVICE_CONNECTED = SERVICE_ID + ".SERVICE_CONNECTED";

	public static final String SERVICE_STARTING = SERVICE_ID + ".SERVICE_STARTING";
	public static final String SERVICE_RESTARTING = SERVICE_ID + ".SERVICE_RESTARTING";
	public static final String SERVICE_STOPPING = SERVICE_ID + ".SERVICE_STOPPING";
	public static final String DATABASE_UPDATED = SERVICE_ID + ".DATABASE_UPDATED";
	public static final String DATABASE_UPDATE_FAILED = SERVICE_ID + ".DATABASE_UPDATE_FAILED";

	public static final String STOP_UPDATING_AND_DIE = SERVICE_ID + ".STOP_UPDATING_AND_DIE";
	public static final String USER_ISSUED_UPDATE_ALL_DB = SERVICE_ID + ".USER_ISSUED_UPDATE_ALL";
	public static final String CHECK_CONNECTIVITY = SERVICE_ID + ".CHECK_CONNECTIVITY";
	public static final String SERVICE_RESTARTED_FROM_KILL_BY_SYSTEM = SERVICE_ID + ".SERVICE_RESTARTED_FROM_KILL_BY_SYSTEM";
	public static final String TRY_TO_CONNECT_TO_RABBIT = SERVICE_ID + ".TRY_TO_CONNECT_TO_RABBIT";
	public static final String OBJECT_STATE_CHANGED = SERVICE_ID + ".OBJECT_STATE_CHANGED";

	private static final double UPDATE_INTERVAL_IN_SECONDS = 30 * 60;
	private static final int SECONDS = 1000;
	private static final long UPDATE_INTERVAL = (long) (UPDATE_INTERVAL_IN_SECONDS * SECONDS);
	private static final int STOPPING_TIMEOUT_IN_SECONDS = 60 * 60;
	private static final int STOPPING_TIMEOUT = STOPPING_TIMEOUT_IN_SECONDS * SECONDS;

	public static final int staticNotificationID = 1;
	private String currentState = SERVICE_NOT_CONNECTED;

	private NotificationManager notificationManager;

	public int mValue;

	public static final int MSG_SET_VALUE = 0;
	
	private void changeNotification(String state) {
		Notification notif = getNotification(null, null, null, true);
		startForeground(staticNotificationID, notif);
	}

	@Override
	public void onCreate() {
		super.onCreate();
		LogHelper.log(UpdaterService.class, Log.DEBUG, "Service onCreate started");

		notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

		Notification notif = getNotification("Domotics Service Starting...", null, "Service is not Connected", false);
		startForeground(staticNotificationID, notif);

		broadcastServiceStarting();
	}

	private Notification getNotification(String scrollText, String title, String description, boolean addIntent) {
		int idImage = getImageFromCurrentState();

		if (scrollText == null) {
			scrollText = "Domotics Service Running";
		}
		if (title == null) {
			title = "DomoticsAtWork Service";
		}
		if (description == null) {
			description = "The Service is running";
		}

		// Set the icon, scrolling text and timestamp
		Notification notif = new Notification(idImage, scrollText, System.currentTimeMillis());

		// The PendingIntent to launch our activity if the user selects this
		// notification
		PendingIntent contentIntent = null;

		Intent it = null;
		if (addIntent) {
			it = new Intent(this, SimpleLongRunningServiceActivity.class);
		} else {
			it = new Intent();
		}
		contentIntent = PendingIntent.getActivity(this, 0, it, 0);

		// Set the info for the views that show in the notification panel.
		notif.setLatestEventInfo(this, title, description, contentIntent);

		return notif;
	}

	private int getImageFromCurrentState() {
		if (SERVICE_CONNECTED.equals(currentState)) {
			return R.drawable.inset_domotics_status_bar_on;
		} else if (SERVICE_CONNECTING.equals(currentState)) {
			return R.drawable.inset_domotics_status_bar_sync;
		} else if (SERVICE_NOT_CONNECTED.equals(currentState)) {
			return R.drawable.inset_domotics_status_bar_off;
		}
		return -1;
	}

	private void startTheService(Intent intent) {
		if (USER_ISSUED_UPDATE_ALL_DB.equals(intent.getAction())) {
			LogHelper.log(UpdaterService.class, Log.VERBOSE, "Received USER_ISSUED_UPDATE_ALL_DB");
			updateAllDB();
		} else if (STOP_UPDATING_AND_DIE.equals(intent.getAction())) {
			LogHelper.log(UpdaterService.class, Log.VERBOSE, "Received STOP_UPDATING_AND_DIE");
			stopSelf();
		} else if (SERVICE_RESTARTED_FROM_KILL_BY_SYSTEM.equals(intent.getAction())) {
			LogHelper.log(UpdaterService.class, Log.VERBOSE, "Received SERVICE_RESTARTED_FROM_KILL_BY_SYSTEM");
			broadcastServiceRestarted();
			updateAllDB();
		}
	}

	private void broadcastServiceStarting() {
		Intent it = new Intent();
		it.setAction(SERVICE_STARTING);
		sendBroadcast(it);
	}

	private void broadcastServiceRestarted() {
		Intent it = new Intent();
		it.setAction(SERVICE_RESTARTING);
		sendBroadcast(it);
	}

	private void broadcastServiceStopping() {
		Intent it = new Intent();
		it.setAction(SERVICE_STOPPING);
		sendBroadcast(it);
	}

	private void broadcast(String action) {
		Intent it = new Intent();
		it.setAction(action);
		sendBroadcast(it);
	}

	private void setStopService() {
		LogHelper.log(UpdaterService.class, Log.DEBUG, "Scheduled the Service to stop in " + STOPPING_TIMEOUT_IN_SECONDS + " seconds");
		Intent i = new Intent();
		i.setClass(this, UpdaterService.class);
		i.setAction(STOP_UPDATING_AND_DIE);
		PendingIntent pi = PendingIntent.getService(this, 0, i, 0);
		AlarmManager alarmMgr = (AlarmManager) getSystemService(ALARM_SERVICE);
		alarmMgr.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + STOPPING_TIMEOUT, STOPPING_TIMEOUT, pi);
	}

	private void setNextUpdate() {
		LogHelper.log(UpdaterService.class, Log.DEBUG, "Scheduled the Service to update every " + UPDATE_INTERVAL_IN_SECONDS + " seconds");
		Intent i = new Intent();
		i.setClass(this, UpdaterService.class);
		i.setAction(CHECK_CONNECTIVITY);
		PendingIntent pi = PendingIntent.getService(this, 0, i, 0);
		AlarmManager alarmMgr = (AlarmManager) getSystemService(ALARM_SERVICE);
		alarmMgr.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + UPDATE_INTERVAL, UPDATE_INTERVAL, pi);
	}

	private void clearScheduledStop() {
		LogHelper.log(UpdaterService.class, Log.DEBUG, "Cleared stop timeout");
		clearUpdates(STOP_UPDATING_AND_DIE);
	}

	private void clearScheduledUpdates() {
		LogHelper.log(UpdaterService.class, Log.DEBUG, "Cleared old scheduled updates");
		clearUpdates(CHECK_CONNECTIVITY);
		clearUpdates(USER_ISSUED_UPDATE_ALL_DB);
	}

	private void clearUpdates(String actionOfUpdates) {
		LogHelper.log(UpdaterService.class, Log.VERBOSE, "Cleared updates");
		Intent i = new Intent();
		i.setClass(this, UpdaterService.class);
		i.setAction(actionOfUpdates);
		PendingIntent pi = PendingIntent.getService(this, 0, i, 0);
		AlarmManager alarmMgr = (AlarmManager) getSystemService(ALARM_SERVICE);
		alarmMgr.cancel(pi);
	}

	private void updateAllDB() {
		LogHelper.log(UpdaterService.class, Log.DEBUG, "Started update of all information from the DB");
		Runnable run = new Runnable() {

			public void run() {
				try {
					initiateServiceActivity();
					broadcast(DATABASE_UPDATED);
				} catch (Exception e) {
					// any exception means we have failed to update. no need to
					// print
					// since that has already been dealt with
				}
			}

		};

		Thread t = new Thread(run);
		t.start();
	}

	private void initiateServiceActivity() throws Exception {
		changeNotification(SERVICE_CONNECTING);
		Thread.sleep(4000);
		changeNotification(SERVICE_CONNECTED);
	}

	// to work with older versions
	@Override
	public void onStart(Intent intent, int startId) {
		super.onStart(intent, startId);
		LogHelper.log(UpdaterService.class, Log.DEBUG, "Service onStart started");

		if (intent == null) {
			LogHelper.log(UpdaterService.class, Log.DEBUG, "Service was restarted by the system");
			intent = new Intent();
			intent.setAction(SERVICE_RESTARTED_FROM_KILL_BY_SYSTEM);
		}
		startTheService(intent);
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		LogHelper.log(UpdaterService.class, Log.DEBUG, "Service onStartCommand started");

		if (intent == null) {
			intent = new Intent();
			intent.setAction(SERVICE_RESTARTED_FROM_KILL_BY_SYSTEM);
		}
		startTheService(intent);
		return START_STICKY;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		LogHelper.log(UpdaterService.class, Log.DEBUG, "Service Method onDestroy started");
		broadcastServiceStopping();
		stopForeground(true);
		notificationManager.cancel(staticNotificationID);
	}

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}
}
