package my.pretty.project.utils.services;

import my.pretty.project.utils.LogHelper;
import android.app.Service;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.IBinder;
import android.util.Log;

public class LoggingService extends Service {

	@Override
	public void onCreate() {
		super.onCreate();
		LogHelper.log(getClass(), Log.VERBOSE, "onCreate()");
	}
	
	@Override
	public void onStart(Intent intent, int startId) {
		super.onStart(intent, startId);		
		LogHelper.log(getClass(), Log.VERBOSE, "onStart(intent("+intent+") startId("+startId+"))");
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		LogHelper.log(getClass(), Log.VERBOSE, "onStartCommand(intent("+intent+") flags("+flags+") startId("+startId+"))");
		return super.onStartCommand(intent, flags, startId);
	}
	
	@Override
	public boolean onUnbind(Intent intent) {
		LogHelper.log(getClass(), Log.VERBOSE, "onUnbind(intent("+intent+"))");
		return super.onUnbind(intent);
	}
	
	@Override
	public void onRebind(Intent intent) {
		super.onRebind(intent);
		LogHelper.log(getClass(), Log.VERBOSE, "onRebind(intent("+intent+"))");
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		LogHelper.log(getClass(), Log.VERBOSE, "onDestroy()");
	}
	
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		
		String orientation = "";
		if(newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
			orientation = "Landscape";
		} else if(newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
			orientation = "Portrait";
		} else if(newConfig.orientation == Configuration.ORIENTATION_SQUARE) {
			orientation = "Square";
		} else if(newConfig.orientation == Configuration.ORIENTATION_UNDEFINED) {
			orientation = "Undefined";
		}
		
		LogHelper.log(getClass(), Log.VERBOSE, "onConfigurationChanged(orientation = "+orientation+")");
	}
	
	@Override
	public IBinder onBind(Intent intent) {
		LogHelper.log(getClass(), Log.VERBOSE, "onBind(intent("+intent+"))");
		return null;
	}

}
